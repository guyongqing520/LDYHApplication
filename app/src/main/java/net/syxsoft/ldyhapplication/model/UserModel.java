package net.syxsoft.ldyhapplication.model;

import android.content.Context;
import android.content.SharedPreferences;

import net.syxsoft.ldyhapplication.bean.UserAccountBean;

/**
 * Created by 谷永庆 on 2018/3/17.
 */

public class UserModel {

    public void addUserAccountInfo(String username, String password,String userid, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE).edit();

        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("userid", userid);
        editor.apply();
    }

    public UserAccountBean getUserAccountInfo(Context context){

        SharedPreferences preferences=context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);

        UserAccountBean userAccountBean =new UserAccountBean();

        String username= preferences.getString("username","");
        String password= preferences.getString("password","");
        String userid= preferences.getString("userid","");

        userAccountBean.setUsername(username);
        userAccountBean.setPassword(password);
        userAccountBean.setUserid(userid);

        return userAccountBean;
    }

}
