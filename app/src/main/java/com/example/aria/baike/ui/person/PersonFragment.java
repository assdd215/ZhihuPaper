package com.example.aria.baike.ui.person;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aria.baike.R;
import com.example.aria.baike.common.BaseFragment;
import com.example.aria.baike.global.Constants;
import com.example.aria.baike.ui.person.adapter.DividerGridItemDecoration;
import com.example.aria.baike.ui.person.adapter.FeaturesGridAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aria on 2017/2/14.
 */

public class PersonFragment extends BaseFragment{

    private FeaturesGridAdapter gridAdapter;
    private OnItemClickListener onItemClickListener;

    @BindView(R.id.Feature_Container)
    RecyclerView Container;
    @BindView(R.id.header_layout)
    RelativeLayout header_layout;
    @BindView(R.id.header_username)
    TextView header_username;
    @BindView(R.id.header_account)
    TextView header_account;
    @BindView(R.id.person_avactor)
    ImageView person_avactor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_content);
        ButterKnife.bind(this,getContentView());
    }

    @Override
    public void initData() {
        List<String> titles = new ArrayList<String>();
        titles.add("我的百科");
        titles.add("我的咨询");
        titles.add("我的笔记");
        titles.add("浏览历史");
        titles.add("我的百科");
        titles.add("我的咨询");
        titles.add("我的笔记");
        titles.add("浏览历史");

        List<Integer> images = new ArrayList<Integer>();
        images.add(R.drawable.ic_library_books_black_36dp);
        images.add(R.drawable.ic_question_answer_black_36dp);
        images.add(R.drawable.ic_note_black_36dp);
        images.add(R.drawable.ic_history_black_36dp);
        images.add(R.drawable.ic_library_books_black_36dp);
        images.add(R.drawable.ic_question_answer_black_36dp);
        images.add(R.drawable.ic_note_black_36dp);
        images.add(R.drawable.ic_history_black_36dp);

        gridAdapter = new FeaturesGridAdapter(context,titles,images);
    }

    @Override
    public void initView() {
        Container.setLayoutManager(new GridLayoutManager(context,3));
        Container.addItemDecoration(new DividerGridItemDecoration(context));
        Container.setAdapter(gridAdapter);
    }

    @Override
    public void initListener() {
        header_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity","onHeader Click");
                onItemClickListener.onHeaderClick(v);
            }
        });
    }

    public static PersonFragment newInstance(Context context) {

        Bundle args = new Bundle();

        PersonFragment fragment = new PersonFragment();
        fragment.setContext(context);
        return fragment;
    }

    public interface OnItemClickListener{
        void onHeaderClick(View view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setUseranme(String username){
        header_username.setText(username);
    }

    public void setAccount(String account){
        header_account.setText("账号:"+account);
    }

    public void setAvactor(String account){
        File file = new File(Constants.PATH+"/"+account+"/avactor.png");
        if (file.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(Constants.PATH+"/"+account+"/avactor.png");
            person_avactor.setImageBitmap(bitmap);
        }else {
            Toast.makeText(getActivity(),"查无头像图片",Toast.LENGTH_SHORT).show();
        }


    }
}
