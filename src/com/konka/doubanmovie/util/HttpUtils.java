package com.konka.doubanmovie.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.konka.doubanmovie.Debug;
import com.konka.doubanmovie.oauth.OAuth2;
import com.konka.doubanmovie.oauth.OAuth2Client;

/**
 * 
 * @author 
 * @date 
 * @desc http相关辅助类
 * 
 */
public class HttpUtils {
	private static final String TAG = "HttpUtils";
	public static final int TIMEOUT = 15000;
	public static final String METHOD_GET = "GET";
	public static final String METHOD_POST = "POST";
	
	public static final String CODE = "code";
	public static final String RETURN = "return";

	public static final String CODE_OK_STR = "200";
	public static final int CODE_OK = 200;
	public static final String CODE_BAD_REQUEST = "400";
	public static final String CODE_UNAUTHORIZED = "401";	
	public static final String CODE_FORBIDDEN = "403";
	public static final String CODE_NOT_FOUND = "404";
	
	private static HttpClient sHttpClient ;
	
	public static HttpClient getHttpClient(){
		return sHttpClient;
	}
	
	//初始化网络连接
	public static void initConnection(){
		if(sHttpClient == null){
			sHttpClient = new DefaultHttpClient();
		}
	}
	
	//关闭网络连接，在退出程序时调用
	public static void shutDownConnection(){
		if(sHttpClient == null)
			return;
		new Thread(){
			public void run(){
				sHttpClient.getConnectionManager().shutdown();
				sHttpClient = null;
			}
		}.start();
	}
	
	//http get 请求
	public static Map<String, String> doGet(String baseUrl, List<NameValuePair> paramsList, 
			boolean needToken) throws IOException{
		String urlStr = baseUrl;
		if(paramsList != null){
			urlStr += "?" + StrUtils.getStringParams(paramsList);
		}
		return doGet(urlStr, needToken);
	}
	//此方法调用豆瓣API必须设置请求头代理，否则返回500错误
	/*
	 * 有的网站会先判别用户的请求是否是来自浏览器，如不是，则返回不正确的文本，所以用httpclient抓取信息时在头部加入代理信息：
  	   header.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
	 */
	public static Map<String, String> doGet(String urlStr, boolean needToken) throws IOException{
		
		Debug.debug(TAG, "[http===" + urlStr + "]");
		HttpGet getMethod = new HttpGet(urlStr);
		//avoid 500 error
		getMethod.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
		
		if(needToken){
			OAuth2 oauth = OAuth2Client.getOAuth();
			if(oauth != null){
				//Debug.debug(TAG, "add authorization header: Bearer " + oauth.getAccessToken());
				//add authorization header
				getMethod.addHeader("Authorization", "Bearer " + oauth.getAccessToken());
			}else{
				Debug.warn(TAG, "error! oauth is null!!!");
				throw new RuntimeException("error! oauth is null. please set it first.");
			}
		}
		if(sHttpClient == null){
			Debug.warn(TAG, "error! sHttpClient is null!!!");
			throw new RuntimeException("sHttpClient is null!!! please call HttpUtils.initConnection() first!");
		}
		HttpResponse response = sHttpClient.execute(getMethod);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(CODE, response.getStatusLine().getStatusCode()+"");
		String content = EntityUtils.toString(response.getEntity(), "utf-8");
		resultMap.put(RETURN, content);
		Debug.debug(TAG, "[http content] = " + content);
		return resultMap;
	}

	//处理http post请求
	public static Map<String, String> doPost(String baseUrl, List<NameValuePair> paramsList) 
			throws IOException{
		HttpPost postMethod = new HttpPost(baseUrl);
		postMethod.setEntity(new UrlEncodedFormEntity(paramsList, "utf-8"));
		if(sHttpClient == null){
			Debug.warn(TAG, "error! sHttpClient is null!!!");
			throw new RuntimeException("sHttpClient is null!!! please call HttpUtils.initConnection() first!");
		}
		HttpResponse response = sHttpClient.execute(postMethod); 
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(CODE, response.getStatusLine().getStatusCode()+"");
		resultMap.put(RETURN, EntityUtils.toString(response.getEntity(), "utf-8"));
		
		return resultMap;
	}
	
	//========================================================================================//
	/*public static Map<String, String> doGet2(String baseUrl, List<NameValuePair> paramsList) 
			throws IOException{
		String urlStr = baseUrl;
		if(paramsList != null){
			urlStr = baseUrl + "?" + StrUtils.getStringParams(paramsList);
		}
		Debug.debug(TAG, "[http===" + urlStr + "]");
		URL url = new URL(urlStr);
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setConnectTimeout(TIMEOUT);
		con.setRequestMethod(METHOD_GET);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		InputStream is;
		try {
			is = con.getInputStream();
		} catch (IOException e) {
			is = con.getErrorStream();
		}
		
		int statusCode = con.getResponseCode();
		Debug.debug(TAG, "[http]code="+statusCode);
		resultMap.put(CODE, String.valueOf(statusCode));
		String content = getContent(is);
		resultMap.put(RETURN, content);
		//Debug.debug(TAG, "[http]content=" + content);
		
		con.disconnect();
		return resultMap;
	}
	
	private static String getContent(InputStream is) throws UnsupportedEncodingException {
		return new String(getContentBytes(is), "UTF-8");
	}

	private static byte[] getContentBytes(InputStream is) throws UnsupportedEncodingException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] buf = new byte[4096];
		int len = 0;
		try {
			while((len = is.read(buf)) != -1) {
				os.write(buf, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return os.toByteArray();
	}*/
}
