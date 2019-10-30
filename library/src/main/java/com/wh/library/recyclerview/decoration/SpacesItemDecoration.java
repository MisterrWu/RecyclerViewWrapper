package com.wh.library.recyclerview.decoration;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.HashMap;

// 设置item 间隔
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private HashMap<String, Integer> mSpaceValueMap = new HashMap<>();

    public static final String TOP_DECORATION = "top_decoration";
    public static final String BOTTOM_DECORATION = "bottom_decoration";
    public static final String LEFT_DECORATION = "left_decoration";
    public static final String RIGHT_DECORATION = "right_decoration";
    public static final String HEAD_TOP_DECORATION = "head_top_decoration";
    public static final String HEAD_BOTTOM_DECORATION = "head_bottom_decoration";

    public SpacesItemDecoration(){}

    public SpacesItemDecoration(int left, int top, int right, int bottom){
        setLeftDecoration(left);
        setTopDecoration(top);
        setRightDecoration(right);
        setBottomDecoration(bottom);
    }

    public void setHeadTopDecoration(int headTop){
        mSpaceValueMap.put(HEAD_TOP_DECORATION,headTop);
    }

    public void setHeadBottomDecoration(int headBottom){
        mSpaceValueMap.put(HEAD_BOTTOM_DECORATION,headBottom);
    }

    public void setTopDecoration(int top){
        mSpaceValueMap.put(TOP_DECORATION,top);
    }

    public void setBottomDecoration(int bottom){
        mSpaceValueMap.put(BOTTOM_DECORATION,bottom);
    }

    public void setLeftDecoration(int left){
        mSpaceValueMap.put(LEFT_DECORATION,left);
    }

    public void setRightDecoration(int right){
        mSpaceValueMap.put(RIGHT_DECORATION,right);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, RecyclerView parent,@NonNull RecyclerView.State state) {

        if(parent.getLayoutManager() != null) {
            // 可以根据不同的item类型进行边距设置
            int viewType = parent.getLayoutManager().getItemViewType(view);
        }

        if (mSpaceValueMap.get(TOP_DECORATION) != null) {
            Integer top = mSpaceValueMap.get(TOP_DECORATION);
            outRect.top = top == null ? 0 : top;
        }
        if (mSpaceValueMap.get(LEFT_DECORATION) != null) {
            Integer left = mSpaceValueMap.get(LEFT_DECORATION);
            outRect.left = left == null ? 0 : left;
        }
        if (mSpaceValueMap.get(RIGHT_DECORATION) != null) {
            Integer right = mSpaceValueMap.get(RIGHT_DECORATION);
            outRect.right = right == null ? 0 : right;
        }
        if (mSpaceValueMap.get(BOTTOM_DECORATION) != null) {
            Integer bottom = mSpaceValueMap.get(BOTTOM_DECORATION);
            outRect.bottom = bottom == null ? 0 : bottom;
        }
    }
}
