package com.konka.doubanmovie.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.konka.doubanmovie.api.Comment;
import com.konka.doubanmovie.api.Movie;

/**
 * 
 * @author 
 * @date
 * @desc
 * 		调用获取短评接口返回的数据结构
 */
public class CommentResponse extends Response{
	public int start;
	public int count;
	public int total;
	public Movie subject;
	public Comment[] comments;
	
	public CommentResponse(){
		
	}
	
	public CommentResponse(JSONObject json) throws JSONException{
		if( !json.isNull("start") ){
			start = json.getInt("start");
		}
		
		if( !json.isNull("count") ){
			count = json.getInt("count");
		}
		
		if( !json.isNull("total") ){
			total = json.getInt("total");
		}
		
		if( !json.isNull("subject")){
			subject = new Movie(json.getJSONObject("subject"), true);
		}
		
		if( !json.isNull("comments") ){
			JSONArray jsonArray = json.getJSONArray("comments");
			int len = jsonArray.length();
			comments = new Comment[len];
			for(int i=0; i<len; i++){
				comments[i] = new Comment(jsonArray.getJSONObject(i));
			}
		}
	}
}
