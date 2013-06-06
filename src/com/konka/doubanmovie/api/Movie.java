package com.konka.doubanmovie.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author 
 * @date
 * @desc
 * 		电影entity
 */
public class Movie{
	//private static final String TAG = "Movie";
	//条目id
	private String id;
	//中文名
	private String title;
	//原名
	private String originalTitle;
	//条目页url
	private String alt;
	//电影海报图，分别提供288px x 465px(大)，96px x 155px(中) 64px x 103px(小)尺寸
	private String largeImgUrl;
	private String mediumImgUrl;
	private String smallImgUrl;
	//评分
	private Rating rating;
	//如果条目类型是电影则为上映日期，如果是电视剧则为首播日期
	private String[] pubDates;
	//年代
	private String year;
	//条目分类, movie或者tv
	private String subtype;
	
	//==================以上是simple版的有效字段==================//
	
	//又名
	private String[] aka;
	//移动版条目页url
	private String mobileUrl;
	//评分人数
	private int ratingsCount;
	//想看人数
	private int wishCount;
	//看过人数
	private int collectCount;
	//在看人数，如果是电视剧，默认值为0，如果是电影值为null
	private String doCount;
	//导演
	private Celebrity[] directors;
	//主演，最多可获得4个
	private Celebrity[] casts;
	//编剧
	private Celebrity[] writers;
	//官方网站
	private String website;
	//豆瓣小站
	private String doubanSite;
	//语言
	private String[] languages;
	//片长
	private String[] durations;
	//影片类型，最多提供3个
	private String[] genres;
	//制片国家/地区
	private String[] countries;
	//简介
	private String summary;
	//短评数量
	private int commentsCount;
	//影评数量
	private int reviewsCount;
	//总季数
	private String seasonsCount;
	//当前第几季(tv only)
	private String currentSeason;
	//影讯页URL(movie only)
	private String scheduleUrl;
	//预告片URL，对高级用户以上开放，最多开放4个地址
	private String[] trailerUrls;
	//电影剧照，前10张
	private Photo[] photos;
	//影评，前10条
	private Review[] popularReviews;
	
	public Movie(){
		
	}
	//@param simple true返回简单类型的电影实体数据，false返回完整数据
	public Movie(JSONObject json, boolean simple) throws JSONException{
		init(json, simple);
	}
	
	public String[] getAka(){
		return aka;
	}
	
	public String getAlt(){
		return alt;
	}
	
	public int getCollectCount(){
		return collectCount;
	}
	
	public int getCommentsCount(){
		return commentsCount;
	}
	
	public String[] getCountries(){
		return countries;
	}
	
	public String getCurrentSeason(){
		return currentSeason;
	}
	
	public Celebrity[] getDirectors(){
		return directors;
	}
	
	public Celebrity[] getCasts(){
		return casts;
	}
	
	public Celebrity[] getWriters(){
		return writers;
	}
	
	public String getDoCount(){
		return doCount;
	}
	
	public String getDoubanSite(){
		return doubanSite;
	}
	
	public String[] getDurations(){
		return durations;
	}
	
	public String[] getGenres(){
		return genres;
	}
	
	public String getId(){
		return id;
	}
	
	public String getLargeImgUrl(){
		return largeImgUrl;
	}
	
	public String getMediumImgUrl(){
		return mediumImgUrl;
	}
	
	public String getSmallImgUrl(){
		return smallImgUrl;
	}
	
	public String[] getPubDates(){
		return pubDates;
	}
	
	public String getMobileUrl(){
		return mobileUrl;
	}
	
	public String getOriginalTitle(){
		return originalTitle;
	}
	
	public Photo[] getPhotos(){
		return photos;
	}
	
	public Review[] getPopularReviews(){
		return popularReviews;
	}
	
	public Rating getRating(){
		return rating;
	}
	
	public int getRatingsCount(){
		return ratingsCount;
	}
	
	public int getReviewsCount(){
		return reviewsCount;
	}
	
	public String getScheduleUrl(){
		return scheduleUrl;
	}
	
	public String getSeasonsCount(){
		return seasonsCount;
	}
	
	public String getSubtype(){
		return subtype;
	}
	
