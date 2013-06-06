package com.konka.doubanmovie;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.konka.doubanmovie.api.ErrCode;
import com.konka.doubanmovie.api.Movie;
import com.konka.doubanmovie.oauth.OAuth2;
import com.konka.doubanmovie.oauth.OAuth2Client;
import com.konka.doubanmovie.util.HttpUtils;

/**
 * 
 * @author 
 * @date
 * @desc 
 * 		
 */
public class HomeActivity extends Activity{
	private static final String TAG ="HomeActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Debug.debug(TAG, "onCreate");
		setContentView(R.layout.home);
		
		HttpUtils.initConnection();
		
		findViewById(R.id.test_btn2).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				test();
			}
        });
		
		findViewById(R.id.test_play).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this, PlayerActivity.class);
				intent.putExtra(PlayerActivity.EXTRA_VIDEO_URL,
						"http://cybertran.baidu.com/video/videotran.ts?output_format=2048&local=1&addr=010031302e34302e34372e31385f353030300000&start=538620&len=391228&src=a74d4fb8596e179fc9bd76478df415961025.m3u8-1.ts"
						/*"http://daily3gp.com/vids/family_guy_penis_car.3gp"*/);
				startActivity(intent);
			}
		});
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		HttpUtils.shutDownConnection();
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	if(requestCode == MainActivity.OAUTH_REQUEST_CODE){
    		if(resultCode == RESULT_OK){
    			OAuth2 oauth = (OAuth2)data.getSerializableExtra("oauth");
    			if(oauth != null){
    				Debug.debug(TAG, "access_token = " + oauth.getAccessToken());
    				Debug.debug(TAG, "refresh_token = " + oauth.getRefreshToken());
    				SharedPrefManager.writeOAuthToPref(getApplicationContext(), oauth);
    				OAuth2Client.setOAuth(oauth);
    				//TODO call function
    			}
    		}
    		else{
    			Toast.makeText(this, "授权登录失败", Toast.LENGTH_LONG).show();
    		}
    	}
    }
	
	/**
     * 验证acess_token是否过期，
     * 		调用此函数应该在第一次调用某个API时，检测access_token是否过期
     * @param stausCode http返回状态码
     * @param returnResult http返回结果
     * @return
     */
    private boolean isTokenValid(String statusCode, String httpContent){
    	if(statusCode.equals(HttpUtils.CODE_BAD_REQUEST)){
			if(httpContent != null && !httpContent.equals("")){
				try {
					JSONObject json = new JSONObject(httpContent);
					if( !json.isNull("code") ){
						int code = json.getInt("code");
						Debug.debug(TAG, "code=" + code);
						if( code == ErrCode.ERR_TOKEN_EXPIRED ){
							return false;
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
    	}
    	return true;
    }
	
	/**
     * called when access_token expired in.
     * @return 
     */
    private boolean refreshToken(){
		OAuth2 oauth = OAuth2Client.getOAuth();
		boolean refreshOk = false;
		try {
			refreshOk = OAuth2Client.refreshToken(oauth);
			//now OAuth has been refreshed
			//save data to preferences. 
			if(refreshOk)
				SharedPrefManager.writeOAuthToPref(getApplicationContext(), oauth);
		} catch (Exception e) {
			e.printStackTrace();
			refreshOk = false;
		}
		return refreshOk;
    }
    
    private void jump2OAuthActivity(){
    	Intent intent = new Intent(HomeActivity.this, OAuthActivity.class);
    	HomeActivity.this.startActivityForResult(intent, MainActivity.OAUTH_REQUEST_CODE);
    }
	
	private void test(){
    	new Thread(){
    		public void run(){
    			String getMovieUrl = "https://api.douban.com/v2/movie/subject/5308265";
    			try {
    				Map<String, String> resultMap = 
    						HttpUtils.doGet(getMovieUrl, null, true);
					String statusCode = resultMap.get(HttpUtils.CODE).toString();
					Debug.debug(TAG, "status code=" + statusCode);
					String content = resultMap.get(HttpUtils.RETURN).toString();
					
					if( !isTokenValid(statusCode, content) ){
						//token过期，刷新token
						if( !refreshToken() ){
							//异常时启动OAuthActivity,跳转至授权界面
							jump2OAuthActivity();
						}else{
							//TODO 刷新失败，应跳到授权也重新登录
						}
					}
					else{
						//http请求ok
						dealwithResult(content);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} 
    			//Result result = new DoubanAPI().getNowPlayingMovies();
    		}
    	}.start();
    }
	
	private void dealwithResult(String content){
    	try {
			JSONObject json = new JSONObject(content);
			new Movie(json, false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
}
