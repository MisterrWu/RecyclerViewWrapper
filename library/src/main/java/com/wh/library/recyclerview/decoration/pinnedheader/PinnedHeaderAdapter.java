package com.wh.library.recyclerview.decoration.pinnedheader;

import android.support.v7.widget.RecyclerView;

public abstract class PinnedHeaderAdapter<WH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<WH> {

    protected abstract boolean isHeader(int position);

    public abstract void onBindHeaderHolder(RecyclerView.ViewHolder holder, int headerPosition);
}
