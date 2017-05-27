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
import com.example.aria.baike.global.UserMessage;
import com.example.aria.baike.ui.person.adapter.DividerGridItemDecoration;
import com.example.aria.baike.ui.person.adapter.FeaturesGridAdapter;
import com.example.aria.baike.ui.person.adapter.PersonMenuAdapter;

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


    private PersonMenuAdapter personMenuAdapter;
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
        titles.add("我的关注");
        titles.add("我的收藏");
        titles.add("我的草稿");
        titles.add("最近浏览");
        titles.add("我的书架");
        titles.add("我的Live");

        List<Integer> images = new ArrayList<Integer>();
        images.add(R.drawable.tubiao01);
        images.add(R.drawable.tubiao02);
        images.add(R.drawable.tubiao03);
        images.add(R.drawable.tubiao04);
        images.add(R.drawable.tubiao06);
        images.add(R.drawable.tubiao07);

        personMenuAdapter = new PersonMenuAdapter(context,titles,images);
    }

    @Override
    public void initView() {
//        Container.setLayoutManager(new GridLayoutManager(context,3));
//        Container.addItemDecoration(new DividerGridItemDecoration(context));
        Container.setLayoutManager(new LinearLayoutManager(context));
        Container.setAdapter(personMenuAdapter);
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
        if (username == null){
            header_username.setText("");
        }else
          header_username.setText(username);
    }


    public void setAccount(String account){
        Log.d("MainActivity","setAccount"+account);
        if (account == null){
            header_account.setText("账号：");
        }else
        header_account.setText("账号:"+account);
    }

    public void setAvactor(String account){
        File file = new File(Constants.PATH+"/"+account+"/avactor.png");
        if (file.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(Constants.PATH+"/"+account+"/avactor.png");
            person_avactor.setImageBitmap(bitmap);
        }else {
            person_avactor.setImageResource(R.drawable.comment_avatar);
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserMessage.getInstance().getAccount()!=null){
            Log.d("MainActivity","personFragment account :" + UserMessage.getInstance().getAccount());
            header_account.setText("账号：" + UserMessage.getInstance().getAccount());
            setAvactor(UserMessage.getInstance().getAccount());
        }else {
            Log.d("MainActivity","personFragment account null");
            header_account.setText("账号：");
            setAvactor(null);
        }
        if (UserMessage.getInstance().getUserName()!=null){
            Log.d("MainActivity",UserMessage.getInstance().getUserName().equals("null")+"");
            Log.d("MainActivity","personFragment not null userName :" + UserMessage.getInstance().getUserName());
            if (UserMessage.getInstance().getUserName().equals("")){
                header_username.setText("你的昵称");
            }else
            header_username.setText(UserMessage.getInstance().getUserName());
        }else {
            Log.d("MainActivity","personFragment userName null Account null");
            header_account.setText("请登录");
        }
    }
}
