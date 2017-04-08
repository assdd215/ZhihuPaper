package com.example.aria.baike.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aria.baike.R;
import com.example.aria.baike.common.BaseActivity;
import com.example.aria.baike.global.Constants;
import com.example.aria.baike.global.Networks;
import com.example.aria.baike.model.User;
import com.example.aria.baike.ui.register.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Aria on 2017/2/14.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{


    @BindView(R.id.password_layout)
    TextInputLayout passwordLayout;
    @BindView(R.id.account_layout)
    TextInputLayout accountLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.register_textView)
    TextView register;
    @BindView(R.id.login_btn)
    Button login_btn;

    ProgressDialog progressDialog;
    private Bitmap avactor;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        passwordLayout.setHint("输入密码");
        accountLayout.setHint("输入账号");
        toolbar.setTitle("登录");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_36dp);

    }

    @Override
    public void initListener() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        login_btn.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                progressDialog = ProgressDialog.show(getActivity(),"登录","正在登陆中");
                final String account = accountLayout.getEditText().getText().toString();
                String password = passwordLayout.getEditText().getText().toString();
                Networks.getInstance().login(account,password).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String str = response.body().string();
                        final String message;
                        JSONObject object;
                        Log.d("MainActivity","str:"+str);
                        try {
                            object = new JSONObject(str);
                            Integer id = object.getInt("id");
                            switch (id){
                                case -1:
                                    message = "账号不存在！！";
                                    Log.d("MainActivity","账号不存在！");
                                    break;
                                case 0:
                                    message = "密码错误！！";
                                    Log.d("MainActivity","密码错误！");
                                    break;
                                default:
                                    Constants.getInstance().isLogin = true;
                                    user = new User();
                                    user.setAccount(object.getString("account"));
                                    user.setId(object.getInt("id"));
                                    user.setPassword(object.getString("password"));
                                    user.setUsername(object.getString("username"));
                                    user.setAvactor(object.getString("avactor"));
                                    message = "登陆成功！！";
                                    Log.d("MainActivity","登陆成功！");
                                    getAvactor(user.getAccount());
                                    break;
                                }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.cancel();
                                    Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent();
                                    intent.putExtra("account",user.getAccount());
                                    intent.putExtra("password",user.getPassword());
                                    intent.putExtra("username",user.getUsername());
                                    intent.putExtra("id",user.getId());
                                    intent.putExtra("avactor",user.getAvactor());
                                    getActivity().setResult(Constants.LOGINATY_RESPONSE,intent);
                                    getActivity().finish();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
        }
    }

    public void getAvactor(final String account){

        File isFile = new File(Constants.PATH+"/"+account+"/avactor.png");
        if (isFile.exists()){
            return;
        }

        Networks.getInstance().getAvactor(account).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream = null;
                File file = new File(Constants.PATH+"/"+account);
                if (!file.exists()){
                    file.mkdirs();
                }

                try {
                    avactor = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                    fileOutputStream = new FileOutputStream(Constants.PATH+"/"+account+"/avactor.png");
                    avactor.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                    fileOutputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    public LoginActivity getActivity(){
        return this;
    }
}
