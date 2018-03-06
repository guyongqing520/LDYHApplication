package net.syxsoft.ldyhapplication.interfaces;

import net.syxsoft.ldyhapplication.callback.BeanCallback;

import okhttp3.RequestBody;

/**
 * Created by 谷永庆 on 2018/2/14.
 */

public interface  IRequestBase {
     void  doRequest(RequestBody body, String url, String method, final BeanCallback callback);
}
