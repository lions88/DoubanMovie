package com.konka.doubanmovie.util;

/**
 * 
 * @author 
 * @date 
 * @desc 存储一些url地址
 * 
 */
public class BaseURL {
	public static final String HTTP = "http://";
	public static final String HTTPS = "https://";
	
	public static final String BASE = "www.douban.com/";
	public static final String SERVICE = "service/";
	public static final String OAUTH_VERSION = "auth2/";
	
	public static final String API_BASE = "api.douban.com/";
	public static final String API_VERSION = "v2/";
	
	//oauth2 auth url
	public static final String AUTH_URL = HTTPS + BASE + SERVICE + OAUTH_VERSION + "auth";
	//oauth2 token URL
	public static final String TOKEN_URL = HTTPS + BASE + SERVICE + OAUTH_VERSION + "token";
	
	//user[me]
	public static final String MY_USER_URL = HTTPS + BASE + API_VERSION + "user/~me";
	
	//get movie
	public static final String MOVIE_URL = HTTPS + API_BASE + API_VERSION + "movie/";
	
	//search movie
	public static final String SEARCH_URL = HTTPS + API_BASE + API_VERSION + "search" ;
	
	//celebrity
	public static final String CELEBRITY_URL = HTTPS + API_BASE + API_VERSION + "movie/celebrity/";
	
	//top250
	public static final String TOP_URL = HTTPS + API_BASE + API_VERSION + "movie/top250";
	
	//new_movie
	public static final String NEW_MOVIES_URL = HTTPS + API_BASE + API_VERSION + "movie/new_movies";
	
	//now playing
	public static final String NOW_PLAYING_URL = HTTPS + API_BASE + API_VERSION + "movie/nowplaying";
	
	//coming
	public static final String COMING_MOVIES_URL = HTTPS + API_BASE + API_VERSION + "movie/coming";
	
	//========================================================================================//
	//for oauth1
	public static final String REQUEST_TOKEN_URL = HTTP + BASE + SERVICE + "auth/request_token";
	public static final String AUTHORIZE_URL = HTTP + BASE + SERVICE + "auth/authorize";
	public static final String ACCESS_TOKEN_URL = HTTP + BASE + SERVICE + "auth/access_token"; 
}
