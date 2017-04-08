package com.example.aria.baike.ui.person.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aria.baike.R;

import java.util.List;

/**
 * Created by Aria on 2017/2/14.
 */

public class FeaturesGridAdapter extends RecyclerView.Adapter<FeaturesViewHolder>{

    private List<String> titles;
    private List<Integer> images;
    private Context context;

    public FeaturesGridAdapter(Context context,List<String> titles, List<Integer> images){
        this.titles = titles;
        this.images = images;
        this.context = context;
    }

    @Override
    public FeaturesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FeaturesViewHolder holder = new FeaturesViewHolder(LayoutInflater.from(context).
                inflate(R.layout.person_feature_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(FeaturesViewHolder holder, int position) {
        holder.title.setText(titles.get(position));
        holder.image.setBackgroundResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}

class FeaturesViewHolder extends RecyclerView.ViewHolder{

    TextView title;
    ImageView image;
    RelativeLayout feature_layout;

    public FeaturesViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.item_title);
        image = (ImageView) itemView.findViewById(R.id.item_image);
        feature_layout = (RelativeLayout) itemView.findViewById(R.id.feature_layout);
    }
}
