package com.example.aria.baike.ui.home;

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
import android.widget.Toast;

import com.example.aria.baike.R;
import com.example.aria.baike.common.BaseFragment;
import com.example.aria.baike.global.Constants;
import com.example.aria.baike.global.Networks;
import com.example.aria.baike.model.Article;
import com.example.aria.baike.ui.detail.DetailActivity;
import com.example.aria.baike.ui.home.adapter.ArticleListAdapter;
import com.example.aria.baike.ui.home.adapter.ArticleScrollListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Aria on 2017/2/14.
 */

public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0: //下拉刷新控件
//                    articleListAdapter.notifyDataSetChanged();
                    articleListAdapter_test.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    scrollListener.setPreviousTotal(0);
                    break;
                case 1://上拉底部加载更多
//                    articleListAdapter.notifyDataSetChanged();
                    articleListAdapter_test.notifyDataSetChanged();
            }
        }
    };

//    @BindView(R.id.banner)
//    Banner banner;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.ArticleList)
    RecyclerView ArticleList;

    ArticleListAdapter articleListAdapter_test;
    ArticleScrollListener scrollListener;

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
    }

    @Override
    public void initView() {
        initArticleList();
    }


    private void initArticleList(){
        articleListAdapter_test = new ArticleListAdapter(context);
        ArticleList.setLayoutManager(new LinearLayoutManager(getContext()));
        ArticleList.setAdapter(articleListAdapter_test);
        ArticleList.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void initListener() {
        swipeRefreshLayout.setOnRefreshListener(this);

        articleListAdapter_test.setOnItemClickListener(new ArticleListAdapter.OnItemClickListener() {
            @Override
            public void onArticleItemClickListener(View view) {
                Toast.makeText(context,"v.getId()"+view.getId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),DetailActivity.class);
                intent.putExtra("url", Constants.getInstance().getArticleList().get(view.getId()-1).getUrl());
                intent.putExtra("article_detail_title",Constants.getInstance().getArticleList().get(view.getId()-1).getTitle());
                startActivity(intent);
            }
        });
        scrollListener = new ArticleScrollListener((LinearLayoutManager) ArticleList.getLayoutManager()) {
            @Override
            public void onLoadMore() {
                for (int i=0;i<5;i++){
                    Article article = new Article();
                    article.setClassify("测试");
                    article.setSummary("测试用案例");
                    article.setTitle("测试用标题"+i);
                    articleListAdapter_test.getArticleList().add(article);

                }
                Message message = new Message();
                message.what = 1;
                handler.sendMessageDelayed(message,500);
            }
        };

        ArticleList.addOnScrollListener(scrollListener);
    }

    private void loadMoreData(){
        JSONObject object = new JSONObject();
        try {
            object.put("lastIndex",Networks.getInstance().lastIndex);
            Networks.getInstance().doPost(Networks.basepath+"/article/getArticles",object).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(getContext(),"数据加载失败！loadMoreData()",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str = response.body().string();
                    List articleList = Constants.getInstance().getArticleList();
                    Article article;
                    JSONObject jsonObject;
                    try {
                        JSONArray jsonArray = new JSONArray(str);
                        for (int i=0;i<jsonArray.length();i++){
                            jsonObject = (JSONObject) jsonArray.get(i);
                            article = new Article();
                            article.setId(jsonObject.getInt("id"));
                            article.setTitle(jsonObject.getString("title"));
                            article.setClassify(jsonObject.getString("classify"));
                            article.setSummary(jsonObject.getString("summary"));
                            article.setUrl(jsonObject.getString("url"));
                            articleList.add(article);
                            Networks.getInstance().lastIndex++;
                        }
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessageDelayed(message,500);
//                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //TODO
                //获取数据的操作
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                JSONObject object = new JSONObject();
                try {
                    object.put("lastIndex",0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Networks.getInstance().doPost(Networks.basepath+"/article/getAll").enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(getContext(),"获取数据失败！HomeFragment",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Networks.getInstance().lastIndex = 0;
                        String str = response.body().string();
                        List articleList = Constants.getInstance().getArticleList();
                        articleList.clear();
                        Article article;
                        JSONObject jsonObject;
                        try {
                            JSONArray jsonArray = new JSONArray(str);
                            for (int i=0;i<jsonArray.length();i++){
                                jsonObject = (JSONObject) jsonArray.get(i);
                                article = new Article();
                                article.setId(jsonObject.getInt("id"));
                                article.setTitle(jsonObject.getString("title"));
                                article.setClassify(jsonObject.getString("classify"));
                                article.setSummary(jsonObject.getString("summary"));
                                article.setUrl(jsonObject.getString("url"));
                                articleList.add(article);
                                Networks.getInstance().lastIndex++;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Message message = new Message();
                message.what = 0;
                handler.sendMessageDelayed(message,2000);
//                handler.sendMessage(message);
//                handler.sendEmptyMessageAtTime(message.what,2000);
            }
        }).start();
    }
}
