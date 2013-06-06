package com.konka.doubanmovie.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.NameValuePair;

/**
 * 
 * @author 
 *
 */
public class StrUtils {
	
	//utf-8转码
	public static String encode(String paramDecStr) {
		//return URLEncoder.encode(paramDecStr);
        if ( paramDecStr == null || paramDecStr.equals("") ) {
            return "";
        }
        try {
            return URLEncoder.encode(paramDecStr, "UTF-8").replace("+", "%20")
                    .replace("*", "%2A").replace("%7E", "~")
                    .replace("#", "%23");
        } catch (UnsupportedEncodingException e) {
            //throw new RuntimeException(e.getMessage(), e);
        	return paramDecStr;
        }
    }

	//得到String格式的参数字符串
	public static String getStringParams(List<NameValuePair> paramsList) {
		StringBuffer sb = new StringBuffer();
		for(NameValuePair param : paramsList){
			sb.append('&');
			sb.append(param.getName());
			sb.append('=');
			sb.append(encode(param.getValue()));
		}
		//去掉第一个&号
		return sb.toString().substring(1);
	}
	
	public static boolean isEmpty(String str){
		return (str == null || str.equals(""));
	}
}
