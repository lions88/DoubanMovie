package com.konka.doubanmovie.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author 
 * @date
 * @desc
 * 		电影影评（长评，大于140字的评论）
 */
public class Review{
	//打分
	private Rating rating;
	//更新时间
	private String update;
	//发表时间
	private String published;
	//作者
	private User author;
	//影评标题
	private String title;
	//赞同次数
	private int votes;
	//不赞同次数
	private int useless;
	//影评的回复数
	private int comments;
	//影评内容
	private String summary;
	//影评对应的电影
	private Movie movie;
	private String id;
	private String alt;
	
	public Review(){
		
	}
	public Review(JSONObject json) throws JSONException{
		init(json);
	}
	
	public String toString(){
		return "[Review(title=" + title+ ")]"; 
	}
	
	public void init(JSONObject json) throws JSONException{
		if( !json.isNull("rating") ){
			try {
				rating = new Rating(json.getJSONObject("rating"));
			} catch (JSONException e) {
				e.printStackTrace();
				rating = null;
			}
		}
		
		if( !json.isNull("update") ){
			update = json.getString("update");
		}
		if( !json.isNull("published") ){
			published = json.getString("published");
		}
		
		if( !json.isNull("author")){
			try {
				author = new User(json.getJSONObject("author"));
			} catch (JSONException e) {
				e.printStackTrace();
				author = null;
			}
		}
		
		if( !json.isNull("title") ){
			title = json.getString("title");
		}
		
		if( !json.isNull("votes") ){
			votes = json.getInt("votes");
		}
		if( !json.isNull("useless") ){
			useless = json.getInt("useless");
		}
		
		if( !json.isNull("comments") ){
			comments = json.getInt("comments");
		}
		
		if( !json.isNull("summary") ){
			summary = json.getString("summary");
		}
		
		if( !json.isNull("movie") ){
			try {
				movie = new Movie(json.getJSONObject("movie"), true);
			} catch (JSONException e) {
				e.printStackTrace();
				movie = null;
			}
		}
		
		if( !json.isNull("alt")){
			alt = json.getString("alt");
		}
		
		if( !json.isNull("id") ){
			id = json.getString("id");
		}
	}
	
	public Rating getRating(){
		return rating;
	}
	
	public String getUpadate(){
		return update;
	}
	
	public String getPublished(){
		return published;
	}
	
	public User getAuthor(){
		return author;
	}
	
	public String getTitle(){
		return title;
	}
	
	public int getVotes(){
		return votes;
	}
	
	public int getUseless(){
		return useless;
	}
	
	public int getComments(){
		return comments;
	}
	
	public String getSummary(){
		return summary;
	}
	
	public Movie getMovie(){
		return movie;
	}
	
	public String getId(){
		return id;
	}
	
	public String getAlt(){
		return alt;
	}
}
