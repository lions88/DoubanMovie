package com.konka.doubanmovie.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author 
 * @date
 * @desc
 * 		用户
 */
public class User{
	//个人主页url
	private String alt;
	//头像小图
	private String avatar;
	private String id;
	private String name;
	private String signature;
	private String uid;
	
	public User(){
		
	}
	public User(JSONObject json) throws JSONException{
		init(json);
	}
	
	public String toString(){
		return "[User(name="+name+")]";
	}
	
	public void init(JSONObject json) throws JSONException{
		if( !json.isNull("alt") ){
			alt = json.getString("alt");
		}
		if( !json.isNull("avatar") ){
			avatar = json.getString("avatar");
		}
		if( !json.isNull("id") ){
			id = json.getString("id");
		}
		if( !json.isNull("name") ){
			name = json.getString("name");
		}
		if( !json.isNull("signature") ){
			signature = json.getString("signature");
		}
		if( !json.isNull("uid") ){
			uid = json.getString("uid");
		}
	}
	
	public String getAlt(){
		return alt;
	}
	public String getAvatar(){
		return avatar;
	}
	public String getId(){
		return id;
	}
	public String getName(){
		return name;
	}
	public String getSignature(){
		return signature;
	}
	public String getUid(){
		return uid;
	}
}
