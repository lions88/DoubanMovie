package com.konka.doubanmovie.oauth;

/**
 * 
 * @Created on 2013-5-15
 * @brief OAuth1.0授权认证，DoubanMovie暂不使用
 * @author 
 * @date Latest modified on: 2013-5-15
 * @version V1.0.00 
 *
 */
public class OAuth1 {
	public static class Token{
		private String token;
		private String secret;
		
		public Token(String token, String secret){
			this.token = token;
			this.secret = secret;
		}
		
		public String getTokenStr(){
			return token;
		}
		public String getTokenSecret(){
			return secret;
		}
	}
	
	//first. 未授权的RequestToken和对应的RequestTokenSecret
	private Token requestToken;
	
	//second. 已授权的RequestToken，从浏览器重定向到callback_url的网址中获得
	private Token authorizedRequestToken;
	
	//third. 授权的AccessToken和对应的AccessTokenSecret
	private Token accessToken;
	
	public OAuth1(){
	}
	
	//设置第一步未认证的token和secret
	public void setUnauthorizedToken(Token token){
		this.requestToken = token;
	}
	public void setUnauthorizedToken(String tokenStr, String secret){
		Token requestToken = new Token(tokenStr, secret);
		setUnauthorizedToken(requestToken);
	}
	
	public Token getUnauthorizedToken(){
		return this.requestToken;
	}
	
	//设置第二步获取的认证的token
	public void setAuthorizedToken(Token token){
		this.authorizedRequestToken = token;
	}
	public void setAuthorizedToken(String tokenStr){
		Token authorizedRequestToken = new Token(tokenStr, null);
		setAuthorizedToken(authorizedRequestToken);
	}
	
	public Token getAuthorizedToken(){
		return this.authorizedRequestToken;
	}
	
	//设置第三部的最终token和secret
	public void setAccessedToken(Token token){
		this.accessToken = token;
	}
	public void setAccessedToken(String tokenStr, String secret){
		Token accessToken = new Token(tokenStr, secret);
		setAccessedToken(accessToken);
	}
	
	public Token getAccessedToken(){
		return this.accessToken;
	}
	
}
