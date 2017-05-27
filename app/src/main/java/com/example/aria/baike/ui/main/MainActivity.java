package com.example.aria.baike.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.aria.baike.R;
import com.example.aria.baike.TestActivity;
import com.example.aria.baike.common.BaseActivity;
import com.example.aria.baike.global.Constants;
import com.example.aria.baike.model.Article;
import com.example.aria.baike.model.User;
import com.example.aria.baike.global.UserMessage;
import com.example.aria.baike.ui.classify.ClassifyFragment;
import com.example.aria.baike.ui.detail.DetailActivity;
import com.example.aria.baike.ui.home.HomeFragment;
import com.example.aria.baike.ui.login.LoginActivity;
import com.example.aria.baike.ui.main.adapter.DrawerAdapter;
import com.example.aria.baike.ui.map.LocationActivity;
import com.example.aria.baike.ui.person.PersonFragment;
import com.example.aria.baike.util.CommonUtil;
import com.example.aria.baike.util.StatusBarCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener{

    private int LastNavigationSelectedPosition = 0;
    private List<Article> articleList;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public final static String HOMEFRAGMENT_ID = "FRAGMENT_TAG1";
    public final static String PERSONFRAGMENT_ID = "FRAGMENT_TAG2";
    public Fragment current_id;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation_bottom_bar)
    BottomNavigationBar navigationBar;
    @BindView(R.id.Container)
    FrameLayout Container;

    //左滑布局
    @BindView(R.id.drawerContainer)
    RecyclerView drawerContainer;
    @BindView(R.id.user_icon)
    CircleImageView user_icon;
    @BindView(R.id.login)
    TextView user_name;
    @BindView(R.id.layout_user_message)
    LinearLayout layout_user_message;
    @BindView(R.id.drawerHeaderLayout)
    LinearLayout drawerHeaderLayout;

    private HomeFragment homeFragment;
    private PersonFragment personFragment;
    private ClassifyFragment classifyFragment;

    private List<String> drawerList;
    private DrawerAdapter drawerAdapter;

    private boolean isDayTheme = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (getIntent().getBundleExtra(Constants.LAUNCH_BY_NOTIFICATION)!=null){
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            Bundle bundle = getIntent().getBundleExtra(Constants.LAUNCH_BY_NOTIFICATION);
            String url = bundle.getString("url");
            String article_detail_title = bundle.getString("article_detail_title");
            intent.putExtra("url",url);
            intent.putExtra("article_detail_title",article_detail_title);
            startActivity(intent);
        }
    }

    @Override
    public void initData() {

        //初始化用户信息
        sharedPreferences = getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
        User user = new User(sharedPreferences.getString("account",null),sharedPreferences.getString("password",null),
                sharedPreferences.getInt("id",-1),sharedPreferences.getString("username",null),sharedPreferences.getString("avactor",null));
        UserMessage.getInstance().setUser(user);

        UserMessage userMessage = UserMessage.getInstance();

        if (getFragmentManager().findFragmentByTag(HOMEFRAGMENT_ID)==null){
            homeFragment = new HomeFragment();
            current_id = homeFragment;
        }

        if (getFragmentManager().findFragmentByTag(PERSONFRAGMENT_ID)==null){
            personFragment = PersonFragment.newInstance(this);

        }

        classifyFragment = ClassifyFragment.newInstance();
    }

    @Override
    public void initView() {
        initToolBar();
        initDrawer();
        initNavigationBottomBar();
        setDefaultFragment();
    }

    private void initDrawer(){
        String []drawerTag = getResources().getStringArray(R.array.drawer_tag);
        drawerList = new ArrayList<String>(drawerTag.length);
        for (int i=0;i<drawerTag.length;i++){
            drawerList.add(drawerTag[i]);
        }
        drawerAdapter = new DrawerAdapter(this,drawerList);
        drawerContainer.setAdapter(drawerAdapter);
        drawerContainer.setLayoutManager(new LinearLayoutManager(this));

        //设置头像信息

    }

    private void initToolBar(){

        toolbar.inflateMenu(R.menu.home_menu);
        toolbar.setNavigationIcon(R.drawable.ic_actionbar_menu);
        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_actionbar_menu_overflow));
    }

    private void initNavigationBottomBar(){
        navigationBar.addItem(new BottomNavigationItem(R.drawable.ic_home_white_36dp,"主页"))
                     .addItem(new BottomNavigationItem(R.drawable.ic_apps_white_36dp,"分类"))
                     .addItem(new BottomNavigationItem(R.drawable.ic_map_white_36dp,"地图"))
                     .addItem(new BottomNavigationItem(R.drawable.ic_perm_identity_white_36dp,"我的"))
                     .setFirstSelectedPosition(LastNavigationSelectedPosition)
                     .setMode(BottomNavigationBar.MODE_FIXED)
                     .initialise();
    }

    private void setDefaultFragment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.Container,personFragment,PERSONFRAGMENT_ID).hide(personFragment)
                    .add(R.id.Container,classifyFragment).hide(classifyFragment)
                    .add(R.id.Container,homeFragment,HOMEFRAGMENT_ID).show(homeFragment);
        transaction.commit();
    }

    @Override
    public void initListener() {
        navigationBar.setTabSelectedListener(this);

        personFragment.setOnItemClickListener(new PersonFragment.OnItemClickListener() {
            @Override
            public void onHeaderClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, LoginActivity.class),Constants.LOGINATY_REQUEST);
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_theme:
                        if (isDayTheme)setTheme(R.style.NightTheme);
                        else setTheme(R.style.DayTheme);
                        isDayTheme = !isDayTheme;
                        refreshUI();
                        break;
                }
                return true;
            }
        });

        drawerAdapter.setOnItemClickListener(new DrawerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                Toast.makeText(getActivity(),drawerList.get(view.getId()),Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawers();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)){
                    drawerLayout.closeDrawers();
                }else {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });

        layout_user_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                navigationBar.selectTab(3);
            }
        });


    }

    @Override
    public void onTabSelected(int position) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (position){
            case 0:
                ((TextView)toolbar.findViewById(R.id.toolbar_title)).setText("主页");
                if (current_id != homeFragment){
                    if (homeFragment == null){
                        homeFragment = new HomeFragment();
                    }
                    transaction.hide(current_id).show(homeFragment);
                }
                current_id = homeFragment;
                toolbar.setVisibility(View.VISIBLE);
                break;
            case 1:
                if (current_id != classifyFragment){
                    transaction.hide(current_id).show(classifyFragment);
                }
                ((TextView)toolbar.findViewById(R.id.toolbar_title)).setText("分类");
                current_id = classifyFragment;
                toolbar.setVisibility(View.GONE);
                break;
            case 2:
                startActivityForResult(new Intent(MainActivity.this, LocationActivity.class),0);
                toolbar.setVisibility(View.VISIBLE);
                break;
            case 3:
                ((TextView)toolbar.findViewById(R.id.toolbar_title)).setText("个人中心");
//                transaction.replace(R.id.Container,personFragment);
                if (current_id != personFragment){
                    if (personFragment == null){
                        personFragment = PersonFragment.newInstance(this);
                    }
                    transaction.hide(current_id).show(personFragment);
                }
                current_id = personFragment;
                toolbar.setVisibility(View.VISIBLE);
                break;

        }
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (UserMessage.getInstance().getUserName() != null){
            Log.d("MainActivity","MainActivity userName :" + UserMessage.getInstance().getUserName());
            if (UserMessage.getInstance().getUserName().equals(""))
                user_name.setText("你的昵称");
            else user_name.setText(UserMessage.getInstance().getUserName());
        }else {
            Log.d("MainActivity","MainActivity userName null account null" );
            user_name.setText("请登录");
        }
        if (UserMessage.getInstance().getAccount()!=null){
            decodeBitmap(UserMessage.getInstance().getAccount());
        }else {
            Log.d("MainActivity","UserMessage.getInstance().getAccount()=null");
            user_icon.setImageResource(R.drawable.comment_avatar);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case Constants.LOCATIONATY_RESPONSE:
                navigationBar.selectTab(0);
                current_id = homeFragment;
                break;
            case Constants.LOGINATY_RESPONSE:
                User user = new User(data.getStringExtra("account"),data.getStringExtra("password"),data.getIntExtra("id",0),data.getStringExtra("username"),data.getStringExtra("avactor"));
                UserMessage.getInstance().setUser(user);
                editor = sharedPreferences.edit();
                editor.putString("username",user.getUsername());
                editor.putString("password",user.getPassword());
                editor.putString("account",user.getAccount());
                editor.putInt("id",user.getId());
                editor.putString("avactor",user.getAccount());
                editor.apply();
                break;
        }
    }

    private void decodeBitmap(String account){
        File file = new File(Constants.PATH+"/"+account+"/avactor.png");
        if (file.exists()){
            Log.d("MainActivity","file exists"+file.getPath());
            Bitmap bitmap = BitmapFactory.decodeFile(Constants.PATH+"/"+account+"/avactor.png");
            user_icon.setImageBitmap(bitmap);
        }else {
            Log.d("MainActivity","file not exists");
            user_icon.setImageResource(R.drawable.comment_avatar);
        }
    }

    private void refreshUI(){
        toolbar.setBackgroundColor(CommonUtil.getAttrColor(R.attr.toolbarColor,this));

//        navigationBar.setBarBackgroundColor(CommonUtil.getAttrColor(R.attr.colorNavigationBottomBarBackground,this));
//        navigationBar.setActiveColor(CommonUtil.getAttrColor(R.attr.colorNavigationBottomBarActiveColor,this));

        drawerLayout.setBackgroundColor(CommonUtil.getAttrColor(R.attr.colorWindowBackground,this));
        drawerAdapter.notifyItemRangeChanged(0,drawerAdapter.getItemCount());
        drawerHeaderLayout.setBackgroundColor(CommonUtil.getAttrColor(R.attr.colorPrimaryDark,this));
        StatusBarCompat.compat(this,CommonUtil.getAttrColor(R.attr.colorAccent,this));
        homeFragment.refreshUI();
    }
}
