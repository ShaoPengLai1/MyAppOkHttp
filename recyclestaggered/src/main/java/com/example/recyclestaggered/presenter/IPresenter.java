package com.example.recyclestaggered.presenter;

import java.util.Map;

public interface IPresenter {
    void startRequestPost(String url, Map<String,String> params, Class clazz);
}
