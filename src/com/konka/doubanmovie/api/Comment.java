package com.konka.doubanmovie.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author 
 * @date 
 * @desc
 * 		电影短评
 */
public class Comment {
	//短评id
	public String id;
	//创建时间
	public String createdAt;
	//电影id
	public String subjectId;
	//短评作者
	public User author;
	//短评内容,140字以内
	public String content;
	//评分
	public Rating rating;
	//有用数
	public int usefulCount;
	
	public Comment(){
		
	}
	
	public Comment(JSONObject json) throws JSONException{
		init(json);
	}
	
	public void init(JSONObject json) throws JSONException{
		if( !json.isNull("id") ){
			id = json.getString("id");
		}
		
		if( !json.isNull("created+at") ){
			createdAt = json.getString("created_at");
		}
		
		if( !json.isNull("subject_id") ){
			subjectId = json.getString("subject_id");
		}
		
		if( !json.isNull("author") ){
			author = new User(json.getJSONObject("author"));
		}
		
		if( !json.isNull("content") ){
			content = json.getString("content");
		}
		
		if( !json.isNull("rating") ){
			rating = new Rating(json.getJSONObject("rating"));
		}
		
		if( !json.isNull("useful_count") ){
			usefulCount = json.getInt("useful_count");
		}
	}
}
