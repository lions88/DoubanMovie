package com.konka.doubanmovie.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.konka.doubanmovie.Debug;
import com.konka.doubanmovie.response.PhotoResponse;
import com.konka.doubanmovie.response.ReviewResponse;
import com.konka.doubanmovie.response.SearchResponse;
import com.konka.doubanmovie.response.TopResponse;
import com.konka.doubanmovie.util.BaseURL;
import com.konka.doubanmovie.util.HttpUtils;
import com.konka.doubanmovie.util.StrUtils;

/**
 * 
 * @author 
 * @date
 * @desc
 * 		interface	
 */
public class DoubanAPI {
	
	/**
	 * 根据电影id获取电影信息
	 * 		不需要token，直接HTTP GET
	 * @param id
	 * @return
	 */
	public Result getMovieById(String id){
		if(StrUtils.isEmpty(id)){
			return invalidParamsException();
		}
		String url = BaseURL.MOVIE_URL + "subject/" + id;
		Map<String, String> resultMap;
		try {
			resultMap = HttpUtils.doGet(url, false);
		} catch (IOException e) {
			e.printStackTrace();
			return invalidIOException();
		}
		
		String statusCode = resultMap.get(HttpUtils.CODE);
		if( !statusCode.equals(HttpUtils.CODE_OK_STR)){
			return invalidHttpException();
		}
		
		String content = resultMap.get(HttpUtils.RETURN).toString();
		try {
			JSONObject json = new JSONObject(content);
			Result result = new Result();
			if(contentOk(json)){
				Movie movie = new Movie(json, false);
				result.code = Result.OK;
				result.data = movie;
			}else{
				String errCode = json.getString("code");
				result.code = Result.OK;
				result.data = new String(ErrCode.getErrMsg(errCode));
			}
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
			return invalidJSONException();
		}
	}
	
	/**
	 * keyword和tag至少有一项不为空
	 * 		基本权限movie_basic_r
	 * @param keyWord 搜索的关键字
	 * @param tag 标签
	 * @param start	  取结果的offset
	 * @param count	 一次取结果的条数
	 * @return Searcher
	 * @throws IOException 
	 */
	public Result Search(String keyword, String tag, int start, int count){
		if( StrUtils.isEmpty(keyword) && StrUtils.isEmpty(tag) ){
			return invalidParamsException();
		}
		if( start < 0 )
			start = 0;
		if( count < 0)
			count = 10;
		
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		paramsList.add(new BasicNameValuePair("start", String.valueOf(start)));
		paramsList.add(new BasicNameValuePair("count", String.valueOf(count)));
		if(!StrUtils.isEmpty(keyword)){
			paramsList.add(new BasicNameValuePair("q", StrUtils.encode(keyword)));
		}else{
			paramsList.add(new BasicNameValuePair("tag", StrUtils.encode(tag)));
		}
		
		Map<String, String> resultMap;
		try {
			resultMap = HttpUtils.doGet(BaseURL.SEARCH_URL, paramsList, true);
		} catch (IOException e) {
			e.printStackTrace();
			return invalidIOException();
		}
		
		String statusCode = resultMap.get(HttpUtils.CODE);
		if( !statusCode.equals(HttpUtils.CODE_OK_STR)){
			return invalidHttpException();
		}
		
		String content = resultMap.get(HttpUtils.RETURN).toString();
		try {
			JSONObject json = new JSONObject(content);
			Result result = new Result();
			if(contentOk(json)){
				SearchResponse search = new SearchResponse(json);
				result.code = Result.OK;
				result.data = search;
			}else{
				int errCode = json.getInt("code");
				result.code = errCode;
				result.data = new String(ErrCode.getErrMsg(errCode));
			}
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
			return invalidJSONException();
		}
	}
	
	/**
	 * 获取正在热映的影片
	 * 		所需权限：movie_premium_r
	 */
	public Result getNowPlayingMovies(){
		String url = BaseURL.NOW_PLAYING_URL;
		Map<String, String> resultMap;
		try {
			resultMap = HttpUtils.doGet(url, true);
		} catch (IOException e) {
			e.printStackTrace();
			return invalidIOException();
		}
		
		String content = resultMap.get(HttpUtils.RETURN).toString();
		
		try {
			JSONObject json = new JSONObject(content);
			Result result = new Result();
			if(contentOk(json)){
				result.code = Result.OK;
				result.data = new TopResponse(json);
			}else{
				int errCode = json.getInt("code");
				result.code = errCode;
				result.data = new String(ErrCode.getErrMsg(errCode));
			}
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
			return invalidJSONException();
		}
	}
	
