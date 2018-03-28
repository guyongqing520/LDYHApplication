package net.syxsoft.ldyhapplication.callback;

import android.app.ProgressDialog;
import android.content.Context;

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
    private ProgressDialog progressDialog;

    public LoadCallBack(Context context) {
        this.context = context;
        initDialog();
    }

    private void initDialog(){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("加载中...");
    }

    private void showDialog() {
        progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void setMsg(String str) {
        progressDialog.setMessage(str);
    }

    public void setMsg(int resId) {
        progressDialog.setMessage(context.getString(resId));
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