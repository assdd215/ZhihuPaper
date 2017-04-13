package com.example.aria.baike.ui.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.aria.baike.R;
import com.example.aria.baike.loader.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aria on 2017/2/18.
 */

public class BannerViewHolder extends RecyclerView.ViewHolder{

    public Banner banner;

    public BannerViewHolder(View itemView) {
        super(itemView);
        banner = (Banner) itemView.findViewById(R.id.banner);
    }
}
