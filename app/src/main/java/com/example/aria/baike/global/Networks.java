package com.example.aria.baike.global;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.aria.baike.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Aria on 2017/2/16.
 */

public class Networks {


    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    private static final String basepath = "http://110.64.89.47:8080";

    private OkHttpClient okHttpClient;

    private static Networks networks;

    private Networks() {
        okHttpClient = new OkHttpClient();
    }

    public static Networks getInstance() {
        if (networks == null) {
            networks = new Networks();
        }
        return networks;
    }

    public OkHttpClient getClient() {
        return okHttpClient;
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

    public Call getAllArticles(){
        Request request = new Request.Builder().url(basepath+"/article/getAll")
                .method("GET",null)
                .build();
        return okHttpClient.newCall(request);
    }

    public void useHttpConnection(String url_str) {
        try {
            URL url = new URL(url_str);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestProperty("Content-Type","application/json");

            connection.connect();

            JSONObject object = new JSONObject();
            object.put("isweb",0);

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            Log.d("MainActivity",object.toString());
            writer.write(object.toString());
            writer.flush();
            writer.close();

            connection.getInputStream();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
