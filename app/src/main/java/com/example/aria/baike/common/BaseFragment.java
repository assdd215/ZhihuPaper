package com.example.aria.baike.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Aria on 2017/2/14.
 */

public class BaseFragment extends Fragment {

    protected View contentView;
    protected ViewGroup container;
    protected Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initData();
        initView();
        initListener();
        this.container = container;
        return contentView;
    }

    public void setContentView(int viewId) {
        this.contentView = getActivity().getLayoutInflater().inflate(viewId,container,false);
    }

    public View getContentView() {
        return contentView;
    }

    public void initData(){}

    public void initView(){}

    public void initListener(){}

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return context;
    }
}


