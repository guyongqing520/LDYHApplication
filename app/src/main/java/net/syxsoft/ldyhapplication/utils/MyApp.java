package net.syxsoft.ldyhapplication.utils;

import android.app.Application;

/**
 * Created by 谷永庆 on 2018/3/16.
 */

public class MyApp extends Application {
    public static MyApp mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

    }
    public static MyApp getInstance() {
        return mInstance;
    }
}