package com.example.aria.baike.ui.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.aria.baike.R;
import com.example.aria.baike.common.BaseActivity;
import com.example.aria.baike.global.Constants;

/**
 * Created by Aria on 2017/2/22.
 */

public class LocationActivity extends BaseActivity{

    LocationClient locationClient;
    public MyLocationListener myLocationListener = new MyLocationListener();
    MapView mapView;
    BaiduMap baiduMap;

    Button BtnLocate;
    boolean isFirstLoc = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        mapView = (MapView) findViewById(R.id.mapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        locationClient = new LocationClient(this);
        locationClient.registerLocationListener(myLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        locationClient.setLocOption(option);
        locationClient.start();

    }

    @Override
    public void initData() {
        BtnLocate = (Button) findViewById(R.id.BtnLocate);


    }

    @Override
    public void initListener() {
        BtnLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,true,null));
                baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,true,null));
            }
        });
    }

    public class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null || mapView == null){
                return;
            }
            MyLocationData data = new MyLocationData.Builder()
                                    .accuracy(bdLocation.getRadius())
                                    .direction(100)
                                    .latitude(bdLocation.getLatitude())
                                    .longitude(bdLocation.getLongitude())
                                    .build();
            baiduMap.setMyLocationData(data);
            if (isFirstLoc){
                isFirstLoc = false;
                LatLng latLng = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(latLng).zoom(18.0f);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        locationClient.stop();
        baiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        mapView = null;
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        Log.d("MainActivity","onBackPressed");
        setResult(Constants.LOCATIONATY_RESPONSE);
        finish();
//        super.onBackPressed();
    }
}
