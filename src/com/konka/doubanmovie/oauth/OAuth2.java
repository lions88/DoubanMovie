package com.konka.doubanmovie.oauth;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.konka.doubanmovie.util.Configuration;

/**
 * 
 * @author 
 * @date
 * @desc
 * 		OAuth2实体
 * 		OAuth2授权流程
 * 			1.根据第三方应用的app_key和secret打开authorizedURL，输入用户名和密码；
 * 			      这个认证网页是服务端的地址，和第三方应用无关，so用户名和密码不会被第三方得到，安全。
 * 			  ok后服务端重定向至callback的redirect_url网址，authorization_code附在redirect_url之后,
 * 			  eg. http://redrect_url?code="xxx",键code值截取下来；
 * 			2.使用上一步的authorization_code作为参数打开accessTokenURL获取，
 * 			  access_token,refresh_token,expired_in等字段，这些字段在https(POST)连接返回的json数据中。
 */
public class OAuth2 implements java.io.Serializable{
	private static final long serialVersionUID = -3525718568177701555L;
	
	private String mApiKey;
	private String mApiSecret;
	private String mRedirectUri;
	private String mResponseType;
	private String mScope;		//可选参数，申请API权限，多个权限用逗号隔开
	private String mState;		//可选参数
	
	//第一步授权获取的Authorization_code
	private String mAuthorizationCode;
	
	//第二步根据mAuthorizationCode获取到的access_token和refresh_token
	private String mAccessToken;
	private String mRefreshToken;
	private String mExpiresIn;	//过期时间
	private String mUserId;		//用户id
	
	static enum Parameters{
		client_id,
		client_secret,
		redirect_uri,
		
		response_type,
		scope,
		state,
		
		code,
		grant_type,
		refresh_token,
	}
	
	public OAuth2(){
		mApiKey = Configuration.getValue(Configuration.KEY_API_KEY);
		mApiSecret = Configuration.getValue(Configuration.KEY_API_SECRET);
		mRedirectUri = Configuration.getValue(Configuration.KEY_REDIRECT_URI);
		mResponseType = "code";
		mScope = "douban_basic_common,movie_basic,movie_basic_r,movie_basic_w,movie_advance_r";
	}
	
	//获取oauth2第一步授权的参数对。authorization_code阶段
	//第一步是获取authorization_code
	public List<NameValuePair> getAuthorizationCodeParamsList() {
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        paramsList.add(new BasicNameValuePair(Parameters.client_id.name(), mApiKey));
        paramsList.add(new BasicNameValuePair(Parameters.response_type.name(),  mResponseType));
        paramsList.add(new BasicNameValuePair(Parameters.redirect_uri.name(), mRedirectUri));
        paramsList.add(new BasicNameValuePair(Parameters.scope.name(), mScope));
        
        return paramsList;
	}
	
	//获取oauth2第二步授权的参数对。authorization_token阶段
	//第二步是获取access_token和refresh_token
	public List<NameValuePair> getAccessTokenParamsList(){
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        paramsList.add(new BasicNameValuePair(Parameters.client_id.name(), mApiKey));
        paramsList.add(new BasicNameValuePair(Parameters.client_secret.name(), mApiSecret));
        paramsList.add(new BasicNameValuePair(Parameters.redirect_uri.name(), mRedirectUri));
        paramsList.add(new BasicNameValuePair(Parameters.grant_type.name(),  "authorization_code"));
        paramsList.add(new BasicNameValuePair(Parameters.code.name(),  mAuthorizationCode));
        
        return paramsList;
	}
	
	//当access_token过期时，使用refresh_token再次换取access_token
	public List<NameValuePair> getRefreshTokenParamsList(String refreshToken){
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        paramsList.add(new BasicNameValuePair(Parameters.client_id.name(), mApiKey));
        paramsList.add(new BasicNameValuePair(Parameters.client_secret.name(), mApiSecret));
        paramsList.add(new BasicNameValuePair(Parameters.redirect_uri.name(), mRedirectUri));
        paramsList.add(new BasicNameValuePair(Parameters.grant_type.name(),  "refresh_token"));
        paramsList.add(new BasicNameValuePair(Parameters.refresh_token.name(),  refreshToken));
        
        return paramsList;
	}
	
	public String getClientId(){
		return mApiKey;
	}
	
	public String getSecret(){
		return mApiSecret;
	}
	
	public String getRedirectUrl(){
		return mRedirectUri;
	}
	
	public String getResponseType(){
		return mResponseType;
	}
	
	public String getScope(){
		return mScope;
	}
	
	public String getState(){
		return mState;
	}
	
	public void setAuthorizationCode(String code){
		this.mAuthorizationCode = code;
	}
	public String getAuthorizationCode(){
		return mAuthorizationCode;
	}
	
	public void setAccessToken(String token){
		this.mAccessToken = token;
	}
	public String getAccessToken(){
		return mAccessToken;
	}
	
	public void setRefreshToken(String token){
		this.mRefreshToken = token;
	}
	public String getRefreshToken(){
		return mRefreshToken;
	}
	
	public void setExpiresIn(String expiresIn){
		this.mExpiresIn = expiresIn;
	}
	public String getExpiresIn(){
		return mExpiresIn;
	}
	
	public void setUserId(String userId){
		this.mUserId = userId;
	}
	public String getUserId(){
		return mUserId;
	}
}
