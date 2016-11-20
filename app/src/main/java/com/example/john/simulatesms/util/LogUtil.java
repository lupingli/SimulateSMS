package com.example.john.simulatesms.util;

import android.util.Log;

/**
 * Created by John on 2016/11/20.
 */

public class LogUtil {
    private static boolean isShow = true;

    public static void v(String tag, String msg) {
        if (isShow) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isShow) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isShow) {
            Log.i(tag, msg);
        }
    }
}
