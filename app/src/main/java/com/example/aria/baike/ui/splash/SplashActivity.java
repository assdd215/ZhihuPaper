package com.example.aria.baike.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.aria.baike.R;
import com.example.aria.baike.common.BaseActivity;
import com.example.aria.baike.global.Constants;
import com.example.aria.baike.global.Networks;
import com.example.aria.baike.model.Article;
import com.example.aria.baike.ui.main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Aria on 2017/2/13.
 */

public class SplashActivity extends BaseActivity{

    String str;
    List<Article> articleList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
    }

    @Override
    public void initData() {
        articleList = new ArrayList<Article>();
        loadData();
    }

    private void loadData(){
        Call call = Networks.getInstance().getAllArticles();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                str = response.body().string();
                Article article;
                JSONObject jsonObject;
                Log.d("MainActivity",str);
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
                    }
                    Constants.getInstance().setArticleList(articleList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent();
                        intent.putExtra("articleList",str);
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                });
            }
        });
    }
}
