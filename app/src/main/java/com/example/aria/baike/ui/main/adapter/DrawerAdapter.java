package com.example.aria.baike.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aria.baike.R;
import com.example.aria.baike.util.CommonUtil;

import java.util.List;

/**
 * Created by Aria on 2017/4/8.
 */

public class DrawerAdapter extends RecyclerView.Adapter<drawerViewHolder>{

    private static final int VIEWTYPE_HOME = 0;
    private static final int VIEWTYPE_TAG = 1;

    private Context context;
    private List<String> itemList;
    private OnItemClickListener onItemClickListener;


    public DrawerAdapter(Context context, List<String> itemList){
        this.context = context;
        this.itemList = itemList;

    }

    @Override
    public drawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEWTYPE_HOME){
            drawerViewHolder holder = new drawerViewHolder(LayoutInflater.from(context).inflate(R.layout.item_drawer_taghome,parent,false));
            return holder;
        }
        drawerViewHolder holder = new drawerViewHolder(LayoutInflater.from(context).inflate(R.layout.item_drawer_tag,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(drawerViewHolder holder, int position) {
        holder.item_theme.setId(position);
        holder.item_theme.setText(itemList.get(position));
        holder.item_theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v);
            }
        });

        holder.item_theme.setTextColor(CommonUtil.getAttrColor(R.attr.colorDrawerMenuText,context));
        holder.drawer_layout.setBackgroundColor(CommonUtil.getAttrColor(R.attr.colorDrawerMenu,context));
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)return VIEWTYPE_HOME;
        else return VIEWTYPE_TAG;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view);
    }
}

class drawerViewHolder extends RecyclerView.ViewHolder{

    TextView item_theme;
    ImageView image_follow;
    View itemView;
    LinearLayout drawer_layout;

    public drawerViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        item_theme = (TextView) itemView.findViewById(R.id.item_theme);
        image_follow = (ImageView) itemView.findViewById(R.id.follow_iv);
        drawer_layout = (LinearLayout) itemView.findViewById(R.id.item_layout);
    }
}
