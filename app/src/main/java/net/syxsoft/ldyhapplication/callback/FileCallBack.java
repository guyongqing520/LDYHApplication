package net.syxsoft.ldyhapplication.callback;

import android.app.ProgressDialog;
import android.content.Context;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 谷永庆 on 2018/3/22.
 */

public abstract class FileCallBack<T> extends BaseCallBack<T> {

    private Context mContext;

    private ProgressDialog mProgressDialog;

    public FileCallBack(Context context) {
        mContext = context;
        initDialog();
    }

    private void initDialog(){
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("下载中...");
        mProgressDialog.setCanceledOnTouchOutside(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMax(100);
    }

    private void hideDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void OnRequestBefore(Request request) {
        mProgressDialog.show();
    }

    @Override
    public void onFailure(Call call, IOException e) {
        hideDialog();
    }

    @Override
    public void onSuccess(Call call, Response response, T t) {
        //Log.e("lgz", "onSuccess: >>>>>>>>>>>>>");
        hideDialog();
    }

    @Override
    public void onEror(Call call, int statusCode, Exception e) {
        hideDialog();
    }

    @Override
    public void inProgress(int progress, long total, int id) {
        //Log.e("lgz", "inProgress: >>>>>>>>>>>>>"+progress);
        mProgressDialog.setProgress(progress);

    }
}