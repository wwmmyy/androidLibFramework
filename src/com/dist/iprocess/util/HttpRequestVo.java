package com.dist.iprocess.util;

import java.util.HashMap;

import org.json.JSONObject; 

import android.content.Context;

/**
 * ============================================================
 * 
 * 版权 ：安博教育集团 版权所有 (c) 2013
 * 
 * 作者:mwqi
 * 
 * 版本 ：1.0
 * 
 * 创建日期 ： 2013-1-18 上午1:09:32
 * 
 * 描述 ：
 * 
 * 
 * 修订历史 ：
 * 
 * ============================================================
 **/
public class HttpRequestVo {
  /**
   * 请求地址
   */
	public String requestUrl;
	/**
   * 上下文
   */
	public Context context;
	/**
	 * 请求参数
	 */
	public HashMap<String,String> requestDataMap;
//	/**
//	 * Json请求参数
//	 */
//	public JSONObject requestJson;
//	/**
//	 * json解析器
//	 */
//	public JSONParser parser;
//	
//	 /**
//         * 返回值类型。-1表示为字符串，其他代表可以转化为类的json
//         */
//        public int returnType;
}
