package com.example.aria.baike.ui.register;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aria.baike.R;
import com.example.aria.baike.common.BaseActivity;
import com.example.aria.baike.global.Networks;
import com.example.aria.baike.model.User;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Aria on 2017/2/15.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.account_layout)
    TextInputLayout accountLayout;
    @BindView(R.id.password_layout)
    TextInputLayout passwordLayout;
    @BindView(R.id.mobile_layout)
    TextInputLayout mobileLayout;

    private ProgressDialog progressDialog;
    private boolean isRegister = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        accountLayout.setHint("输入账号");
        passwordLayout.setHint("输入密码");
        mobileLayout.setHint("输入手机号");
    }

    @Override
    public void initView() {
        toolbar.setTitle("注册");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_36dp);
    }

    @Override
    public void initListener() {
        findViewById(R.id.register_btn).setOnClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onDestroy() {
        if (progressDialog !=null){
            progressDialog.dismiss();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_btn:
                progressDialog = ProgressDialog.show(v.getContext(),"提示","注册中");
                User user = new User();
                user.setAccount(accountLayout.getEditText().getText().toString());
                user.setPassword(passwordLayout.getEditText().getText().toString());
                Log.d("MainActivity",user.getAccount());
                Log.d("MainActivity",user.getPassword());
                Networks.getInstance().RegisterAccount(user).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String codestr = response.body().string();
                        Log.d("MainActivity",codestr);
                        int code = Integer.parseInt(codestr);
                        final String str;
                        switch (code){
                            case 1:
                                str = "注册成功！";
                                isRegister = true;
                                break;
                            case 0:
                                str = "已有相同账号！";
                                break;
                            default:
                                str = "注册失败！";
                                break;
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.cancel();
                                Toast.makeText(getActivity(),"response:"+str,Toast.LENGTH_SHORT).show();
                                if (isRegister){
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            finish();
                                        }
                                    },1000);
                                }
                            }
                        });

                    }
                });
        }
    }
}
