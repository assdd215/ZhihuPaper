package com.example.aria.baike.ui.classify;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.widget.TextView;

import com.example.aria.baike.R;
import com.example.aria.baike.common.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aria on 2017/3/1.
 */

public class ContentFragment extends BaseFragment{

    @BindView(R.id.textview)
    TextView textView;

    String tab;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_classify_pager_content);
        ButterKnife.bind(this,contentView);
    }

    @Override
    public void initData() {
        tab = getArguments().getString("tab");
    }

    @Override
    public void initView() {
        textView.setText("该界面尚未完善");
    }

    public static ContentFragment newInstance(String tab){
        ContentFragment fragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tab",tab);
        fragment.setArguments(bundle);
        return fragment;
    }


}
