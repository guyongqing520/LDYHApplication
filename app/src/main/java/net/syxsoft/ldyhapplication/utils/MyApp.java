package net.syxsoft.ldyhapplication.utils;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Activity> activities=new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add( activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public  static void finishAll(){
        for (Activity activity :activities){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }

}