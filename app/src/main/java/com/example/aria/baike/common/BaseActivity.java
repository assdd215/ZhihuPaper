package com.example.aria.baike.common;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
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
        Resources.Theme theme = getTheme();
        TypedValue typedValue = new TypedValue();
        theme.resolveAttribute(R.attr.colorAccent,typedValue,true);

        StatusBarCompat.compat(this, ResourcesCompat.getColor(getResources(),typedValue.resourceId,null));
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
