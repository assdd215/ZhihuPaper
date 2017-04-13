package com.example.aria.baike.ui.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.aria.baike.R;
import com.example.aria.baike.common.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aria on 2017/2/27.
 */

public class DetailActivity extends BaseActivity{

    @BindView(R.id.WebContent)
    WebView WebContent;
    @BindView(R.id.article_detail_title)
    TextView article_detail_title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    String url;
    String title;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("article_detail_title");
    }

    @Override
    public void initView() {

        initWebContent(url);
        article_detail_title.setText(title);
        initToolBar();
    }

    public void initWebContent(String url){
        WebSettings webSettings = WebContent.getSettings();
        webSettings.setSupportZoom(true); // 设置支持缩放
//        webSettings.setTextZoom(200);
        webSettings.setUseWideViewPort(true); // 设置适配屏幕
        WebContent.loadUrl(url);
        WebContent.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    public void initToolBar(){
        toolbar.inflateMenu(R.menu.article_toolbar_menu);
        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_actionbar_menu_overflow));
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_36dp);
//        setSupportActionBar(toolbar);
    }

    @Override
    public void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
