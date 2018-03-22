package net.syxsoft.ldyhapplication.callback;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;

import net.syxsoft.ldyhapplication.utils.OkHttp3Utils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 1. 类的用途 如果要将得到的json直接转化为集合 建议使用该类
 * onSuccess() onFailed()方法运行在主线程
 * 2. Created by 谷永庆
 * 3. @date 2017/9/24 18:47
 */

public abstract class GsonObjectCallback<T> implements Callback {
    private Handler handler = OkHttp3Utils.getInstance().getHandler();
    private Context mContext;
    private ProgressDialog mProgressDialog;

    private void initDialog(){
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("加载中...");
        mProgressDialog.setCanceledOnTouchOutside(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMax(100);
    }

    private void hideDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public GsonObjectCallback(Context context) {
        mContext = context;
        initDialog();
    }

    //主线程处理
    public abstract void onSuccess(T t);

    //主线程处理
    public abstract void onFailed(Call call, IOException e);


    public void OnRequestBefore() {
        mProgressDialog.show();
    }

    //请求失败
    @Override
    public void onFailure(final Call call, final IOException e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onFailed(call, e);
            }
        });
        hideDialog();
    }

    //请求json 并直接返回泛型的对象 主线程处理
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String json = response.body().string();
        Class<T> cls = null;

        Class clz = this.getClass();
        ParameterizedType type = (ParameterizedType) clz.getGenericSuperclass();
        Type[] types = type.getActualTypeArguments();
        cls = (Class<T>) types[0];
        Gson gson = new Gson();
        final T t = gson.fromJson(json, cls);
        handler.post(new Runnable() {
            @Override
            public void run() {
                onSuccess(t);
            }
        });

        hideDialog();
    }
}