package com.example.aria.baike.global;

import android.content.Context;
import android.os.Environment;

import com.example.aria.baike.model.Article;

import java.util.List;

/**
 * Created by Aria on 2017/2/22.
 */

public  class Constants {
    public static final int LOCATIONATY_RESPONSE = 0;
    public static final int LOGINATY_RESPONSE = 1;
    public static final int LOGINATY_REQUEST = 2;

    private List<Article> articleList;

    public static final String PATH = Environment.getExternalStorageDirectory().getPath()+"/com/aria/Baike";
    private static Constants constants = null;

    public boolean isLogin = false;

    private Constants(){}

    public static Constants getInstance(){
        if (constants == null){
            constants = new Constants();
        }
        return constants;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public List<Article> getArticleList() {
        return articleList;
    }
}
