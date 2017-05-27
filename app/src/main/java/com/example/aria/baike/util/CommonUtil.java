package com.example.aria.baike.util;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;

/**
 * Created by Aria on 2017/5/3.
 */

public class CommonUtil {

    public static int getAttrColor(int resourceId, Context context){
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(resourceId,typedValue,true);
        return ContextCompat.getColor(context,typedValue.resourceId);

    }
}
