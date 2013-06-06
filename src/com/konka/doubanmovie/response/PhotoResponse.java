package com.konka.doubanmovie.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.konka.doubanmovie.api.Movie;
import com.konka.doubanmovie.api.Photo;

/**
 * 
 * @author 
 * @date
 * @desc 
 *		获取电影剧照API返回的结果
 */
public class PhotoResponse extends Response{
	public int start;
	public int count;
	public int total;
	public Movie subject;
	public Photo[] photos;
	
	public PhotoResponse(){

	}
	
	public PhotoResponse(JSONObject json) throws JSONException{
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
		
		if( !json.isNull("photos") ){
			JSONArray jsonArray = json.getJSONArray("photos");
			int len = jsonArray.length();
			photos = new Photo[len];
			for(int i=0; i<len; i++){
				photos[i] = new Photo(jsonArray.getJSONObject(i));
			}
		}
	}
}
