package com.example.aria.baike.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.aria.baike.ui.home.adapter.ArticleLinearLayoutManager;
import com.example.aria.baike.ui.home.adapter.ArticleListAdapter;
import com.example.aria.baike.ui.home.adapter.ArticleScrollListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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
                    articleListAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    scrollListener.setPreviousTotal(0);
                    scrollListener.setLoading(false);
                    articleListAdapter.setFootText(ArticleListAdapter.FOOT_LOADING);
                    break;

            }
        }
    };

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.ArticleList)
    RecyclerView ArticleList;

    ArticleListAdapter articleListAdapter;
    ArticleScrollListener scrollListener;
    ArticleLinearLayoutManager articleLinearLayoutManager;

    private volatile boolean isLoadingMore = false;

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
        articleListAdapter = new ArticleListAdapter(context);
        articleLinearLayoutManager = new ArticleLinearLayoutManager(context);
        ArticleList.setLayoutManager(articleLinearLayoutManager);
        ArticleList.setAdapter(articleListAdapter);
        ArticleList.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void initListener() {
        swipeRefreshLayout.setOnRefreshListener(this);

        articleListAdapter.setOnItemClickListener(new ArticleListAdapter.OnItemClickListener() {
            @Override
            public void onArticleItemClickListener(View view) {
                Toast.makeText(context,"v.getId()"+view.getId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),DetailActivity.class);
                intent.putExtra("url", Constants.getInstance().getArticleList().get(view.getId()).getUrl());
                intent.putExtra("article_detail_title",Constants.getInstance().getArticleList().get(view.getId()).getTitle());
                startActivity(intent);
            }
        });
        scrollListener = new ArticleScrollListener((LinearLayoutManager) ArticleList.getLayoutManager()) {
            @Override
            public void onLoadMore() {
                if (!isLoadingMore){
                    isLoadingMore = true;
                    loadMoreData();
                }

            }
        };

        ArticleList.addOnScrollListener(scrollListener);
    }

    private void loadMoreData(){
        new LoadAsyncTask().execute();
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
                Networks.getInstance().doPost(Networks.basepath+"/article/getArticles",object).enqueue(new Callback() {
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
                        Article article = new Article();
                        article.setType(Article.BANNER);
                        articleList.add(article);
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

    class LoadAsyncTask extends AsyncTask<Integer,Void,List<Article>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("MainActivity","onPreExecute");
            articleListAdapter.addFoot();

        }

        @Override
        protected List<Article> doInBackground(Integer... params) {
            Log.d("MainActivity","doInBackground");
            JSONObject object = new JSONObject();
            List<Article> articleList = null;
            try {
                object.put("lastIndex",Networks.getInstance().lastIndex);
                Response response = Networks.getInstance().doPost(Networks.basepath+"/article/getArticles",object).execute();
                if (response.code() == 200){
                    articleList = new ArrayList<Article>();
                    Article article;
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    JSONObject jsonObject;
                    for (int i=0;i<jsonArray.length();i++){
                        jsonObject = (JSONObject) jsonArray.get(i);
                        article = new Article();
                        article.setId(jsonObject.getInt("id"));
                        article.setTitle(jsonObject.getString("title"));
                        article.setClassify(jsonObject.getString("classify"));
                        article.setSummary(jsonObject.getString("summary"));
                        article.setUrl(jsonObject.getString("url"));
                        article.setType(Article.ARTITLE);
                        articleList.add(article);
                        Networks.getInstance().lastIndex++;
                    }
                    Thread.sleep(1000);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return articleList;
        }

        @Override
        protected void onPostExecute(List<Article> newArticles) {
            Log.d("MainActivity","onPostExecute");
            super.onPostExecute(newArticles);
            if (newArticles == null){
                Log.d("MainActivity","newArticles null");
                articleListAdapter.setFootText(ArticleListAdapter.FOOT_ERROR);
                scrollListener.setLoading(false);
            }
           else if (newArticles.size() == 0){
                Log.d("MainActivity","newArticles size 0");
                articleListAdapter.setFootText(ArticleListAdapter.FOOT_END);
                scrollListener.setLoading(true);
            }
            else if (newArticles.size() > 0){
                Log.d("MainActivity","newArticles size > 0");
                articleListAdapter.removeFoot();
                Constants.getInstance().getArticleList().addAll(newArticles);
                articleListAdapter.notifyDataSetChanged();
                scrollListener.setLoading(false);
            }
            isLoadingMore = false;
        }
    }

    public void refreshUI(){
        articleListAdapter.notifyDataSetChanged();
    }
}
