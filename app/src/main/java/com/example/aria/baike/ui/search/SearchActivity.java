package com.example.aria.baike.ui.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.example.aria.baike.R;
import com.example.aria.baike.common.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aria on 2017/2/17.
 */

public class SearchActivity extends BaseActivity{

    @BindView(R.id.search_backbtn)
    ImageView SearchBackBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
    }

    @Override
    public void initListener() {
        SearchBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
