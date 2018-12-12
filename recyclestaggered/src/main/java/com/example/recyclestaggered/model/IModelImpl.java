package com.example.recyclestaggered.model;

import com.example.okhttp.ICallBack;
import com.example.okhttp.OkHttpUtil;
import com.example.recyclestaggered.callback.MyCallBack;

import java.util.Map;

public class IModelImpl implements IModel{
    @Override
    public void requestDataPost(String url, Map<String, String> params, Class clazz, final MyCallBack myCallBack) {
        OkHttpUtil.getInstance().postAsynchronization(url, params, clazz, new ICallBack() {
            @Override
            public void success(Object o) {
                myCallBack.setData(o);
            }

            @Override
            public void failed(Exception e) {
                myCallBack.setData(e.getMessage());
            }
        });
    }
}
