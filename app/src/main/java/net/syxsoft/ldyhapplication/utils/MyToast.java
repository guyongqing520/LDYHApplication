package net.syxsoft.ldyhapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIListener;

/**
 * Created by 谷永庆 on 2018/3/25.
 */

public class MyToast {

    private static MyToast instance = new MyToast();

    private MyToast() {
    }

    public static MyToast getInstance() {
        return instance;
    }

    public void show(String message, Context context) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);

        View view = toast.getView();

        view.setPadding(50, 50, 50, 50);
        toast.setGravity(Gravity.CENTER, 0, 0);

        TextView v = view.findViewById(android.R.id.message);


        toast.setView(view);
        toast.show();
    }
}
