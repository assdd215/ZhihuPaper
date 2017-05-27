package com.example.aria.baike.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

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
        Article article = new Article();
        article.setType(Article.BANNER);
        articleList.add(0,article);
        loadData();
    }

    private void loadData(){
        JSONObject object = new JSONObject();
        try {
            object.put("lastIndex",Networks.getInstance().lastIndex);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Call call = Networks.getInstance().doPost(Networks.basepath+"/article/getArticles",object);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"获取数据失败！",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("MainActivity","response code:"+response.code());
                if (response.code()!= 200){
                    finish();
                    Log.d("MainActivity","response code:"+response.code());
                    return;
                }
                str = response.body().string();
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
                        article.setType(Article.ARTITLE);
                        articleList.add(article);
                        Networks.getInstance().lastIndex++;
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
                        if (getIntent().getBundleExtra(Constants.LAUNCH_BY_NOTIFICATION)!=null){
                            intent.putExtra(Constants.LAUNCH_BY_NOTIFICATION,getIntent().
                                    getBundleExtra(Constants.LAUNCH_BY_NOTIFICATION));
                        }
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                });
            }
        });
    }
}
