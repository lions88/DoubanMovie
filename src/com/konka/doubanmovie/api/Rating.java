package com.konka.doubanmovie.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author 
 * @date
 * @desc
 * 		电影评分）
 */
public class Rating{
	//最大值（目前5）
	public int max;
	//最小指（目前0）
	public int min;
	//评分值
	public float average;
	
	public String stars;
	
	public Rating(){
		
	}
	public Rating(JSONObject json) throws JSONException{
		init(json);
	}
	
	public String toString(){
		return "[Rating(max=" + max + ", min=" + min + ", average=" + average + ", stars=" + stars +")]"; 
	}
	
	public void init(JSONObject json) throws JSONException{
		/*if( !json.isNull("max_rating") ){
			max = json.getInt("max_rating");
		}
		else*/ if( !json.isNull("max") ){
			max = json.getInt("max");
		}
		
		/*if( !json.isNull("min_rating") ){
			min = json.getInt("min_rating");
		}
		else*/ if( !json.isNull("min") ){
			min = json.getInt("min");
		}
		
		if( !json.isNull("value") ){
			average = json.getInt("value");
		}
		else if( !json.isNull("average") ){
			average = json.getLong("average");
		}
		
		if( !json.isNull("stars") ){
			stars = json.getString("stars");
		}
	}
}
