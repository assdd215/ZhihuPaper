package com.example.aria.baike.common;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.aria.baike.R;
import com.example.aria.baike.util.StatusBarCompat;

/**
 * Created by Aria on 2017/2/13.
 */

public class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        StatusBarCompat.compat(this,getResources().getColor(R.color.colorDarkBlue));
        initData();
        initView();
        initListener();
    }

    public void initView(){}

    public void initData(){}

    public void initListener(){}

    public BaseActivity getActivity(){
        return this;
    }
}