	/**
	 * 获取即将上映的影片
	 * 		所需权限：movie_premium_r
	 */
	public Result getComingMovies(int start, int count){
		if( start < 0 )
			start = 0;
		if( count < 0)
			count = 20;
		
		String url = BaseURL.COMING_MOVIES_URL;
		Map<String, String> resultMap;
		try {
			resultMap = HttpUtils.doGet(url, true);
		} catch (IOException e) {
			e.printStackTrace();
			return invalidIOException();
		}
		
		String content = resultMap.get(HttpUtils.RETURN).toString();
		
		try {
			JSONObject json = new JSONObject(content);
			Result result = new Result();
			if(contentOk(json)){
				result.code = Result.OK;
				result.data = new TopResponse(json);
			}else{
				int errCode = json.getInt("code");
				result.code = errCode;
				result.data = new String(ErrCode.getErrMsg(errCode));
			}
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
			return invalidJSONException();
		}
	}
	
	/**
	 * 新片榜
	 * 		所需权限：movie_advance_r
	 */
	public Result getNewMovies(){
		String newMoviesUrl = BaseURL.NEW_MOVIES_URL;
		Map<String, String> resultMap;
		try {
			resultMap = HttpUtils.doGet(newMoviesUrl, true);
		} catch (IOException e) {
			e.printStackTrace();
			return invalidIOException();
		}
		
		String statusCode = resultMap.get(HttpUtils.CODE);
		if( !statusCode.equals(HttpUtils.CODE_OK_STR)){
			return invalidHttpException();
		}
		
		String content = resultMap.get(HttpUtils.RETURN).toString();
		try {
			JSONObject json = new JSONObject(content);
			Result result = new Result();
			if(contentOk(json)){
				result.code = Result.OK;
				result.data = new TopResponse(json);
			}else{
				int errCode = json.getInt("code");
				result.code = errCode;
				result.data = new String(ErrCode.getErrMsg(errCode));
			}
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
			return invalidJSONException();
		}
	}
	
	/**
	 * 获取电影剧照
	 * 		所需权限：movie_advance_r
	 * @param id 
	 * @return 包含photos列表
	 */
	public Result getPhotos(String id, int start, int count){
		if(StrUtils.isEmpty(id)){
			return invalidParamsException();
		}
		if( start < 0 )
			start = 0;
		if( count < 0)
			count = 10;
		String photoUrl = BaseURL.MOVIE_URL + "subject/" + id + "/photos";
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		paramsList.add(new BasicNameValuePair("start", String.valueOf(start)));
		paramsList.add(new BasicNameValuePair("count", String.valueOf(count)));
		Map<String, String> resultMap;
		try {
			resultMap = HttpUtils.doGet(photoUrl, paramsList, true);
		} catch (IOException e) {
			e.printStackTrace();
			return invalidIOException();
		}
		
		String statusCode = resultMap.get(HttpUtils.CODE);
		if( !statusCode.equals(HttpUtils.CODE_OK_STR)){
			return invalidHttpException();
		}
		
		String content = resultMap.get(HttpUtils.RETURN).toString();
		try {
			JSONObject json = new JSONObject(content);
			Result result = new Result();
			if(contentOk(json)){
				Debug.debug("DoubanAPI", "content");
				result.code = Result.OK;
				result.data = new PhotoResponse(json);
			}else{
				int errCode = json.getInt("code");
				result.code = errCode;
				result.data = new String(ErrCode.getErrMsg(errCode));
			}
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
			return invalidJSONException();
		}
	}
	
	/**
	 * 获取影评
	 * 		所需权限：movie_advance_r
	 * @param id movieId
	 * @param start 
	 * @param count
	 * @return 包含reviews列表
	 */
	public Result getReviews(String id, int start, int count){
		if(StrUtils.isEmpty(id)){
			return invalidParamsException();
		}
		if( start < 0 )
			start = 0;
		if( count < 0)
			count = 10;
		String reviewUrl = BaseURL.MOVIE_URL + "subject/" + id + "/reviews";
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		paramsList.add(new BasicNameValuePair("start", String.valueOf(start)));
		paramsList.add(new BasicNameValuePair("count", String.valueOf(count)));
		Map<String, String> resultMap;
		try {
			resultMap = HttpUtils.doGet(reviewUrl, paramsList, true);
		} catch (IOException e) {
			e.printStackTrace();
			return invalidIOException();
		}
		
		String statusCode = resultMap.get(HttpUtils.CODE);
		if( !statusCode.equals(HttpUtils.CODE_OK_STR)){
			return invalidHttpException();
		}
		
		String content = resultMap.get(HttpUtils.RETURN).toString();
		try {
			JSONObject json = new JSONObject(content);
			Result result = new Result();
			if(contentOk(json)){
				Debug.debug("DoubanAPI", "content");
				result.code = Result.OK;
				result.data = new ReviewResponse(json);
			}else{
				int errCode = json.getInt("code");
				result.code = errCode;
				result.data = new String(ErrCode.getErrMsg(errCode));
			}
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
			return invalidJSONException();
		}
	}
	
