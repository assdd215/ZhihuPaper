package com.example.aria.baike.ui.home.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by Aria on 2017/4/9.
 */

public abstract class ArticleScrollListener extends RecyclerView.OnScrollListener{

    private int preX = -1;
    private int preY = -1;

    //声明一个LinearLayoutManager
    private LinearLayoutManager linearLayoutManager;

    //已经加载出来的item的数量
    private int totalItemCount;

    //主要用来存储上一个totalItemCount;
    private int previousTotal = 0;

    //在屏幕上可见的item数量
    private int visibleItemCount;

    //在屏幕可见的Item中的第一个
    private int firstVisibleItem;

    //是否正在上拉数据
    private boolean loading = false;


    public ArticleScrollListener(LinearLayoutManager linearLayoutManager){
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = linearLayoutManager.getItemCount();
        firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem ){
            Log.d("MainActivity","loadMore");
            onLoadMore();
            loading = true;
        }
    }

    public void setPreviousTotal(int previousTotal) {
        this.previousTotal = previousTotal;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public abstract void onLoadMore();
}
