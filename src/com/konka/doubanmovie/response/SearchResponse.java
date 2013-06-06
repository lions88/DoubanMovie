package com.konka.doubanmovie.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.konka.doubanmovie.api.Movie;

/**
 * 
 * @author 
 * @date
 * @desc 搜索API返回的数据结构
 * 
 */
public class SearchResponse extends Response{
	public String query;
	public String tag;
	public int start;
	public int count;
	public int total;
	public Movie[] subjects;
	
	public SearchResponse(){
		
	}
	
	public SearchResponse(JSONObject json) throws JSONException{
		init(json);
	}
	
	public void init(JSONObject json) throws JSONException{
		if( !json.isNull("query") ){
			query = json.getString("query");
		}
		
		if( !json.isNull("tag") ){
			tag = json.getString("tag");
		}
		
		if( !json.isNull("start") ){
			start = json.getInt("start");
		}
		
		if( !json.isNull("count") ){
			count = json.getInt("count");
		}
		
		if( !json.isNull("total") ){
			total = json.getInt("total");
		}
		
		if( !json.isNull("subjects") ){
			JSONArray jsonArray = new JSONArray(json.getString("subjects"));
			int size = jsonArray.length();
			subjects = new Movie[size];
			for(int i=0; i<size; i++){
				subjects[i] = new Movie(jsonArray.getJSONObject(i), true); 
			}
		}
	}
	
	public String getKeyword(){
		return query;
	}
	
	public String getTag(){
		return tag;
	}
	
	public int getStart(){
		return start;
	}
	
	public int getCount(){
		return count;
	}
	
	public int getTotal(){
		return total;
	}
	
	public Movie[] getSubjects(){
		return subjects;
	}
}
