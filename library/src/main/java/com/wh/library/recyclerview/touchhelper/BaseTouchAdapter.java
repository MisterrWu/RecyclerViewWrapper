package com.wh.library.recyclerview.touchhelper;

import android.support.v7.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public abstract class BaseTouchAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements TouchAdapter {

    protected List<T> mData;

    @Override
    public void swapData(int fromPosition, int toPosition) {
        if(mData == null || mData.isEmpty()){
            return;
        }
        if(toPosition >= getLastSwapPosition()){
            return;
        }
        Collections.swap(mData,fromPosition,toPosition);
    }

    @Override
    public void moveData(int fromPosition, int toPosition) {
        if(mData == null || mData.isEmpty()){
            return;
        }
        if (toPosition >= getLastSwapPosition()){
            return;
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    /**
     * 返回最后一个可以交换位置的item下标
     * @return
     */
    protected abstract int getLastSwapPosition();
}
