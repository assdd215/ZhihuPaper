package com.example.aria.baike.ui.home;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.aria.baike.R;
import com.example.aria.baike.common.BaseFragment;
import com.example.aria.baike.global.Constants;
import com.example.aria.baike.loader.GlideImageLoader;
import com.example.aria.baike.model.Article;
import com.example.aria.baike.ui.detail.DetailActivity;
import com.example.aria.baike.ui.home.adapter.ArticleListAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aria on 2017/2/14.
 */

public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private List<String> BannerTitles;
    private List<Integer> BannerImages;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0: //下拉刷新控件
                    swipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

//    @BindView(R.id.banner)
//    Banner banner;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.ArticleList)
    RecyclerView ArticleList;

    ArticleListAdapter articleListAdapter;

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_content);
        if (getActivity() == null){
            Log.d("MainActivity","null");
        }
        ButterKnife.bind(this,getContentView());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("MainActivity","onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initData() {
        BannerImages = new ArrayList<Integer>();
        BannerTitles = new ArrayList<String>();
        BannerImages.add(R.drawable.banner01);
        BannerImages.add(R.drawable.banner02);
        BannerTitles.add("title01");
        BannerTitles.add("震惊！UC震惊部！");
    }

    @Override
    public void initView() {
//        initBanner();
        initArticleList();
    }


    private void initArticleList(){
        articleListAdapter = new ArticleListAdapter(context);
        ArticleList.setLayoutManager(new LinearLayoutManager(getContext()));
        ArticleList.setAdapter(articleListAdapter);
        ArticleList.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void initListener() {
        swipeRefreshLayout.setOnRefreshListener(this);
        articleListAdapter.setOnItemClickListener(new ArticleListAdapter.OnItemClickListener() {
            @Override
            public void onArticleItemClickListener(View v) {
                Toast.makeText(context,"v.getId()"+v.getId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),DetailActivity.class);
                intent.putExtra("url", Constants.getInstance().getArticleList().get(v.getId()-1).getUrl());
                intent.putExtra("article_detail_title",Constants.getInstance().getArticleList().get(v.getId()-1).getTitle());
                startActivity(intent);
            }

            @Override
            public void onBannerClickListener() {

            }
        });

    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //TODO
                //获取数据的操作
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = 0;
                handler.sendMessage(message);
            }
        }).start();
    }
}
