package com.konka.doubanmovie.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.konka.doubanmovie.api.Movie;
import com.konka.doubanmovie.api.Review;

/**
 * 
 * @author 
 * @date
 * @desc
 * 		调用获取影评接口返回的数据结构
 */
public class ReviewResponse extends Response{
	public int start;
	public int count;
	public int total;
	public Movie subject;
	public Review[] reviews;
	
	public ReviewResponse(){
		
	}
	
	public ReviewResponse(JSONObject json) throws JSONException{
		init(json);
	}
	
	public void init(JSONObject json) throws JSONException{
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
		
		if( !json.isNull("reviews") ){
			JSONArray jsonArray = json.getJSONArray("reviews");
			int len = jsonArray.length();
			reviews = new Review[len];
			for(int i=0; i<len; i++){
				reviews[i] = new Review(jsonArray.getJSONObject(i));
			}
		}
	}
}
