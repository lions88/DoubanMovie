package com.konka.doubanmovie;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.konka.doubanmovie.oauth.OAuth2;

/**
 * 
 * @author 
 * @date 
 * @desc
 * 		存储简单类型数据至ShrePrefrences
 */
public class SharedPrefManager {
	public static final String KEY_ACCESS_TOKEN = "access_token";
	public static final String KEY_EXPIRES_IN = "expires_in";
	public static final String KEY_REFRESH_TOKEN = "refresh_token";
	public static final String KEY_USER_ID = "douban_user_id";
	
	//write OAuth to preferences
	public static void writeOAuthToPref(Context context, OAuth2 oauth){
		if(oauth == null)
			return;
		
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		pref.edit()
			.putString(KEY_ACCESS_TOKEN, oauth.getAccessToken())
			.putString(KEY_EXPIRES_IN, oauth.getExpiresIn())
			.putString(KEY_REFRESH_TOKEN, oauth.getRefreshToken())
			.putString(KEY_USER_ID, oauth.getUserId())
			.commit();
	}
	
	//read OAuth from preferences
	public static OAuth2 readOAuthFromPref(Context context){
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		String accessToken = pref.getString(KEY_ACCESS_TOKEN, null);
		String userId = pref.getString(KEY_USER_ID, null);
		if( accessToken == null || userId == null){
			return null;
		}
		OAuth2 oauth = new OAuth2();
		oauth.setAccessToken(accessToken);
		oauth.setUserId(userId);
		oauth.setExpiresIn(pref.getString(KEY_EXPIRES_IN, "604800"));
		oauth.setRefreshToken(pref.getString(KEY_REFRESH_TOKEN, null));
		return oauth;
	}
}
