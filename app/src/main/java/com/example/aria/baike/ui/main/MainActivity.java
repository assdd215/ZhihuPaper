package com.example.aria.baike.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.aria.baike.R;
import com.example.aria.baike.TestActivity;
import com.example.aria.baike.common.BaseActivity;
import com.example.aria.baike.global.Constants;
import com.example.aria.baike.model.Article;
import com.example.aria.baike.ui.classify.ClassifyFragment;
import com.example.aria.baike.ui.home.HomeFragment;
import com.example.aria.baike.ui.login.LoginActivity;
import com.example.aria.baike.ui.map.LocationActivity;
import com.example.aria.baike.ui.person.PersonFragment;
import com.example.aria.baike.ui.search.SearchActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener{

    private int LastNavigationSelectedPosition = 0;
    private List<Article> articleList;

    public final static String HOMEFRAGMENT_ID = "FRAGMENT_TAG1";
    public final static String PERSONFRAGMENT_ID = "FRAGMENT_TAG2";
    public Fragment current_id;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation_bottom_bar)
    BottomNavigationBar navigationBar;
    @BindView(R.id.Container)
    FrameLayout Container;

    private HomeFragment homeFragment;
    private PersonFragment personFragment;
    private ClassifyFragment classifyFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {

        if (getFragmentManager().findFragmentByTag(HOMEFRAGMENT_ID)==null){
            homeFragment = new HomeFragment();
            current_id = homeFragment;
        }
        if (getFragmentManager().findFragmentByTag(PERSONFRAGMENT_ID)==null){
            personFragment = PersonFragment.newInstance(this);
        }

//        homeFragment = new HomeFragment();


        classifyFragment = ClassifyFragment.newInstance();
    }

    @Override
    public void initView() {
        initToolBar();
        initNavigationBottomBar();
        setDefaultFragment();
    }

    private void initToolBar(){

        toolbar.inflateMenu(R.menu.home_menu);

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
                    case R.id.action_home:
                        Toast.makeText(getActivity(),"home",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, TestActivity.class));
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onTabSelected(int position) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (position){
            case 0:
                toolbar.getMenu().getItem(0).setVisible(true);
                toolbar.getMenu().getItem(1).setVisible(false);
                toolbar.getMenu().getItem(2).setVisible(false);
                ((TextView)toolbar.findViewById(R.id.toolbar_title)).setText("主页");
                if (current_id != homeFragment){
                    Log.d("MainActivity","homeFragment");
                    if (homeFragment == null){
                        homeFragment = new HomeFragment();
                    }
                    transaction.hide(current_id).show(homeFragment);
                }
                current_id = homeFragment;
//                transaction.replace(R.id.Container,homeFragment);

                break;
            case 1:
                toolbar.getMenu().getItem(0).setVisible(false);
                toolbar.getMenu().getItem(1).setVisible(true);
                toolbar.getMenu().getItem(2).setVisible(false);
                if (current_id != classifyFragment){
                    transaction.hide(current_id).show(classifyFragment);
                }
                ((TextView)toolbar.findViewById(R.id.toolbar_title)).setText("分类");
                current_id = classifyFragment;
                break;
            case 2:
                startActivityForResult(new Intent(MainActivity.this, LocationActivity.class),0);
                break;
            case 3:
                toolbar.getMenu().getItem(0).setVisible(false);
                toolbar.getMenu().getItem(1).setVisible(false);
                toolbar.getMenu().getItem(2).setVisible(true);
                ((TextView)toolbar.findViewById(R.id.toolbar_title)).setText("个人中心");
//                transaction.replace(R.id.Container,personFragment);
                if (current_id != personFragment){
                    Log.d("MainActivity","personFragment");
                    if (personFragment == null){
                        personFragment = PersonFragment.newInstance(this);
                    }
                    transaction.hide(current_id).show(personFragment);
                }
                current_id = personFragment;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case Constants.LOCATIONATY_RESPONSE:
                navigationBar.selectTab(0);
                current_id = homeFragment;
                break;
            case Constants.LOGINATY_RESPONSE:
                if (Constants.getInstance().isLogin == false){
                    return;
                }
                Log.d("MainActivity","onActivityResult"+Constants.LOGINATY_RESPONSE);
                personFragment.setUseranme(data.getStringExtra("username"));
                personFragment.setAccount(data.getStringExtra("account"));
                personFragment.setAvactor(data.getStringExtra("account"));
        }
    }
}