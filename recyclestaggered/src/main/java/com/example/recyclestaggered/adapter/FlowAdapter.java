package com.example.recyclestaggered.adapter;

import android.content.Context;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recyclestaggered.R;
import com.example.recyclestaggered.bean.ShoppingBean;

import java.util.ArrayList;
import java.util.List;

public  class FlowAdapter extends RecyclerView.Adapter<FlowAdapter.ViewHolder> {

    private List<ShoppingBean> mData;
    private Context context;

    public FlowAdapter(Context context) {
        this.context = context;
        mData=new ArrayList<>();
    }

    public void addItem(ShoppingBean shoppingBean) {
        if (shoppingBean != null) {
            mData.add(shoppingBean);
        }
    }

    @NonNull
    @Override
    public FlowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_recycle_flow,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlowAdapter.ViewHolder viewHolder, final int i) {
        ShoppingBean shoppingBean=mData.get(i);
        viewHolder.title.setText(shoppingBean.getData().get(i).getName());
        Glide.with(context).load(shoppingBean.getData().get(i).getIcon()).into(viewHolder.avatar);
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClick!=null){
                    mClick.OnClick(i);
                }
            }
        });
        viewHolder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mClick != null){
                    mClick.OnLongClick(i);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final ImageView avatar;
        public final LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_recycle_staggered);
            avatar = itemView.findViewById(R.id.iv_recycle_staggered);
            linearLayout=itemView.findViewById(R.id.constraint);
        }
    }
    /**
     * 增加数据，传入添加的位置和数据
     */
    public void addData(int position,ShoppingBean shoppingBean) {
        mData.add(position,shoppingBean);
        notifyItemInserted(position);
    }

    /**
     * 移除数据
     */
    public void removeData(int position) {
        mData.remove(position);
        notifyItemInserted(position);
    }
    Click mClick;

    public void setClickListener(FlowAdapter.Click click){
        mClick = click;
    }

    public interface Click{
        void OnClick(int position);
        void OnLongClick(int position);
    }
}
