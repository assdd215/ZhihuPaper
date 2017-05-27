package com.example.aria.baike.common;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.baidu.mapapi.SDKInitializer;
import com.example.aria.baike.ui.map.LocationService;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Aria on 2017/2/22.
 */

public class MyApplication extends Application{

    public LocationService locationService;
    public Vibrator vibrator;

    @Override
    public void onCreate() {
        super.onCreate();

        locationService = new LocationService(getApplicationContext());
        vibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
