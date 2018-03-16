package net.syxsoft.ldyhapplication.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 类的用途 联网判断
 * Created by 谷永庆 on 2018/3/16.
 */


public class NetWorkUtils {
    //判断网络是否连接
    public static boolean isNetWorkAvailable(Context context) {
        //网络连接管理器
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //网络信息
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null;

    }

}