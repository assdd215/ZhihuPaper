package com.example.aria.baike.ui.classify;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.example.aria.baike.R;
import com.example.aria.baike.common.BaseFragment;
import com.example.aria.baike.ui.classify.adapter.ContentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aria on 2017/3/1.
 */

public class ClassifyFragment extends BaseFragment{

    @BindView(R.id.Container)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    List<String> tabIndicators;
    List<Fragment> tabFragments;
    List<View> viewList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_classify);

        ButterKnife.bind(this,getContentView());
    }

    public static ClassifyFragment newInstance(){
        ClassifyFragment fragment = new ClassifyFragment();
        return fragment;
    }

    @Override
    public void initData() {
        tabIndicators = new ArrayList<String>();
        tabFragments = new ArrayList<Fragment>();
        viewList = new ArrayList<View>();
    }

    @Override
    public void initView() {
        initTab();
        initContent();
    }

    private void initTab(){
        tabLayout.setupWithViewPager(viewPager);

    }

    private void initContent(){
        for (int i=0;i<8;i++){
            tabIndicators.add("Tab"+i);
            tabFragments.add(ContentFragment.newInstance(tabIndicators.get(i)));
        }
        viewPager.setAdapter(new ContentPagerAdapter(getChildFragmentManager(),tabFragments,tabIndicators));
    }
}
