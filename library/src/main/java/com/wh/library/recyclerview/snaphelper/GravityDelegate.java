package com.wh.library.recyclerview.snaphelper;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * @author wuhan
 * @date 2018/11/15 10:11
 */
public class GravityDelegate {

    private int mRow ;
    private int mColumn ;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mVerticalLayoutManager;
    private RecyclerView.LayoutManager mHorizontalLayoutManager;

    @Nullable
    private OrientationHelper mVerticalHelper;
    @Nullable
    private OrientationHelper mHorizontalHelper;

    public GravityDelegate(int row,int column){
        this.mRow = row;
        this.mColumn = column;
    }

    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        this.mRecyclerView = recyclerView;
    }

    @Nullable
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        int[] out = new int[2];
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToCenter(layoutManager, targetView,
                    getHorizontalHelper(layoutManager));
        } else {
            out[0] = 0;
        }

        if (layoutManager.canScrollVertically()) {
            out[1] = distanceToCenter(layoutManager, targetView,
                    getVerticalHelper(layoutManager));
        } else {
            out[1] = 0;
        }
        return out;
    }

    private int distanceToCenter(@NonNull RecyclerView.LayoutManager layoutManager,
                                 @NonNull View targetView, OrientationHelper helper) {
        if(mRecyclerView == null){
            return 0;
        }
        if (layoutManager.canScrollHorizontally()) {
            int totalWidth = mRecyclerView.getWidth();

            int columnWidth = totalWidth / mColumn;

            int position = layoutManager.getPosition(targetView);
            int pageIndex = pageIndex(position);

            int currentPageStart = pageIndex * countOfpage();

            int distance = ((position - currentPageStart) / mRow) * columnWidth;

            final int childStart = helper.getDecoratedStart(targetView);
            return childStart - distance;
        } else {//数值方向
            int totalHeight = mRecyclerView.getHeight();

            int rowHeight = totalHeight / mRow;

            int position = layoutManager.getPosition(targetView);
            int pageIndex = pageIndex(position);

            int currentPageStart = pageIndex * countOfpage();

            int distance = ((position - currentPageStart) / mColumn) * rowHeight;

            final int childStart = helper.getDecoratedStart(targetView);
            return childStart - distance;
        }
    }

    /**
     * get the page of position
     *
     * @param position
     * @return
     */
    private int pageIndex(int position) {
        return position / countOfpage();
    }

    /**
     * the total count of per page
     *
     * @return
     */
    private int countOfpage() {
        return mRow * mColumn;
    }

    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX,
                                      int velocityY) {
        final int itemCount = layoutManager.getItemCount();
        if (itemCount == 0) {
            return RecyclerView.NO_POSITION;
        }

        View mStartMostChildView = null;
        if (layoutManager.canScrollVertically()) {
            mStartMostChildView = findStartView(layoutManager, getVerticalHelper(layoutManager));
        } else if (layoutManager.canScrollHorizontally()) {
            mStartMostChildView = findStartView(layoutManager, getHorizontalHelper(layoutManager));
        }

        if (mStartMostChildView == null) {
            return RecyclerView.NO_POSITION;
        }
        final int centerPosition = layoutManager.getPosition(mStartMostChildView);
        if (centerPosition == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION;
        }

        final boolean forwardDirection;
        if (layoutManager.canScrollHorizontally()) {
            forwardDirection = velocityX > 0;
        } else {
            forwardDirection = velocityY > 0;
        }
        boolean reverseLayout = false;
        if ((layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            RecyclerView.SmoothScroller.ScrollVectorProvider vectorProvider =
                    (RecyclerView.SmoothScroller.ScrollVectorProvider) layoutManager;
            PointF vectorForEnd = vectorProvider.computeScrollVectorForPosition(itemCount - 1);
            if (vectorForEnd != null) {
                reverseLayout = vectorForEnd.x < 0 || vectorForEnd.y < 0;
            }
        }

        int pageIndex = pageIndex(centerPosition);

        int currentPageStart = pageIndex * countOfpage();

        return reverseLayout
                ? (forwardDirection ? currentPageStart - countOfpage() : currentPageStart)
                : (forwardDirection ? currentPageStart + countOfpage() : (currentPageStart + countOfpage() - 1));
    }

    @Nullable
    private View findStartView(RecyclerView.LayoutManager layoutManager,
                               OrientationHelper helper) {
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return null;
        }

        View closestChild = null;
        int startest = Integer.MAX_VALUE;

        for (int i = 0; i < childCount; i++) {
            final View child = layoutManager.getChildAt(i);
            int childStart = helper.getDecoratedStart(child);

            if (childStart < startest) {
                startest = childStart;
                closestChild = child;
            }
        }

        return closestChild;
    }

    @NonNull
    private OrientationHelper getVerticalHelper(@NonNull RecyclerView.LayoutManager layoutManager) {
        if (mVerticalHelper == null || mVerticalLayoutManager != layoutManager) {
            this.mVerticalLayoutManager = layoutManager;
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return mVerticalHelper;
    }

    @NonNull
    private OrientationHelper getHorizontalHelper(
            @NonNull RecyclerView.LayoutManager layoutManager) {
        if (mHorizontalHelper == null || mHorizontalLayoutManager != layoutManager) {
            this.mHorizontalLayoutManager = layoutManager;
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return mHorizontalHelper;
    }
}
