package com.wx.common.message;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * 前台返回实体对象
 * Created by Administrator on 2016/8/22.
 */
public class BaseResponse implements Serializable {

    @Expose
    private String code;
    @Expose
    private Object data;
    @Expose
    private String message;

    public BaseResponse(String code, Object data) {
        this.code = code;
        this.data = data;
    }

    public BaseResponse(String code, String message, Object data) {
        this(code, data);
        this.message = message;
    }
}
