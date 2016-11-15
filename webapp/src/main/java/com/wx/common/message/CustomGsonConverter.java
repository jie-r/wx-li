package com.wx.common.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

/**
 * Created by kalen on 16/8/29.
 */
public class CustomGsonConverter extends GsonHttpMessageConverter {
    public CustomGsonConverter() {
        //更换Gson转换器
        Gson gson = new GsonBuilder()
                .serializeNulls()        //null值属性也需要序列化
                .setDateFormat("yyyy-MM-dd HH:mm:ss") //设置日期转换
                .create();
        super.setGson(gson);
    }
}
