package net.syxsoft.ldyhapplication.model;

import net.syxsoft.ldyhapplication.bean.BeanBase;
import net.syxsoft.ldyhapplication.callback.BeanCallback;
import net.syxsoft.ldyhapplication.interfaces.IRequestBase;
import net.syxsoft.ldyhapplication.utils.GsonUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 谷永庆 on 2018/2/4.
 */

public class RequestBase implements IRequestBase {

    @Override
    public void doRequest(RequestBody body, String url, String method, final BeanCallback callback) {
        if (!method.equals("POST") && !method.equals("GET")) {
            callback.onError("请求方式必须为GET和POST");
            return;
        }

        if (url == null && url.equals("")) {
            callback.onError("请求URL不能为空");
            return;
        }

        OkHttpClient client = new OkHttpClient();

        Request request = null;

        if (method == "GET") {
            request = new Request.Builder()
                    .get()
                    .url(url)
                    .build();
        }

        if (method == "POST") {

            if (body == null) {
                callback.onError("请求body不能为空");
                return;
            }

            request = new Request.Builder()
                    .post(body)
                    .url(url)
                    .build();
        }


        Call call = client.newCall(request);
        //异步请求，子线程
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                callback.onSuccess(json);
            }
        });
    }
}
