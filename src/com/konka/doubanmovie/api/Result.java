package com.konka.doubanmovie.api;

/**
 * 
 * @author 
 * @date
 * @desc
 * 		返回结果类型封装
 */
public class Result {
	public static final int OK = 0;
	
	//结果码，0表示正常，其他对应ErrorCode中的错误码
	public int code;
	//public boolean isOk;
	public Object data;
	
	public Result(){
		code = 0xFFFF;
	}
}
