package com.konka.doubanmovie.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author 
 * @date
 * @desc
 * 		影人信息（演员、导演、剧本作者等）
 */
public class Celebrity {
	private String id;
	private String name;
	//条目页URL
	private String alt;
	//影人头像，分别提供420px x 600px(大)，140px x 200px(中) 70px x 100px(小)尺寸
	private String largeAvatar;
	private String mediumAvatar;
	private String smallAvatar;
	//英文名
	private String name_en;
	//移动版条目页URL
	private String mobileUrl;
	//简介
	private String summary;
	//更多中文名
	private String[] aka;
	//更多英文名
	private String[] aka_en;
	//官方网站
	private String website;
	//性别
	private String gender;
	//生日
	private String birthday;
	//出生地
	private String bornPlace;
	//星座
	private String constellation;
	//影人剧照，最多10张
	private Photo[] photos;
	//职业
	private String[] professions;
	//影人作品，最多5部
	private Movie[] works;
	
	public Celebrity(){
		
	}
	
	//@param simple 是否是简单类型数据，（只有id,name,alt,avatars有效）
	public Celebrity(JSONObject json, boolean simple) throws JSONException{
		init(json, simple);
	}
	
	public String toString(){
		return "[Celebrity(name=" + name + ", alt=" + alt + ", large=" + largeAvatar + ")]";
	}
	
	public String[] getAka(){
		return aka;
	}
	
	public String[] getAkaEn(){
		return aka_en;
	}
	
	public String getAlt(){
		return alt;
	}
	
	public String getLargeAvatar(){
		return largeAvatar;
	}
	
	public String getMediumAvatar(){
		return mediumAvatar;
	}
	
	public String getSmallAvatar(){
		return smallAvatar;
	}
 	
	public String getBirthday(){
		return birthday;
	}
	
	public String getBornPlace(){
		return bornPlace;
	}
	
	public String getConstellation(){
		return constellation;
	}
	
	public String getGender(){
		return gender;
	}
	
	public String getId(){
		return id;
	}
	
	public String getMobileUrl(){
		return mobileUrl;
	}
	
	public String getName(){
		return name;
	}
	
	public String getNameEn(){
		return name_en;
	}
	
	public Photo[] getPhotos(){
		return photos;
	}
	
	public String[] getProfessions(){
		return professions;
	}
	
	public Movie[] getWorks(){
		return works;
	}
	
	public String getSummary(){
		return summary;
	}
	
	public String getWebsite(){
		return website;
	}
	
	public void init(JSONObject json, boolean simple) throws JSONException{
		JSONArray jsonArray;
		int i, size;
		
		if( !json.isNull("id") ){
			id = json.getString("id");
		}
		
		if( !json.isNull("name") ){
			name = json.getString("name");
		}
		
		if( !json.isNull("alt") ){
			alt = json.getString("alt");
		}
		
		if( !json.isNull("avatars") ){
			JSONObject jsonAvatar = new JSONObject(json.getString("avatars"));
			if( !jsonAvatar.isNull("large") ){
				largeAvatar = jsonAvatar.getString("large");
			}
			if( !jsonAvatar.isNull("small") ){
				smallAvatar = jsonAvatar.getString("small");
			}
			if( !jsonAvatar.isNull("medium") ){
				mediumAvatar = jsonAvatar.getString("medium");
			}
		}
		
		if(simple){
			return;
			//===========================================================//
		}
		
		if( !json.isNull("aka") ){
			jsonArray = new JSONArray(json.getString("aka"));
			size = jsonArray.length();
			aka = new String[size];
			for(i=0; i<size; i++){
				aka[i] = jsonArray.getString(i);
			}
		}
		
		if( !json.isNull("aka_en") ){
			jsonArray = new JSONArray(json.getString("aka_en"));
			size = jsonArray.length();
			aka_en = new String[size];
			for(i=0; i<size; i++){
				aka_en[i] = jsonArray.getString(i);
			}
		}
		
		if( !json.isNull("birthday") ){
			birthday = json.getString("birthday");
		}
		
		if( !json.isNull("born_place") ){
			bornPlace = json.getString("born_place");
		}
		
		if( !json.isNull("constellation") ){
			constellation = json.getString("constellation");
		}
	
		if( !json.isNull("gender") ){
			gender = json.getString("gender");
		}
		
		if( !json.isNull("mobile_url") ){
			mobileUrl = json.getString("mobile_url");
		}
		
		if( !json.isNull("name_en") ){
			name_en = json.getString("name_en");
		}
		
		if( !json.isNull("photos") ){
			jsonArray = new JSONArray(json.getString("photos"));
			size = jsonArray.length();
			photos = new Photo[size];
			for(i=0; i<size; i++){
				photos[i] = new Photo(jsonArray.getJSONObject(i));
			}
		}
		
		if( !json.isNull("professions") ){
			jsonArray = new JSONArray(json.getString("professions"));
			size = jsonArray.length();
			professions = new String[size];
			for(i=0; i<size; i++){
				professions[i] = jsonArray.getString(i);
			}
		}
		
		if( !json.isNull("summary") ){
			summary = json.getString("summary");
		}
		
		if( !json.isNull("website") ){
			website = json.getString("website");
		}
		
		if( !json.isNull("works") ){
			jsonArray = new JSONArray(json.getString("works"));
			size = jsonArray.length();
			works = new Movie[size];
			for(i=0; i<size; i++){
				works[i] = new Movie(jsonArray.getJSONObject(i), true);
			}
		}
	}
}
