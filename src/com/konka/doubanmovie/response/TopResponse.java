package com.konka.doubanmovie.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.konka.doubanmovie.api.Movie;

/**
 * 
 * @author 
 * @desc
 * 		top250电影排行榜、北美排行榜、口碑榜接口返回通用数据结构体
 */
public class TopResponse extends Response{
	
	public int start;
	public int count;
	public int total;
	public String date;
	
	//排行榜名称
	public String title;		//not null.
	//电影列表
	public Movie[] subjects;	//not null.
	
	public TopResponse(){
		
	}
	
	public TopResponse(JSONObject json) throws JSONException{
		if( !json.isNull("start") ){
			start = json.getInt("start");
		}
		
		if( !json.isNull("count") ){
			count = json.getInt("count");
		}
		
		if( !json.isNull("total") ){
			total = json.getInt("total");
		}
		
		if( !json.isNull("ttile") ){
			title = json.getString("title");
		}
		
		if( !json.isNull("subjects") ){
			JSONArray jsonArray = new JSONArray(json.getString("subjects"));
			int len = jsonArray.length();
			subjects = new Movie[len];
			for(int i=0; i<len; i++){
				subjects[i] = new Movie(jsonArray.getJSONObject(i), true);
			}
		}else if(!json.isNull("entries")){
			JSONArray jsonArray = new JSONArray(json.getString("entries"));
			int len = jsonArray.length();
			subjects = new Movie[len];
			for(int i=0; i<len; i++){
				subjects[i] = new Movie(jsonArray.getJSONObject(i), true);
			}
		}
		
		if( !json.isNull("date") ){
			date = json.getString("date");
		}
	}
}
