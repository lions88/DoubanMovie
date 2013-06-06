package com.konka.doubanmovie.util;

import java.util.Properties;

/**
 * 
 * @author 
 * @date 
 * @desc 常量配置类，保存API_KEY、API_SRECRET�?
 * 
 */
public class Configuration {
	public static final String KEY_API_KEY = "api_key";
	public static final String KEY_API_SECRET = "api_secret";
	public static final String KEY_REDIRECT_URI = "redirect_uri";
	
	private static Properties sProps;
	
	static{
		sProps = new Properties();
		sProps.setProperty(KEY_API_KEY, "your api key");
		sProps.setProperty(KEY_API_SECRET, "your api secret");
		sProps.setProperty(KEY_REDIRECT_URI, "your redirect url");
	}
	
	public static String getValue(String key){
		return sProps.getProperty(key);
	}
	
	public static void updateValue(String key, String value){
		sProps.setProperty(key, value);
	}
}
