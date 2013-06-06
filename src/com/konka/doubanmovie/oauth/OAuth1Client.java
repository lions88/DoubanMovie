package com.konka.doubanmovie.oauth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.util.Base64;

import com.konka.doubanmovie.Debug;
import com.konka.doubanmovie.oauth.OAuth1.Token;
import com.konka.doubanmovie.util.BaseURL;
import com.konka.doubanmovie.util.Configuration;
import com.konka.doubanmovie.util.HttpUtils;
import com.konka.doubanmovie.util.StrUtils;

public class OAuth1Client {
	private static OAuth1 oauth;
	//private static Comparator<NameValuePair> comparator;
	
	static{
		oauth = new OAuth1();
		/*comparator = new Comparator<NameValuePair>() {
            public int compare(NameValuePair p1, NameValuePair p2) {
                int result = p1.getName().compareTo(p2.getName());
                if (0 == result)
                    result = p1.getValue().compareTo(p2.getValue());
                return result;
            }
        };*/
	}
	
	public static OAuth1 getOAuth(){
		return oauth;
	}
	
	public Token getReuqestToken(){
		List<NameValuePair> params = getCommonPamarsList();
		String consumerSecret = Configuration.getValue(Configuration.KEY_API_SECRET);
		String signature = getSignature("GET", BaseURL.REQUEST_TOKEN_URL, params, consumerSecret);
		params.add(new BasicNameValuePair("oauth_signature", signature));
		
		try {
			Map<String, String> resultMap = HttpUtils.doGet(BaseURL.REQUEST_TOKEN_URL, params, false);
			Debug.debug("OAuth1Cilent", "code=" + resultMap.get(HttpUtils.CODE));
			Debug.debug("OAuth1Cilent", "content=" + resultMap.get(HttpUtils.RETURN));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Token getAuthorizedReuqestToken(){
		return null;
	}
	
	public static Token getAccessedToken(){
		return null;
	}
	
	private List<NameValuePair> getCommonPamarsList(){
		String api_key = Configuration.getValue(Configuration.KEY_API_KEY);
		
		List<NameValuePair> commonParamsList = new ArrayList<NameValuePair>();
		commonParamsList.add(new BasicNameValuePair("oauth_consumer_key", api_key));
		commonParamsList.add(new BasicNameValuePair("oauth_nonce", getNonce()));
		commonParamsList.add(new BasicNameValuePair("oauth_signature_method", "HMAC-SHA1"));
		commonParamsList.add(new BasicNameValuePair("oauth_timestamp", String.valueOf(
				System.currentTimeMillis() / 1000) ));
		commonParamsList.add(new BasicNameValuePair("oauth_version", "1.0"));
		
		//Collections.sort(commonParamsList, comparator);
		return commonParamsList;
	}
	
	private String getSignature(String HttpMethod, String baseUrl, 
			List<NameValuePair> params, String consumerSecret){
		try {
			StringBuffer buf = new StringBuffer();
			buf.append(HttpMethod.toUpperCase());
			buf.append('&');
			buf.append(URLEncoder.encode(baseUrl, "UTF-8"));
			buf.append('&');
			buf.append(StrUtils.getStringParams(params));
			
			String secret = consumerSecret + "&" ;
			SecretKeySpec spec = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA1");
		
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(spec);

			byte[] data = mac.doFinal(buf.toString().getBytes("UTF-8"));
			return Base64.encodeToString(data, Base64.DEFAULT);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static String samples = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	public static Random random = new Random(System.currentTimeMillis());
	public static final int NONCE_LEN = 32;
	
	//随机字符串
	public static String getNonce() {
		int MAX_LEN = samples.length();
		int size = random.nextInt(NONCE_LEN);
		if (size == 0) {
			return getNonce();
		}
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < size; i++) {
			buf.append(samples.charAt(random.nextInt(MAX_LEN)));
		}
		return buf.toString();
	}
	
}