	/**
	 * 获取电影短评（少于140字的）
	 * 		所需权限：movie_advance_r
	 * @param id
	 * @param start
	 * @param count
	 * @return
	 */
	public Result getComments(String id, int start, int count){
		if(StrUtils.isEmpty(id)){
			return invalidParamsException();
		}
		if( start < 0 )
			start = 0;
		if( count < 0)
			count = 10;
		String reviewUrl = BaseURL.MOVIE_URL + "subject/" + id + "/comments";
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		paramsList.add(new BasicNameValuePair("start", String.valueOf(start)));
		paramsList.add(new BasicNameValuePair("count", String.valueOf(count)));
		Map<String, String> resultMap;
		try {
			resultMap = HttpUtils.doGet(reviewUrl, paramsList, true);
		} catch (IOException e) {
			e.printStackTrace();
			return invalidIOException();
		}
		
		String statusCode = resultMap.get(HttpUtils.CODE);
		if( !statusCode.equals(HttpUtils.CODE_OK_STR)){
			return invalidHttpException();
		}
		
		String content = resultMap.get(HttpUtils.RETURN).toString();
		try {
			JSONObject json = new JSONObject(content);
			Result result = new Result();
			if(contentOk(json)){
				Debug.debug("DoubanAPI", "content");
				result.code = Result.OK;
				result.data = new Comment(json);
			}else{
				int errCode = json.getInt("code");
				result.code = errCode;
				result.data = new String(ErrCode.getErrMsg(errCode));
			}
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
			return invalidJSONException();
		}
	}
	
	
	/**
	 * 获取top250排行榜
	 * 		注：此接口不需要token，直接HTTP GET获取内容
	 * @param start
	 * @param count
	 * @return
	 */
	public Result getTopList(int start, int count){
		if( start < 0 )
			start = 0;
		if( count < 0)
			count = 10;
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		paramsList.add(new BasicNameValuePair("start", String.valueOf(start)));
		paramsList.add(new BasicNameValuePair("count", String.valueOf(count)));
		Map<String, String> resultMap;
		try {
			resultMap = HttpUtils.doGet(BaseURL.TOP_URL, paramsList, false);
		} catch (IOException e) {
			e.printStackTrace();
			return invalidIOException();
		}
		String statusCode = resultMap.get(HttpUtils.CODE);
		if( !statusCode.equals(HttpUtils.CODE_OK_STR)){
			return invalidHttpException();
		}
		
		String content = resultMap.get(HttpUtils.RETURN).toString();
		try {
			JSONObject json = new JSONObject(content);
			Result result = new Result();
			if(contentOk(json)){
				Debug.debug("DoubanAPI", "content");
				result.code = Result.OK;
				result.data = new TopResponse(json);
			}else{
				int errCode = json.getInt("code");
				result.code = errCode;
				result.data = new String(ErrCode.getErrMsg(errCode));
			}
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
			return invalidJSONException();
		}
	}
	
	
	//返回的json数据是否正常，当包含msg和code字段时异常
	private boolean contentOk(JSONObject json){
		return json.isNull("code") && json.isNull("msg") ;
	}
	
	private Result invalidParamsException(){
		Result result = new Result();
		result.code = ErrCode.INVALID_PARAM_CODE;
		result.data = new String(ErrCode.getErrMsg(ErrCode.INVALID_PARAM_CODE));
		return result;
	}
	
	private Result invalidIOException(){
		Result result = new Result();
		result.code = ErrCode.IO_EXCEPTION_CODE;
		result.data = new String(ErrCode.getErrMsg(ErrCode.IO_EXCEPTION_CODE));
		return result;
	}
	
	private Result invalidJSONException(){
		Result result = new Result();
		result.code = ErrCode.JSON_EXCEPTION_CODE;
		result.data = new String(ErrCode.getErrMsg(ErrCode.JSON_EXCEPTION_CODE));
		return result;
	}
	
	private Result invalidHttpException(){
		Result result = new Result();
		result.code = ErrCode.HTTP_EXCEPTION_CODE;
		result.data = new String(ErrCode.getErrMsg(ErrCode.HTTP_EXCEPTION_CODE));
		return result;
	}
}
