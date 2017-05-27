package com.example.aria.baike.util;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by Aria on 2017/5/27.
 */

public class SystemUtils {

    public static boolean isAppALive(Context context,String packageName){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();

        for (int i=0;i<processInfos.size();i++){
            if (processInfos.get(i).processName.equals(packageName)){
                Log.d("MainActivity",String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
        Log.d("MainActivity",
                String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }
}
