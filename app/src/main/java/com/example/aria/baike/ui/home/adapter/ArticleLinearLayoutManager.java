package com.example.aria.baike.ui.home.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by Aria on 2017/4/19.
 */

public class ArticleLinearLayoutManager extends LinearLayoutManager{
    public ArticleLinearLayoutManager(Context context) {
        super(context);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }
}
