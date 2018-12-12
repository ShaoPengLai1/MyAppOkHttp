package com.example.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.recyclestaggered.R;

public class DividerGridItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;
    public DividerGridItemDecoration(Context context) {
        //在构造方法中，讲分割线的样子拿到
        mDivider = ContextCompat.getDrawable(context,R.drawable.recycler_divider);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        //画垂直和水平的两种分割线
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        final int ChildCount=parent.getChildCount();
        for (int i=0;i<ChildCount;i++){
            final View child=parent.getChildAt(i);
            final RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top=child.getTop()-params.topMargin;
            final int bottom=child.getBottom()+params.bottomMargin;
            final int left=child.getRight()+params.rightMargin;
            final int right=left+mDivider.getIntrinsicWidth();

            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        //拿到所有孩子
        //根据孩子的坐标位置，计算我们的要画的分割线的位置，然后画
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin
                    + mDivider.getIntrinsicWidth();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0,0,mDivider.getIntrinsicWidth(),mDivider.getIntrinsicHeight());
    }
}
