package com.wh.library.recyclerview.snaphelper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 
 * @author wuhan
 * @date 2018/11/15 12:00
 */
public class GridPagerSnapHelper extends PagerSnapHelper {


    private GravityDelegate delegate;

    public GridPagerSnapHelper(int row,int column){
        delegate = new GravityDelegate(row,column);
    }

    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        return delegate.calculateDistanceToFinalSnap(layoutManager,targetView);
    }

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        super.attachToRecyclerView(recyclerView);
        delegate.attachToRecyclerView(recyclerView);
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX,
                                      int velocityY) {
        return delegate.findTargetSnapPosition(layoutManager,velocityX,velocityY);
    }
}
