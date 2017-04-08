package com.example.aria.baike.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.aria.baike.R;
import com.example.aria.baike.global.Constants;
import com.example.aria.baike.loader.GlideImageLoader;
import com.example.aria.baike.model.Article;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aria on 2017/2/18.
 */


public class ArticleListAdapter extends RecyclerView.Adapter<ArticleViewHolder>{

    private Context context;
    private final int BANNER_VIEWTYPE = 0x0123;
    private final int ARTITLE_VIEWTYPE = 0x0124;
    private List<Article> articleList;
    List list;
    OnItemClickListener onItemClickListener;

    private List<Integer> BannerImages;
    private List<String> BannerTitles;

    public ArticleListAdapter(Context context){
        this.context = context;
        articleList = Constants.getInstance().getArticleList();
        BannerImages = new ArrayList<Integer>();
        BannerTitles = new ArrayList<String>();
        BannerImages.add(R.drawable.banner01);
        BannerImages.add(R.drawable.banner02);
        BannerTitles.add("title01");
        BannerTitles.add("震惊！UC震惊部！");
        list = new ArrayList();
        for (int i=0;i<7;i++){
            list.add(i);
        }
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ArticleViewHolder holder;
        switch (viewType) {
            case BANNER_VIEWTYPE:
                holder = new ArticleViewHolder(LayoutInflater.from(context).inflate(R.layout.banner_header_layout, parent, false), true);
                break;
            case ARTITLE_VIEWTYPE:
                holder = new ArticleViewHolder(LayoutInflater.from(context).inflate(R.layout.item_article_content, parent, false), false);
                break;
            default:
                holder = null;
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder,int position) {
        Log.d("MainActivity",position+"");
        switch (getItemViewType(position)){
            case BANNER_VIEWTYPE:
                Banner banner = holder.banner;
                banner.setImageLoader(new GlideImageLoader());
                banner.setImages(BannerImages)
                        .setBannerTitles(BannerTitles)
                        .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                        .start();
                break;
            case ARTITLE_VIEWTYPE:
                final int pos = position;

                holder.title.setText(articleList.get(position-1).getTitle());
                holder.summary.setText(articleList.get(position-1).getSummary());
                holder.from.setText("来自类别："+articleList.get(position-1).getClassify());

                holder.summary.setId(pos);
                holder.summary.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onArticleItemClickListener(v);
                    }
                });

                holder.topicBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(context,v);
                        popupMenu.getMenuInflater().inflate(R.menu.article_popedmenu,popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()){
                                    case R.id.item_delete:
                                        removeItem(pos);
                                }
                                return false;
                            }
                        });
                        popupMenu.show();
                    }
                });
        }
    }

    @Override
    public int getItemCount() {
        return articleList.size()+1;
    }

    public void removeItem(int position){
        Toast.makeText(context,"position:"+position,Toast.LENGTH_SHORT).show();
        articleList.remove(position-1);
        notifyItemRemoved(position);
//        notifyItemRangeChanged(position,list.size());
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return BANNER_VIEWTYPE;
        }else return ARTITLE_VIEWTYPE;
    }

    public interface OnItemClickListener{
        void onArticleItemClickListener(View v);
        void onBannerClickListener();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void updateArticleList(){
        articleList = Constants.getInstance().getArticleList();
    }
}


