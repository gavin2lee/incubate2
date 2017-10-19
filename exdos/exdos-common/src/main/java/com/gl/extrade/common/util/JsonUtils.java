package com.gl.extrade.common.util;

import com.alibaba.fastjson.JSON;

public abstract class JsonUtils {

	public static String toJsonString(Object obj){
		return JSON.toJSONString(obj);
	}
	
	public static <T> T toObject(String text, Class<T> clz){
		return JSON.parseObject(text, clz);
	}
}
