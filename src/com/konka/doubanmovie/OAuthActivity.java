package com.konka.doubanmovie;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.konka.doubanmovie.oauth.OAuth2;
import com.konka.doubanmovie.oauth.OAuth2Client;
import com.konka.doubanmovie.util.HttpUtils;

/**
 * 
 * @author 
 * @date
 * @desc 
 * 		OAuht2安全认证授权登录界面，内嵌WebView
 */
public class OAuthActivity extends Activity{
	private static final String TAG = "OAuthActivity";
	private static final int MSG_OK = 1;
	private static final int MSG_FAILED = 2;
	
	private WebView mWebView;
	private WebViewClient mClient;
	private OAuth2 mOAuth;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Debug.debug(TAG, "onCreate()");
		setContentView(R.layout.oauth_view);
		
		HttpUtils.initConnection();
		
		initWebView();
		initWebViewClient();
		
		mOAuth = new OAuth2();
		String oauthUrl = OAuth2Client.getAuthorizationCodeURL(mOAuth);
		mWebView.loadUrl(oauthUrl);
	}
	
	@Override
	public void onResume(){
		super.onResume();
		mWebView.requestFocus();
	}
	
	private void initWebView(){
		mWebView = (WebView)findViewById(R.id.oauth_webview);
		WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
	}
	
	private void initWebViewClient(){
		mClient = new WebViewClient(){
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				Debug.debug(TAG, "onPageStarted()");
				Debug.debug(TAG, "url="+url);
				//授权输入账号后，会再次call onPageStarted. code附在url尾部
				if(url.indexOf("code=") != -1){
					int start = url.indexOf("code");
					String subCodeStr = url.substring(start);
					int split = subCodeStr.indexOf("=");
					String code = subCodeStr.substring(split+1);
					Debug.debug(TAG, "code=" + code);
					
					//set authorization_code
					mOAuth.setAuthorizationCode(code);
					//now get access_token by authorization_code
					new Thread(){
						public void run(){
							try {
								boolean ret = OAuth2Client.accessToken(mOAuth);
								if(ret){
									Message msg = mHandler.obtainMessage();
									msg.what = MSG_OK;
									msg.obj = mOAuth;
									mHandler.sendMessage(msg);
								}
								else
									mHandler.sendEmptyMessage(MSG_FAILED);
							} catch (Exception e) {
								e.printStackTrace();
								mHandler.sendEmptyMessage(MSG_FAILED);
							} 
						}
					}.start();
				}
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				Debug.debug(TAG, "onPageFinished()");
				Debug.debug(TAG, "url="+url);
			}
			@Override
			public void onReceivedError(WebView view, int errorCode,
		            String description, String failingUrl) {
				Debug.warn(TAG, "onReceivedError() errCode="+errorCode + "; description="
		            +description +"; failingUrl="+failingUrl);
		    }
			@Override
			public void onReceivedSslError(WebView view, SslErrorHandler handler,
		            SslError error) {
				Debug.warn(TAG, "onReceivedSslError()");
				handler.cancel();
			}
		};
		
		mWebView.setWebViewClient(mClient);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {// 捕捉返回键
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			switch(msg.what){
			case MSG_OK:
				Intent intent = new Intent();
				intent.putExtra("oauth", (OAuth2)msg.obj);
				setResult(RESULT_OK, intent);
				OAuthActivity.this.finish();
				break;
			case MSG_FAILED:
				setResult(RESULT_CANCELED);
				OAuthActivity.this.finish();
				break;
			}
		}
	};
}
