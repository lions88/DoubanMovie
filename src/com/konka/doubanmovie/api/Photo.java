package com.konka.doubanmovie.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author 
 * @date
 * @desc 
 * 		图片属性
 */
public class Photo{
	//图片id
	private String id;
	//条目id
	private String subjectId;
	//图片展示页url
	private String alt;
	//图片地址，icon尺寸
	private String icon;
	//图片地址，image尺寸
	private String imageUrl;
	//图片地址，thumb尺寸
	private String thumb;
	//封面
	private String cover;
	//创建时间
	private String createdAt;
	//描述
	private String desc;
	//上传用户
	private User author;
	//相册id	
	private String albumId;
	//相册标题
	private String albumTitle;
	//相册地址
	private String albumUrl;
	//下一张图片
	private String nextPhotoId;
	//上一张图片
	private String prevPhotoId;
	//图片在相册中的位置，按照时间排序
	private int position;
	//评论数
	private int commentsCount;
	//评论数
	private int photosCount;
	//全部剧照数量
	private int recsCount;
	
	public Photo(){
		
	}
	
	public Photo(JSONObject json) throws JSONException{
		init(json);
	}
	
	public String toString(){
		return "[Photo(url=" + imageUrl + ")]";
	}
	
	public String getAlbumId(){
		return albumId;
	}
	
	public String getAlbumTitle(){
		return albumTitle;
	}
	
	public String getAlbumUrl(){
		return albumUrl;
	}
	
	public String getAlt(){
		return alt;
	}
	
	public User getAuthor(){
		return author;
	}
	
	public int getCommentsCount(){
		return commentsCount;
	}
	
	public String getCover(){
		return cover;
	}
	
	public String getCreatedAt(){
		return createdAt;
	}
	
	public String getDesc(){
		return desc;
	}
	
	public String getIcon(){
		return icon;
	}
	
	public String getId(){
		return id;
	}
	
	public String getImageUrl(){
		return imageUrl;
	}
	
	public String getNextPhotoId(){
		return nextPhotoId;
	}
	
	public int getPhotosCount(){
		return photosCount;
	}
	
	public int getPosition(){
		return position;
	}
	
	public String getPrevPhotoId(){
		return prevPhotoId;
	}
	
	public int getResCount(){
		return recsCount;
	}
	
	public String getSubjectId(){
		return subjectId;
	}
	
	public String getThumb(){
		return thumb;
	}
	
	public void init(JSONObject json) throws JSONException{
		if( !json.isNull("album_id") ){
			albumId = json.getString("album_id");
		}
		
		if( !json.isNull("album_title") ){
			albumTitle = json.getString("album_title");
		}
		
		if( !json.isNull("album_url") ){
			albumUrl = json.getString("album_url");
		}
		
		if( !json.isNull("alt") ){
			alt = json.getString("alt");
		}
		
		if( !json.isNull("author") ){
			author = new User(json.getJSONObject("author"));
		}
		
		if( !json.isNull("comments_count") ){
			commentsCount = json.getInt("comments_count");
		}
		
		if( !json.isNull("cover") ){
			cover = json.getString("cover");
		}
		
		if( !json.isNull("created_at") ){
			createdAt = json.getString("created_at");
		}
		
		if( !json.isNull("desc") ){
			desc = json.getString("desc");
		}
		
		if( !json.isNull("icon") ){
			icon = json.getString("icon");
		}
		
		if( !json.isNull("id") ){
			id = json.getString("id");
		}
		
		if( !json.isNull("image") ){
			imageUrl = json.getString("image");
		}
		
		if( !json.isNull("next_photo") ){
			nextPhotoId = json.getString("next_photo");
		}
		
		if( !json.isNull("photos_count") ){
			photosCount = json.getInt("photos_count");
		}
		
		if( !json.isNull("position") ){
			position = json.getInt("position");
		}
		
		if( !json.isNull("prev_photo") ){
			prevPhotoId = json.getString("prev_photo");
		}
		
		if( !json.isNull("recs_count") ){
			recsCount = json.getInt("recs_count");
		}
		
		if( !json.isNull("subject_id") ){
			subjectId = json.getString("subject_id");
		}
		
		if( !json.isNull("thumb") ){
			thumb = json.getString("thumb");
		}
	}
}
