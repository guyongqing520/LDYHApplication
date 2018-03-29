package net.syxsoft.ldyhapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIListener;

/**
 * Created by 谷永庆 on 2018/3/25.
 */

public class MyAlert {
    public MyAlert(String title, String message, boolean positiveButton, boolean negativeButton, Context context) {
        if (positiveButton && negativeButton) {

            DialogUIUtils.showAlert((Activity) context,title,message,"","","确认","取消",false,true,true,new DialogUIListener() {
                @Override
                public void onPositive() {
                }

                @Override
                public void onNegative() {
                }
            }).show();

        } else {
            DialogUIUtils.showAlert((Activity) context,title,message,"","","确认","",false,true,true,new DialogUIListener() {
                @Override
                public void onPositive() {
                }

                @Override
                public void onNegative() {
                }
            }).show();
        }
    }
}
