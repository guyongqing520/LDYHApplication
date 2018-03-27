package net.syxsoft.ldyhapplication.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by 谷永庆 on 2018/3/25.
 */

public class MyAlert {
    public MyAlert(String title, String message, boolean positiveButton, boolean negativeButton, Context context) {
        if (positiveButton && negativeButton) {

            new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setMessage(message).create().show();

        } else {
            new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setMessage(message).create().show();
        }
    }
}
