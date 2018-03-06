package net.syxsoft.ldyhapplication.callback;

import net.syxsoft.ldyhapplication.bean.BeanBase;

/**
 * Created by 谷永庆 on 2018/3/4.
 */

public interface BeanCallback {

    void onError(String msg);
    void onSuccess(String json);
}
