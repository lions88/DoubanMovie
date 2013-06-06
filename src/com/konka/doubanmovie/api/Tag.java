package com.konka.doubanmovie.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author 
 * @date
 * @desc
 * 		电影标签
 */
public class Tag{
	public int count;
	public String alt;
	public String title;
	
	public Tag(){
		
	}
	
	public Tag(JSONObject json) throws JSONException{
		init(json);
	}
	
	public void init(JSONObject json) throws JSONException{
		if( !json.isNull("count") ){
			count = json.getInt("count");
		}
		
		if( !json.isNull("alt") ){
			alt = json.getString("alt");
		}
		
		if( !json.isNull("title") ){
			title = json.getString("title");
		}
	}
	
	public int getCount(){
		return count;
	}
	
	public String getAlt(){
		return alt;
	}
	
	public String getTitle(){
		return title;
	}
}
