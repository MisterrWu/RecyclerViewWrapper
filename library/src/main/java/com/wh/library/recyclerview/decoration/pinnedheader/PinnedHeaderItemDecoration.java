package com.wh.library.recyclerview.decoration.pinnedheader;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

// 添加固定标签
public class PinnedHeaderItemDecoration extends RecyclerView.ItemDecoration {

    private PinnedHeaderAdapter mAdapter;

    public PinnedHeaderItemDecoration(PinnedHeaderAdapter adapter){
        this.mAdapter = adapter;
    }

    /**
     * 把要固定的View绘制在上层
     */
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        int firstVisiblePosition;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(layoutManager instanceof LinearLayoutManager){
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            firstVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition();
        }else {
            View firstView = parent.getChildAt(0);
            firstVisiblePosition = parent.getChildAdapterPosition(firstView);
        }
        if (firstVisiblePosition == RecyclerView.NO_POSITION) {
            return;
        }
        int pinnedHeaderPosition = getPinnedHeaderViewPosition(firstVisiblePosition);
        if (pinnedHeaderPosition != -1) {
            RecyclerView.ViewHolder viewHolder = mAdapter.onCreateViewHolder(parent,
                    mAdapter.getItemViewType(pinnedHeaderPosition));

            mAdapter.onBindHeaderHolder(viewHolder,pinnedHeaderPosition);

            ensurePinnedHeaderViewLayout(viewHolder.itemView, parent);
            int sectionPinOffset = 0;

            for (int index = 0; index < parent.getChildCount(); index++) {
                if (mAdapter.isHeader(parent.getChildAdapterPosition(parent.getChildAt(index)))) {
                    View sectionView = parent.getChildAt(index);
                    int sectionTop = sectionView.getTop();
                    int pinViewHeight = viewHolder.itemView.getHeight();
                    if (sectionTop < pinViewHeight && sectionTop > 0) {
                        sectionPinOffset = sectionTop - pinViewHeight;
                    }
                }
            }
            int saveCount = c.save();
            c.translate(0, sectionPinOffset);
            viewHolder.itemView.draw(c);
            c.restoreToCount(saveCount);
        }
    }

    /**
     * 根据第一个可见的adapter的位置去获取临近的一个要固定标签的position的位置
     *
     * @param adapterFirstVisible 第一个可见的adapter的位置
     * @return -1：未找到 >=0 找到位置
     */
    private int getPinnedHeaderViewPosition(int adapterFirstVisible) {
        for (int index = adapterFirstVisible; index >= 0; index--) {
            if (mAdapter.isHeader(index)) {
                return index;
            }
        }
        return -1;
    }

    private void ensurePinnedHeaderViewLayout(View pinView, RecyclerView recyclerView) {
        if (pinView.isLayoutRequested()) {
            /**
             * 用的是RecyclerView的宽度测量，和RecyclerView的宽度一样
             */
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) pinView.getLayoutParams();
            if (layoutParams == null) {
                throw new NullPointerException("PinnedHeaderItemDecoration");
            }
            int widthSpec = View.MeasureSpec.makeMeasureSpec(
                    recyclerView.getMeasuredWidth() - layoutParams.leftMargin - layoutParams.rightMargin, View.MeasureSpec.EXACTLY);

            int heightSpec;
            if (layoutParams.height > 0) {
                heightSpec = View.MeasureSpec.makeMeasureSpec(layoutParams.height, View.MeasureSpec.EXACTLY);
            } else {
                heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            }
            pinView.measure(widthSpec, heightSpec);
            pinView.layout(0, 0, pinView.getMeasuredWidth(), pinView.getMeasuredHeight());
        }
    }
}
