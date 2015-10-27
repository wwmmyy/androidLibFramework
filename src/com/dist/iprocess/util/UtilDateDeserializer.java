package com.dist.iprocess.util;

import java.lang.reflect.Type;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
/**
 * Gson
* @类名: UtilDateDeserializer 
* @描述: 负责处理google的日期转化 
* @作者: 王明远 
* @日期: 2015-4-23 下午5:29:58 
* @修改人: 
 * @修改时间: 
 * @修改内容:
 * @版本: V1.0
 * @版权:Copyright ©  All rights reserved.
 */
public class UtilDateDeserializer implements JsonDeserializer<java.util.Date> {
    @Override
    public java.util.Date deserialize(JsonElement json, Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {
        return new java.util.Date(json.getAsJsonPrimitive().getAsLong());
    }
}