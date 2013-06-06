package com.konka.doubanmovie.oauth;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.konka.doubanmovie.Debug;
import com.konka.doubanmovie.util.BaseURL;
import com.konka.doubanmovie.util.HttpUtils;
import com.konka.doubanmovie.util.StrUtils;

/**
 * 
 * @author 
 * @date
 * @desc
 * 		OAuth2授权流程
 * 			1.根据第三方应用的app_key和secret打开authorizedURL，输入用户名和密码；
 * 			      这个认证网页是服务端的地址，和第三方应用无关，so用户名和密码不会被第三方得到，安全。
 * 			  ok后服务端重定向至callback的redirect_url网址，authorization_code附在redirect_url之后,
 * 			  eg. http://redrect_url?code="xxx",键code值截取下来；
 * 			2.使用上一步的authorization_code作为参数打开accessTokenURL获取，
 * 			  access_token,refresh_token,expired_in等字段，这些字段在https(POST)连接返回的json数据中。
 */
public class OAuth2Client {
	private static OAuth2 sOAuth = null;
	
	public static void setOAuth(OAuth2 oauth){
		sOAuth = oauth;
	}
	
	public static OAuth2 getOAuth(){
		return sOAuth;
	}
	
	/**
	 * 获取authorization_code的url
	 * @param oauth
	 * @return
	 */
	public static String getAuthorizationCodeURL(OAuth2 oauth){
		String url = BaseURL.AUTH_URL;
		List<NameValuePair> paramsList = oauth.getAuthorizationCodeParamsList();
		StringBuffer sb = new StringBuffer();
		sb.append(url);
		sb.append('?');
		sb.append(StrUtils.getStringParams(paramsList));
		return sb.toString();
	}
	
	/**
	 * 获取access_token的url
	 * @param oauth
	 * @return
	 */
	public static String getAccessTokenURL(OAuth2 oauth){
		String auth_code = oauth.getAuthorizationCode();
		if( auth_code == null || auth_code.equals("") ){
			System.err.println("###oauth code is null!!!");
			return null;
		}
		List<NameValuePair> paramsList = oauth.getAccessTokenParamsList();
		StringBuffer sb = new StringBuffer();
		sb.append(BaseURL.TOKEN_URL);
		sb.append('?');
		sb.append(StrUtils.getStringParams(paramsList));
		return sb.toString();
	}
	
	//OAuth2的第二步，根据authoization_code换取access_token
	//POST
	public static boolean accessToken(OAuth2 oauth) throws IOException, JSONException{
		String tokenUrl = BaseURL.TOKEN_URL;
		List<NameValuePair> paramsList = oauth.getAccessTokenParamsList();
		Map<String, String> resultMap = HttpUtils.doPost(tokenUrl, paramsList);
		Debug.debug("OAuth2Cilent", "statusCode=" + resultMap.get(HttpUtils.CODE));
		Debug.debug("OAuth2Cilent", "content=" + resultMap.get(HttpUtils.RETURN));
		if(resultMap.get(HttpUtils.CODE).equals(HttpUtils.CODE_OK_STR)){
			//http返回200
			JSONObject json = new JSONObject(resultMap.get(HttpUtils.RETURN));
			dealWithTokenResult(json, oauth);
			return true;
		} 
		return false;
	}
	
	//当access_token过期时根据refresh_token再次换取新的access_token
	//POST
	public static boolean refreshToken(OAuth2 oauth) throws IOException, JSONException{
		if(oauth == null)
			return false;
		String refreshToken = oauth.getRefreshToken();
		if(refreshToken == null || refreshToken.equals("")){
			//Debug.warn(TAG, "error. refresh token is null");
			return false;
		}
		
		String tokenUrl = BaseURL.TOKEN_URL;
		List<NameValuePair> paramsList = oauth.getRefreshTokenParamsList(refreshToken);
		Map<String, String> resultMap = HttpUtils.doPost(tokenUrl, paramsList);
		Debug.debug("OAuth2Cilent", "statusCode=" + resultMap.get(HttpUtils.CODE));
		Debug.debug("OAuth2Cilent", "content=" + resultMap.get(HttpUtils.RETURN));
		if(resultMap.get(HttpUtils.CODE).equals(HttpUtils.CODE_OK_STR)){
			//http返回200
			JSONObject json = new JSONObject(resultMap.get(HttpUtils.RETURN));
			dealWithTokenResult(json, oauth);
			return true;
		} 
		return false;
	}
	
	private static void dealWithTokenResult(JSONObject json, OAuth2 oauth) throws JSONException{
		if( !json.isNull("access_token") ){
			oauth.setAccessToken(json.getString("access_token"));
		}
		if( !json.isNull("expires_in") ){
			oauth.setExpiresIn(json.getString("expires_in"));
		}
		if( !json.isNull("refresh_token") ){
			oauth.setRefreshToken(json.getString("refresh_token"));
		}
		if( !json.isNull("douban_user_id") ){
			oauth.setUserId(json.getString("douban_user_id"));
		}
	}
}
