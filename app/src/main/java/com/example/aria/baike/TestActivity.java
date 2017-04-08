package com.example.aria.baike;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aria.baike.common.BaseActivity;
import com.example.aria.baike.global.Networks;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Aria on 2017/3/12.
 */

public class TestActivity extends BaseActivity{

    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        imageView = (ImageView) findViewById(R.id.test_image);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Networks.getInstance().test().enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Toast.makeText(TestActivity.this, "失败！", Toast.LENGTH_SHORT).show();
                Log.d("MainActivity2","失败！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
//                Toast.makeText(TestActivity.this, "返回！"+str, Toast.LENGTH_SHORT).show();
                Log.d("MainActivity2","返回！"+str);
                InputStream inputStream = new FileInputStream(str);
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }
}
