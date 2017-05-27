package com.example.aria.baike.ui.home.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aria.baike.R;
import com.example.aria.baike.global.Constants;
import com.example.aria.baike.loader.GlideImageLoader;
import com.example.aria.baike.model.Article;
import com.example.aria.baike.util.CommonUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aria on 2017/4/13.
 */

public class ArticleListAdapter extends RecyclerView.Adapter{

    private Context context;
    private static final int BANNER_VIEWTYPE = 0x0123;
    private static final int ARTITLE_VIEWTYPE = 0x0124;
    private static final int FOOT_VIEWTYPE = 0x0125;

    public static final String FOOT_LOADING = "正在载入中……";
    public static final String FOOT_END = "没有更多数据";
    public static final String FOOT_ERROR = "加载出错！";

    private List<Article> articleList;
    private List<Integer> BannerImages;
    private List<String> BannerTitles;
    private TextView footText;

    private OnItemClickListener onItemClickListener;

    public ArticleListAdapter(Context context){
        this.context = context;
        articleList = Constants.getInstance().getArticleList();
        BannerImages = new ArrayList<Integer>();
        BannerTitles = new ArrayList<String>();
        BannerImages.add(R.drawable.banner01);
        BannerImages.add(R.drawable.banner02);
        BannerImages.add(R.drawable.banner03);
        BannerImages.add(R.drawable.banner04);
        BannerImages.add(R.drawable.banner05);
        BannerTitles.add("小事 · 并非有意害人，只是本性如此");
        BannerTitles.add("原作者还没画完就去世了，那他的作品怎么办？");
        BannerTitles.add("行走在都柏林，旅途就像一场电影");
        BannerTitles.add("春天尝起来是什么味道？");
        BannerTitles.add("科幻电影里，这些设计都已经变成了常客");
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;

        switch (viewType){
            case BANNER_VIEWTYPE:
                holder = new BannerViewHolder(LayoutInflater.from(context).inflate(R.layout.banner_header_layout,parent,false));

                break;
            case ARTITLE_VIEWTYPE:
                holder = new ArticleViewHolder(LayoutInflater.from(context).inflate(R.layout.item_article_content,parent,false));
                break;
            case FOOT_VIEWTYPE:
                holder = new FootViewHolder(LayoutInflater.from(context).inflate(R.layout.item_article_foot,parent,false));
                footText = ((FootViewHolder)holder).foot;
                break;
            default:
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FootViewHolder){
            return;
        }
        if (holder instanceof ArticleViewHolder){
            final int pos = position;
            ArticleViewHolder articleViewHolder = (ArticleViewHolder) holder;
            articleViewHolder.title.setText(articleList.get(position).getTitle());
            articleViewHolder.summary.setText(articleList.get(position).getSummary());
            articleViewHolder.from.setText("来自类别："+articleList.get(position).getClassify());

            articleViewHolder.summary.setId(pos);
            articleViewHolder.summary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onArticleItemClickListener(v);
                }
            });

            articleViewHolder.topicBtn.setOnClickListener(new View.OnClickListener() {
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

            articleViewHolder.layoutBackground.setBackgroundColor(CommonUtil.getAttrColor(R.attr.colorCardViewBackground,context));
            articleViewHolder.summary.setTextColor(CommonUtil.getAttrColor(R.attr.colorCardViewContentColor,context));
            articleViewHolder.title.setTextColor(CommonUtil.getAttrColor(R.attr.colorCardViewTitleColor,context));
        }
        if (holder instanceof BannerViewHolder){
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            Banner banner = bannerViewHolder.banner;
            banner.setImageLoader(new GlideImageLoader());
            banner.setImages(BannerImages)
                    .setBannerTitles(BannerTitles)
                    .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                    .start();
        }

    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && articleList.get(position).getType() == Article.BANNER)return BANNER_VIEWTYPE;
        if(articleList.get(position).getType() == Article.FOOT){
            return FOOT_VIEWTYPE;
        }
        return ARTITLE_VIEWTYPE;
    }

    public void removeItem(int position){
        articleList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,articleList.size() - position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onArticleItemClickListener(View view);
    }

    public void addFoot(){
        if (articleList.get(articleList.size()-1).getType() == FOOT_VIEWTYPE){
            footText.setText(FOOT_LOADING);
            return;
        }
        Article article = new Article();
        article.setType(Article.FOOT);
        articleList.add(article);
        notifyItemInserted(articleList.size()-1);
    }

    public void setFootText(String text){
        if (footText == null)return;
        footText.setText(text);
    }

    public void removeFoot(){
        articleList.remove(articleList.size()-1);
    }
}
