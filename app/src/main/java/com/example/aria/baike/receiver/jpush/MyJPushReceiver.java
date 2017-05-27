package com.example.aria.baike.receiver.jpush;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.aria.baike.global.Constants;
import com.example.aria.baike.ui.detail.DetailActivity;
import com.example.aria.baike.ui.main.MainActivity;
import com.example.aria.baike.util.SystemUtils;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Aria on 2017/5/27.
 */

public class MyJPushReceiver extends BroadcastReceiver{

    private NotificationManager mNotificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == mNotificationManager){
            mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();
        Log.d("MainActivity","onReceive:"+intent.getAction());
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())){
            openNotification(context,bundle);
        }
    }

    private void openNotification(Context context,Bundle bundle){
        if (SystemUtils.isAppALive(context,"com.example.aria.baike")){
            Log.d("MainActivity","进程正在运行");

            try {
                JSONObject object = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                Intent mainIntent = new Intent(context, MainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Intent detailIntent = new Intent(context, DetailActivity.class);
                detailIntent.putExtra("url",object.getString("url"));
                detailIntent.putExtra("article_detail_title",object.getString("article_detail_title"));
                Intent[] intents = {mainIntent,detailIntent};
                context.startActivities(intents);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            Log.d("MainActivity","进程没有运行");

            try {
                JSONObject object = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.example.aria.baike");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                Bundle args = new Bundle();
                bundle.putBoolean(Constants.LAUNCH_BY_NOTIFICATION,true);
                bundle.putString("url",object.getString("url"));
                intent.putExtra(Constants.LAUNCH_BY_NOTIFICATION,bundle);
                context.startActivity(intent);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
