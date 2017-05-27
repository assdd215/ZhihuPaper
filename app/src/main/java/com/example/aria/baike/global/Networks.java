package com.example.aria.baike.global;

import android.util.Log;

import com.example.aria.baike.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Aria on 2017/2/16.
 */

public class Networks {


    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    public static final String basepath = "http://120.24.211.43:8080/baike";

    private static final int READ_TIMEOUT = 1;
    private static final int WRITE_TIMEOUT = 1;

    public int lastIndex = 0;

    private OkHttpClient okHttpClient;

    private static Networks networks;

    private Networks() {
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)
                .build();
    }

    public static Networks getInstance() {
        if (networks == null) {
            networks = new Networks();
        }
        return networks;
    }


    public Call RegisterAccount(User user) {
        JSONObject object = new JSONObject();
        Request request = null;
        try {
            object.put("account",user.getAccount());
            object.put("password",user.getPassword());
            Log.d("MainActivity",object.toString());
            RequestBody body = RequestBody.create(JSON,object.toString());
            request = new Request.Builder()
                            .url(basepath+"/user/createAccount")
                            .post(body)
                            .build();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return okHttpClient.newCall(request);
    }

    public Call login(String account,String password){
        JSONObject object = new JSONObject();
        Request request = null;
        try {
            object.put("account",account);
            object.put("password",password);
            RequestBody body = RequestBody.create(JSON,object.toString());
            request = new Request.Builder()
                    .url(basepath+"/user/login")
                    .post(body)
                    .build();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return okHttpClient.newCall(request);
    }

    public Call getAvactor(String account){
        JSONObject object = new JSONObject();
        Request request = null;
        try{
            object.put("account",account);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON,object.toString());
        request = new Request.Builder()
                .url(basepath+"/user/getAvactor")
                .post(body)
                .build();
        return okHttpClient.newCall(request);
    }

    public Call test(){
        Request request = new Request.Builder().url(basepath + "/user/testImage").build();
        return okHttpClient.newCall(request);
    }


    public Call doPost(String url,JSONObject object){
        Log.d("MainActivity","object:"+object.toString());
        RequestBody body = RequestBody.create(JSON,object.toString());
        Request request = new Request.Builder()
                                    .url(url)
                                    .post(body)
                                    .build();
        return okHttpClient.newCall(request);
    }

    public Call doPost(String url){
        Request request = new Request.Builder().url(url).build();
        return okHttpClient.newCall(request);
    }



}
