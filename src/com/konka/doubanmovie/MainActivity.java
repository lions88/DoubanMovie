package com.konka.doubanmovie;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.konka.doubanmovie.oauth.OAuth2;
import com.konka.doubanmovie.oauth.OAuth2Client;

public class MainActivity extends Activity {
	public static final int OAUTH_REQUEST_CODE = 100;
	private static final String TAG = "MainActivity";
	private static final int OAUTH_FAILED_ID = 2;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        OAuth2 oauth;
        if( (oauth = 
        		SharedPrefManager.readOAuthFromPref(getApplicationContext())) != null){
        	Debug.debug(TAG, "access_token = " + oauth.getAccessToken());
			Debug.debug(TAG, "refresh_token = " + oauth.getRefreshToken());
			
			OAuth2Client.setOAuth(oauth);
			jump2HomeActivity();
			MainActivity.this.finish();
        }else{
        	jump2OAuthActivity();
        }
    }
    
	@SuppressWarnings("deprecation")
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	if(requestCode == OAUTH_REQUEST_CODE){
    		if(resultCode == RESULT_OK){
    			OAuth2 oauth = (OAuth2)data.getSerializableExtra("oauth");
    			if(oauth != null){
    				Debug.debug(TAG, "access_token = " + oauth.getAccessToken());
    				Debug.debug(TAG, "refresh_token = " + oauth.getRefreshToken());
    				//write data
    				SharedPrefManager.writeOAuthToPref(getApplicationContext(), oauth);
    				OAuth2Client.setOAuth(oauth);
    				//jump activity
    				jump2HomeActivity();
    				MainActivity.this.finish();
    			}
    		}
    		else{
    			//Toast.makeText(this, "授权登录失败", Toast.LENGTH_LONG).show();
    			showDialog(OAUTH_FAILED_ID);
    		}
    	}
    }
    
    @Override
    public Dialog onCreateDialog(int dialogId){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("提示");
		builder.setCancelable(false);
    	if(dialogId == OAUTH_FAILED_ID){
    		builder.setMessage("授权登录失败！");
    		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					MainActivity.this.finish();
				}
			});
    	}
    	return builder.create();
    }
    
    private void jump2OAuthActivity(){
    	Intent intent = new Intent(MainActivity.this, OAuthActivity.class);
		MainActivity.this.startActivityForResult(intent, OAUTH_REQUEST_CODE);
    }
    
    private void jump2HomeActivity(){
    	Intent intent = new Intent(MainActivity.this, HomeActivity.class);
    	MainActivity.this.startActivity(intent);
    }
    
}