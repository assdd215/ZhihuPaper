package com.example.aria.baike.ui.person.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aria.baike.R;

import java.util.List;

/**
 * Created by Aria on 2017/4/9.
 */

public class PersonMenuAdapter extends RecyclerView.Adapter<PersonViewHolder>{

    private Context context;
    private List<String> itemList;
    private List<Integer> iconList;

    public PersonMenuAdapter(Context context,List<String>itemList,List<Integer>iconList){
        this.context = context;
        this.itemList = itemList;
        this.iconList = iconList;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PersonViewHolder holder = new PersonViewHolder(LayoutInflater.from(context).inflate(R.layout.item_person_menu,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        holder.textView.setText(itemList.get(position));
//        holder.imageView.setBackgroundDrawable(context.getResources().getDrawable(iconList.get(position)));
        holder.imageView.setImageResource(iconList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}

class PersonViewHolder extends RecyclerView.ViewHolder{

    TextView textView;
    ImageView imageView;

    public PersonViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.menu_text);
        imageView = (ImageView) itemView.findViewById(R.id.menu_icon);
    }
}
