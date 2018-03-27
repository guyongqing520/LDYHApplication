package net.syxsoft.ldyhapplication.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.util.List;
import java.util.Locale;

import static com.baidu.mapapi.BMapManager.getContext;

/**
 * Created by 谷永庆 on 2018/3/18.
 */

public class GeoLocationUtils {


    public static Address addressTolatlng(String address, Context context) {

        Address maddress = null;
        if (address != null) {
            Geocoder gc = new Geocoder(context, Locale.CHINA);
            List<Address> addressList = null;
            try {
                addressList = gc.getFromLocationName(address, 1);
                if (!addressList.isEmpty()) {
                    maddress = addressList.get(0);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return maddress;
    }


    public static double GetShortDistance(double lon1, double lat1, double lon2, double lat2) {
        double DEF_2PI = 6.28318530712; // 2*PI
        double DEF_PI180 = 0.01745329252; // PI/180.0
        double DEF_R = 6370693.5; // radius of earth
        double DEF_PI = 3.14159265359;

        double ew1, ns1, ew2, ns2;
        double dx, dy, dew;
        double distance;
        // 角度转换为弧度
        ew1 = lon1 * DEF_PI180;
        ns1 = lat1 * DEF_PI180;
        ew2 = lon2 * DEF_PI180;
        ns2 = lat2 * DEF_PI180;
        // 经度差
        dew = ew1 - ew2;
        // 若跨东经和西经180 度，进行调整
        if (dew > DEF_PI)
            dew = DEF_2PI - dew;
        else if (dew < -DEF_PI)
            dew = DEF_2PI + dew;
        dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
        dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
        // 勾股定理求斜边长
        distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }
}
