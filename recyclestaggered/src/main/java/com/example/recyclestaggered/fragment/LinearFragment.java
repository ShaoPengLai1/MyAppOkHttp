package com.example.recyclestaggered.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recyclestaggered.R;
import com.example.recyclestaggered.adapter.GridAdapter;
import com.example.recyclestaggered.adapter.LinearAdapter;
import com.example.recyclestaggered.api.Apis;
import com.example.recyclestaggered.bean.ShoppingBean;
import com.example.recyclestaggered.presenter.IPresenterImpl;
import com.example.view.IView;

import java.util.HashMap;
import java.util.Map;

public class LinearFragment extends Fragment implements IView {

    private IPresenterImpl iPresenter;
    private LinearAdapter adapter;
    private RecyclerView recyclerView;
    private ShoppingBean bean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_linear,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iPresenter=new IPresenterImpl(this);
        adapter=new LinearAdapter(getActivity());
        recyclerView = view.findViewById(R.id.recyclerview);
        //写一个布局管理器，写一个线性管理器
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        //设置方向，这里用垂直方向
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置布局管理器
        recyclerView.setLayoutManager(linearLayoutManager);

        initData();
    }

    private void initData() {
        Map<String,String> params = new HashMap<>();
        iPresenter.startRequestPost(Apis.URL_LOGIN_POST,params,ShoppingBean.class);
    }

    @Override
    public void getRequest(Object o) {
        if (o instanceof ShoppingBean){
            bean = (ShoppingBean) o;
            for (int i = 0; i< bean.getData().size(); i++){
                adapter.addItem(bean);
            }
        }
        recyclerView.setAdapter(adapter);
        //设置分隔线（系统提供）
        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        //添加自定义分割线
        divider.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.recycler_divider_horizontal));
        recyclerView.addItemDecoration(divider);

        adapter.setClickListener(new LinearAdapter.Click() {
            @Override
            public void OnClick(final int position) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("确定要删除条目吗？");
                builder.setMessage(bean.getData().get(position).getName());
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.removeData(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }

            @Override
            public void OnLongClick(int position) {

            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
    }
}
