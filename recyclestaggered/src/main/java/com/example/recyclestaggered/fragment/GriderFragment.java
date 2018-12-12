package com.example.recyclestaggered.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.recyclestaggered.R;
import com.example.recyclestaggered.adapter.GridAdapter;
import com.example.recyclestaggered.api.Apis;
import com.example.recyclestaggered.bean.ShoppingBean;
import com.example.recyclestaggered.presenter.IPresenterImpl;
import com.example.view.DividerGridItemDecoration;
import com.example.view.IView;

import java.util.HashMap;
import java.util.Map;

public class GriderFragment extends Fragment implements IView {

    private final int mSpanCount = 3;
    private IPresenterImpl iPresenter;
    private GridAdapter adapter;
    private RecyclerView recyclerView;
    private ShoppingBean bean;
    private Button button_recycler_grid_add;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grider,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iPresenter=new IPresenterImpl(this);
        adapter=new GridAdapter(getActivity());
        recyclerView=view.findViewById(R.id.recyclerview);
        button_recycler_grid_add=view.findViewById(R.id.button_recycler_grid_add);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), mSpanCount);
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置布局管理器
        recyclerView.setLayoutManager(gridLayoutManager);
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
        DividerGridItemDecoration dividerGridItemDecoration = new DividerGridItemDecoration(getActivity());
        recyclerView.addItemDecoration(dividerGridItemDecoration);

        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        button_recycler_grid_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addData(0,bean);
            }
        });
        adapter.setClickListener(new GridAdapter.Click() {
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
