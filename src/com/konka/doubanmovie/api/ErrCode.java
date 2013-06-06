package com.konka.doubanmovie.api;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author 
 * @date 
 * @desc 错误码-信息提示
 */
public class ErrCode {
	public static Map<Integer, String> sErrCodeMap;
	public static final int INVALID_PARAM_CODE = 0;
	public static final int IO_EXCEPTION_CODE = -1;
	public static final int JSON_EXCEPTION_CODE = -2;
	public static final int HTTP_EXCEPTION_CODE = -3;	//http请求异常，非200情况
	
	//don't change these values.
	//access_token_is_missing 
	public static final int ERR_TOKEN_MISSING = 102;
	//invalid_access_token 
	public static final int ERR_TOKEN_INVALID = 103;
	//access_token_has_expired 
	public static final int ERR_TOKEN_EXPIRED = 106;
	
	
	static{
		sErrCodeMap = new HashMap<Integer, String>();
		
		sErrCodeMap.put(INVALID_PARAM_CODE, "参数不合法");
		sErrCodeMap.put(IO_EXCEPTION_CODE, "IO异常");
		sErrCodeMap.put(JSON_EXCEPTION_CODE, "json解析异常");
		sErrCodeMap.put(HTTP_EXCEPTION_CODE, "http请求异常");
		
		sErrCodeMap.put(999, "未知错误");				//unknow_v2_error
		sErrCodeMap.put(1000, "需要权限");				//need_permission
		sErrCodeMap.put(1001, "资源不存在");			//uri_not_found
		sErrCodeMap.put(1002, "参数不全");				//missing_args
		sErrCodeMap.put(1003, "上传的图片太大");			//image_too_large
		sErrCodeMap.put(1004, "输入有违禁词");			//has_ban_word
		sErrCodeMap.put(1005, "输入为空或者输入字数不够");	//input_too_short
		sErrCodeMap.put(1006, "相关的对象不存在");			//target_not_fount，比如回复帖子时，发现小组被删掉了
		sErrCodeMap.put(1007, "验证码有误"); 			//need_captcha，需要验证码，验证码有误
		sErrCodeMap.put(1008, "不支持的图片格式");			//image_unknow	
		sErrCodeMap.put(1009, "照片格式有误"); 			//image_wrong_format，照片格式有误(仅支持JPG,JPEG,GIF,PNG或BMP)
		
		sErrCodeMap.put(1010, "访问私有图片ck验证错误");	//image_wrong_ck
		sErrCodeMap.put(1011, "访问私有图片ck过期");		//image_ck_expired
		sErrCodeMap.put(1012, "题目为空"); 				//title_missing
		sErrCodeMap.put(1013, "描述为空"); 				//desc_missing
		
		sErrCodeMap.put(100, "错误的请求协议");			//invalid_request_scheme 
		sErrCodeMap.put(101, "错误的请求方法");			//invalid_request_method 
		sErrCodeMap.put(101, "错误的请求方法");			//invalid_request_method
		sErrCodeMap.put(102, "未找到access_token");	//access_token_is_missing
		sErrCodeMap.put(103, "access_token不存在或已被用户删除");	//invalid_access_token
		sErrCodeMap.put(104, "apikey不存在或已删除");		//invalid_apikey
		sErrCodeMap.put(105, "apikey已被禁用");			//apikey_is_blocked 
		sErrCodeMap.put(106, "access_token已过期");	//access_token_has_expired
		sErrCodeMap.put(107, "请求地址未注册");			//invalid_request_uri 
		sErrCodeMap.put(108, "用户未授权访问此数据");		//invalid_credencial1
		sErrCodeMap.put(109, "apikey未申请此权限");		//invalid_credencial2
		sErrCodeMap.put(110, "未注册的测试用户");			//not_trial_user 
		
		sErrCodeMap.put(111, "用户访问速度限制");			//rate_limit_exceeded1
		sErrCodeMap.put(112, "IP访问速度限制");			//rate_limit_exceeded2
		sErrCodeMap.put(113, "缺少参数");				//required_parameter_is_missing
		sErrCodeMap.put(114, "错误的grant_type");		//unsupported_grant_type
		sErrCodeMap.put(115, "错误的response_type");	//unsupported_response_type
		sErrCodeMap.put(116, "client_secret不匹配");	//client_secret_mismatch  
		sErrCodeMap.put(117, "redirect_uri不匹配");	//redirect_uri_mismatch  
		sErrCodeMap.put(118, "authorization_code不存在或已过期");	//invalid_authorization_code 
		sErrCodeMap.put(119, "refresh_token不存在或已过期");	//invalid_refresh_token 
		sErrCodeMap.put(120, "用户名密码不匹配");			//username_password_mismatch  
		sErrCodeMap.put(121, "用户不存在或已删除");			//invalid_user 
		sErrCodeMap.put(122, "用户已被屏蔽");			//user_has_blocked 
		sErrCodeMap.put(123, "因用户修改密码而导致access_token过期");	//access_token_has_expired_since_password_changed 
		sErrCodeMap.put(124, "access_token未过期");	//access_token_has_not_expired 
		sErrCodeMap.put(125, "访问的scope不合法");		//invalid_request_scope 
	}
	
	/**
	 * 根据错误码返回对应的错误信息
	 * @param errCode
	 * @return
	 */
	public static String getErrMsg(int errCode){
		if(sErrCodeMap.containsKey(errCode)){
			return sErrCodeMap.get(errCode);
		}
		return "unknown error";
	}
	
	public static String getErrMsg(String errCodeStr){
		int errCode = Integer.parseInt(errCodeStr);
		if(sErrCodeMap.containsKey(errCode)){
			return sErrCodeMap.get(errCode);
		}
		return "unknown error";
	}
}
