package net.syxsoft.ldyhapplication.callback;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.dou361.dialogui.DialogUIUtils;

import net.syxsoft.ldyhapplication.ui.AppActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 谷永庆 on 2018/3/22.
 */

public abstract class LoadCallBack<T> extends BaseCallBack<T> {
    private Context context;
    private Dialog dialog;
    private String msg;


    public LoadCallBack(Context context) {
        this.context = context;
        DialogUIUtils.init(context);
    }


    private void showDialog() {

        if (msg == null || msg.length() == 0) {
            msg = "加载中...";
        }

        dialog = DialogUIUtils.showLoading(context, msg, true, true, true, false).show();
    }

    private void hideDialog() {
        DialogUIUtils.dismiss(dialog);
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    @Override
    public void OnRequestBefore(Request request) {
        showDialog();
    }

    @Override
    public void onFailure(Call call, IOException e) {
        hideDialog();
    }

    @Override
    public void onResponse(Response response) {
        hideDialog();
    }

    @Override
    public void inProgress(int progress, long total, int id) {

    }
}