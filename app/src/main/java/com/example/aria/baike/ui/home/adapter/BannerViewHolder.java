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

    Banner banner;

    public BannerViewHolder(View itemView) {
        super(itemView);
        banner = (Banner) itemView.findViewById(R.id.banner);
    }

    public void initBanner(){
       List BannerImages = new ArrayList<Integer>();
       List BannerTitles = new ArrayList<String>();
        BannerImages.add(R.drawable.banner01);
        BannerImages.add(R.drawable.banner02);
        BannerTitles.add("title01");
        BannerTitles.add("震惊！UC震惊部！");
        if (banner == null){
            Log.d("MainActivity","banner is null");
        }
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(BannerImages).setBannerTitles(BannerTitles).setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
    }
}
