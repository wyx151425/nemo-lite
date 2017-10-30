package com.rumofuture.nemolite.app.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class OnListScrollListener extends RecyclerView.OnScrollListener {

    private int pageLimit;  // 允许查询的数据最大条数
    private int prevItemTotal = 0;  // 本次查询前适配器内数据的总数

    private int pageCode = 0;
    private boolean queryable = true;

    private LinearLayoutManager mLinearLayoutManager;

    public OnListScrollListener(int pageLimit) {
        this.pageLimit = pageLimit;
    }

    public OnListScrollListener(LinearLayoutManager linearLayoutManager, int pageLimit) {
        this.mLinearLayoutManager = linearLayoutManager;
        this.pageLimit = pageLimit;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemTotal = recyclerView.getChildCount();
        int currentItemTotal = mLinearLayoutManager.getItemCount();
        int firstVisibleItemTotal = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (!queryable) {
            queryable = (currentItemTotal - prevItemTotal) == pageLimit;
        }

        if (queryable && (currentItemTotal - visibleItemTotal) <= firstVisibleItemTotal) {
            pageCode++;
            queryable = false;
            prevItemTotal = currentItemTotal;
            onLoadMore(pageCode);
        }
    }

    public void setLayoutManager(LinearLayoutManager linearLayoutManager) {
        mLinearLayoutManager = linearLayoutManager;
    }

    public void init() {
        pageCode = 0;
        queryable = true;
    }

    public abstract void onLoadMore(int pageCode);
}
