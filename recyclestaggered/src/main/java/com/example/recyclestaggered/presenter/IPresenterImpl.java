package com.example.recyclestaggered.presenter;

import com.example.recyclestaggered.callback.MyCallBack;
import com.example.recyclestaggered.model.IModelImpl;
import com.example.view.IView;

import java.util.Map;

public class IPresenterImpl implements IPresenter {

    private IView mIview;
    private IModelImpl model;

    public IPresenterImpl(IView iview) {
        mIview = iview;
        model = new IModelImpl();
    }
    @Override
    public void startRequestPost(String url, Map<String, String> params, Class clazz) {
        model.requestDataPost(url, params, clazz, new MyCallBack() {
            @Override
            public void setData(Object data) {
                mIview.getRequest(data);
            }
        });
    }
    public void onDetach(){
        if(model!=null){
            model = null;
        }
        if (mIview!=null){
            mIview = null;
        }
    }
}
