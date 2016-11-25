package com.example.john.simulatesms.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by John on 2016/11/21.
 */

public class SimulateSMSApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
