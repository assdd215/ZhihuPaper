package com.example.aria.baike.ui.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.aria.baike.R;

/**
 * Created by Aria on 2017/4/13.
 */

public class FootViewHolder extends RecyclerView.ViewHolder{

    public TextView foot;

    public FootViewHolder(View itemView) {
        super(itemView);
        foot = (TextView) itemView.findViewById(R.id.footer);
    }
}
