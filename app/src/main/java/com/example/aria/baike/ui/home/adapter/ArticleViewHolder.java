package com.example.aria.baike.ui.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aria.baike.R;
import com.youth.banner.Banner;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Aria on 2017/2/18.
 */

public class ArticleViewHolder extends RecyclerView.ViewHolder{

    TextView from;
    CircleImageView topicAvactor;
    TextView title;
    TextView summary;
    ImageView topicBtn;

    public ArticleViewHolder(View itemView){
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.item_ariticle_title);
        summary = (TextView) itemView.findViewById(R.id.item_article_summary);
        topicBtn = (ImageView) itemView.findViewById(R.id.topic_image);
        topicAvactor = (CircleImageView) itemView.findViewById(R.id.topic_avactor);
        from = (TextView) itemView.findViewById(R.id.topic_from_textview);
    }
}
