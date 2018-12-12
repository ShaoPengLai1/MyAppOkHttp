package com.example.recyclestaggered.model;

import com.example.recyclestaggered.callback.MyCallBack;

import java.util.Map;

public interface IModel {
    void requestDataPost(String url, Map<String,String> params, Class clazz, MyCallBack myCallBack);
}