	public String getSummary(){
		return summary;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String[] getTrailerUrls(){
		return trailerUrls;
	}

	public String getWebsite(){
		return website;
	}
	
	public int getWishCount(){
		return wishCount;
	}
	
	public String getYear(){
		return year;
	}
	
	public String[] getLanguages(){
		return languages;
	}
	
	public void init(JSONObject json, boolean simple) throws JSONException{
		int size;
		int i;
		JSONArray jsonArray;
		String key;
		
		key = "id";
		if( !json.isNull(key) ){
			id = json.getString(key);
			//Debug.debug(TAG, "id="+id);
		}
		
		key = "title";
		if( !json.isNull(key) ){
			title = json.getString(key);
			//Debug.debug(TAG, "title="+title);
		}
		
		key = "original_title";
		if( !json.isNull(key) ){
			originalTitle = json.getString(key);
			//Debug.debug(TAG, "originalTitle="+originalTitle);
		}
		
		key = "alt";
		if( !json.isNull(key) ){
			alt = json.getString(key);
			//Debug.debug(TAG, "alt=" + alt);
		}
		
		key = "images";
		if( !json.isNull(key) ){
			JSONObject jsonImg = json.getJSONObject(key);
			key = "small";
			if( !jsonImg.isNull(key) ){
				smallImgUrl = jsonImg.getString(key);
				//Debug.debug(TAG, "smallImgUrl="+smallImgUrl);
			}
			key = "large";
			if( !jsonImg.isNull(key) ){
				largeImgUrl = jsonImg.getString(key);
				//Debug.debug(TAG, "largeImgUrl="+largeImgUrl);
			}
			key = "medium";
			if( !jsonImg.isNull(key) ){
				mediumImgUrl = jsonImg.getString(key);
				//Debug.debug(TAG, "mediumImgUrl="+mediumImgUrl);
			}
		}
		
		key = "rating";
		if( !json.isNull(key) ){
			rating = new Rating(json.getJSONObject(key));
			//Debug.debug(TAG, "rating="+rating);
		}
		
		key = "pubdates";
		if( !json.isNull(key)){
			jsonArray = new JSONArray(json.getString(key));
			size = jsonArray.length();
			pubDates = new String[size];
			for(i=0; i<size; i++){
				pubDates[i] = jsonArray.getString(i);
				//Debug.debug(TAG, "pubDates["+i+"]=" + pubDates[i]);
			}
		}
		
		key = "year";
		if( !json.isNull(key) ){
			year = json.getString(key);
			//Debug.debug(TAG, "year=" + year);
		}
		
		key = "subtype";
		if( !json.isNull(key) ){
			subtype = json.getString(key);
			//Debug.debug(TAG, "subtype="+subtype);
		}
		
		if(simple){
			return;
			//=========================以上是simple类型返回==========================//
		}
		
		key = "aka";
		if( !json.isNull(key) ){
			jsonArray = new JSONArray(json.getString(key));
			size = jsonArray.length();
			aka = new String[size];
			for(i=0; i<size; i++){
				aka[i] = jsonArray.getString(i);
				//Debug.debug(TAG, "aka["+i+"]=" + aka[i]);
			}
		}
		
		key = "collect_count";
		if( !json.isNull(key) ){
			collectCount = json.getInt(key);
			//Debug.debug(TAG, "collectCount=" + collectCount);
		}
		
		key = "commnets_count";
		if( !json.isNull(key)){
			commentsCount = json.getInt(key);
			//Debug.debug(TAG, "commentsConut=" + commentsCount);
		}
		
		key = "countries";
		if( !json.isNull(key) ){
			jsonArray = new JSONArray(json.getString(key));
			size = jsonArray.length();
			countries = new String[size];
			for(i=0; i<size; i++){
				countries[i] = jsonArray.getString(i);
				//Debug.debug(TAG, "countries["+i+"]=" + countries[i]);
			}
		}
		
		key = "current_season";
		if( !json.isNull(key) ){
			currentSeason = json.getString(key);
			//Debug.debug(TAG, "currentSeason=" + currentSeason);
		}
		
		key = "directors";
		if( !json.isNull(key) ){
			jsonArray = new JSONArray(json.getString(key));
			size = jsonArray.length();
			directors = new Celebrity[size];
			for(i=0; i<size; i++){
				directors[i] = new Celebrity(jsonArray.getJSONObject(i), true);
				//Debug.debug(TAG, "directors["+i+"]=" + directors[i]);
			}
		}
		
		key = "casts";
		if( !json.isNull(key) ){
			jsonArray = new JSONArray(json.getString(key));
			size = jsonArray.length();
			casts = new Celebrity[size];
			for(i=0; i<size; i++){
				casts[i] = new Celebrity(jsonArray.getJSONObject(i), true);
				//Debug.debug(TAG, "casts["+i+"]=" + casts[i]);
			}
		}
		
		key = "writers";
		if( !json.isNull(key) ){
			jsonArray = new JSONArray(json.getString(key));
			size = jsonArray.length();
			writers = new Celebrity[size];
			for(i=0; i<size; i++){
				writers[i] = new Celebrity(jsonArray.getJSONObject(i), true);
				//Debug.debug(TAG, "writers["+i+"]=" + writers[i]);
			}
		}
		
		key = "do_count";
		if( !json.isNull(key) ){
			doCount = json.getString(key);
			//Debug.debug(TAG, "doCount=" + doCount);
		}
		
		key = "douban_site";
		if( !json.isNull(key)){
			doubanSite = json.getString(key);
			//Debug.debug(TAG, "doubanSite=" + doubanSite);
		}
		
		key = "durations";
		if( !json.isNull(key) ){
			jsonArray = new JSONArray(json.getString(key));
			size = jsonArray.length();
			durations = new String[size];
			for(i=0; i<size; i++){
				durations[i] = jsonArray.getString(i);
				//Debug.debug(TAG, "durations["+i+"]=" + durations[i]);
			}
		}
		
		key = "genres";
		if( !json.isNull(key) ){
			jsonArray = new JSONArray(json.getString(key));
			size = jsonArray.length();
			genres = new String[size];
			for(i=0; i<size; i++){
				genres[i] = jsonArray.getString(i);
				//Debug.debug(TAG, "genres["+i+"]=" + genres[i]);
			}
		}
		
		key = "languages";
		if( !json.isNull(key)){
			jsonArray = new JSONArray(json.getString(key));
			size = jsonArray.length();
			languages = new String[size];
			for(i=0; i<size; i++){
				languages[i] = jsonArray.getString(i);
				//Debug.debug(TAG, "languages["+i+"]=" + languages[i]);
			}
		}
		
		key = "mobile_url";
		if( !json.isNull(key) ){
			mobileUrl = json.getString(key);
			//Debug.debug(TAG, "mobileUrl="+mobileUrl);
		}
		
		key = "photos";
		if( !json.isNull(key) ){
			jsonArray = new JSONArray(json.getString(key));
			size = jsonArray.length();
			photos = new Photo[size];
			for(i=0; i<size; i++){
				photos[i] = new Photo(jsonArray.getJSONObject(i));
				//Debug.debug(TAG, "photos["+i+"]=" + photos[i]);
			}
		}
		
		key = "popular_reviews";
		if( !json.isNull(key) ){
			jsonArray = new JSONArray(json.getString(key));
			size = jsonArray.length();
			popularReviews = new Review[size];
			for(i=0; i<size; i++){
				popularReviews[i] = new Review(jsonArray.getJSONObject(i));
				//Debug.debug(TAG, "popularReviews["+i+"]=" + popularReviews[i]);
			}
		}
		
		key = "ratings_count";
		if( !json.isNull(key) ){
			ratingsCount = json.getInt(key);
			//Debug.debug(TAG, "ratingsCount="+ratingsCount);
		}
		
		key = "reviews_count";
		if( !json.isNull(key) ){
			reviewsCount = json.getInt(key);
			//Debug.debug(TAG, "reviewsCount="+reviewsCount);
		}
		
		key = "schedule_url";
		if( !json.isNull(key) ){
			scheduleUrl = json.getString(key);
			//Debug.debug(TAG, "scheduleUrl="+scheduleUrl);
		}
		
		key = "seasons_count";
		if( !json.isNull(key) ){
			seasonsCount = json.getString(key);
			//Debug.debug(TAG, "seasonsCount="+seasonsCount);
		}
		
		key = "summary";
		if( !json.isNull(key) ){
			summary = json.getString(key);
		}
		
		key = "trailer_urls";
		if( !json.isNull(key) ){
			jsonArray = new JSONArray(json.getString(key));
			size = jsonArray.length();
			trailerUrls = new String[size];
			for(i=0; i<size; i++){
				trailerUrls[i] = jsonArray.getString(i);
				//Debug.debug(TAG, "trailerUrls["+i+"]=" + trailerUrls[i]);
			}
		}
		
		key = "website";
		if( !json.isNull(key) ){
			website = json.getString(key);
			//Debug.debug(TAG, "website=" + website);
		}
		
		key = "wish_count";
		if( !json.isNull(key) ){
			wishCount = json.getInt(key);
			//Debug.debug(TAG, "wishCount=" + wishCount);
		}
	}
}
