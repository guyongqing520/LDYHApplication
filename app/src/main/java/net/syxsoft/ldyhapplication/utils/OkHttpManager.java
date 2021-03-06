package net.syxsoft.ldyhapplication.utils;

import android.app.Dialog;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import com.dou361.dialogui.DialogUIUtils;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import net.syxsoft.ldyhapplication.callback.BaseCallBack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 谷永庆 on 2018/3/22.
 */

public class OkHttpManager {

    private static OkHttpManager mOkHttpManager;

    private OkHttpClient mOkHttpClient;

    private Gson mGson;

    private Handler handler;

    private OkHttpManager() {
        File sdcache = new File(Environment.getExternalStorageDirectory(), "cache");
        int cacheSize = 10 * 1024 * 1024;
        mOkHttpClient = new OkHttpClient.Builder().connectTimeout(1500, TimeUnit.SECONDS)
                //添加OkHttp3的拦截器
                .addNetworkInterceptor(new CacheInterceptor())
                .writeTimeout(2000, TimeUnit.SECONDS).readTimeout(2000, TimeUnit.SECONDS)
                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize))
                .build();
        mGson = new Gson();
        handler = new Handler(Looper.getMainLooper());
    }

    //创建 单例模式（OkHttp官方建议如此操作）
    public static OkHttpManager getInstance() {
        if (mOkHttpManager == null) {
            mOkHttpManager = new OkHttpManager();
        }
        return mOkHttpManager;
    }

    /***********************
     * 对外公布的可调方法
     ************************/

    public void getRequest(String url, final BaseCallBack callBack) {

        if (!NetWorkUtils.isNetWorkAvailable(MyApp.getInstance())) {

            final Dialog dialog = DialogUIUtils.showLoading(MyApp.getInstance(), "没有网络连接", true, true, true, false).show();
            dialog.getWindow().setDimAmount(0);
            dialog.show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.hide();
                }
            }, 1000);

        } else {

            Request request = buildRequest(url, null, HttpMethodType.GET);
            doRequest(request, callBack);
        }
    }

    public void postRequest(String url, final BaseCallBack callBack, Map<String, String> params) {
        if (!NetWorkUtils.isNetWorkAvailable(MyApp.getInstance())) {

            final Dialog dialog = DialogUIUtils.showLoading(MyApp.getInstance(), "没有网络连接", true, true, true, false).show();
            dialog.getWindow().setDimAmount(0);
            dialog.show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.hide();
                }
            }, 1000);


        } else {
            Request request = buildRequest(url, params, HttpMethodType.POST);
            doRequest(request, callBack);
        }
    }

    public void postUploadSingleImage(String url, final BaseCallBack callback, File file, String fileKey, Map<String, String> params) {
        Param[] paramsArr = fromMapToParams(params);

        try {
            postAsyn(url, callback, file, fileKey, paramsArr);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void postUploadMoreImages(String url, final BaseCallBack callback, File[] files, String[] fileKeys, Map<String, String> params) {
        Param[] paramsArr = fromMapToParams(params);

        try {
            postAsyn(url, callback, files, fileKeys, paramsArr);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /***********************
     * 对内方法
     ************************/
    //单个文件上传请求  不带参数
    private void postAsyn(String url, BaseCallBack callback, File file, String fileKey) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        doRequest(request, callback);
    }

    //单个文件上传请求 带参数
    private void postAsyn(String url, BaseCallBack callback, File file, String fileKey, Param... params) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        doRequest(request, callback);
    }

    //多个文件上传请求 带参数
    private void postAsyn(String url, BaseCallBack callback, File[] files, String[] fileKeys, Param... params) throws IOException {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        doRequest(request, callback);
    }

    //异步下载文件
    public void asynDownloadFile(final String url, final String destFileDir, final BaseCallBack callBack) {
        final Request request = buildRequest(url, null, HttpMethodType.GET);
        callBack.OnRequestBefore(request);  //提示加载框
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBackFailure(callBack, call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                InputStream is = null;
                byte[] buf = new byte[1024 * 2];
                final long fileLength = response.body().contentLength();
                int len = 0;
                long readLength = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    File file = new File(destFileDir, getFileName(url));
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        readLength += len;
                        int curProgress = (int) (((float) readLength / fileLength) * 100);
                        //Log.e("lgz", "onResponse: >>>>>>>>>>>>>" + curProgress + ", readLength = " + readLength + ", fileLength = " + fileLength);
                        callBack.inProgress(curProgress, fileLength, 0);
                    }
                    fos.flush();
                    //如果下载文件成功，第一个参数为文件的绝对路径
                    callBackSuccess(callBack, call, response, file.getAbsolutePath());
                } catch (IOException e) {
                    callBackError(callBack, call, response.code());
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });

    }

    //构造上传图片 Request
    private Request buildMultipartFormRequest(String url, File[] files, String[] fileKeys, Param[] params) {
        params = validateParam(params);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (Param param : params) {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\""),
                    RequestBody.create(MediaType.parse("image/png"), param.value));
        }
        if (files != null) {
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                //TODO 根据文件名设置contentType
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    //Activity页面所有的请求以Activity对象作为tag，可以在onDestory()里面统一取消,this
    public void cancelTag(Object tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    private String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    private Param[] fromMapToParams(Map<String, String> params) {
        if (params == null)
            return new Param[0];
        int size = params.size();
        Param[] res = new Param[size];
        Set<Map.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : entries) {
            res[i++] = new Param(entry.getKey(), entry.getValue());
        }
        return res;
    }

    //去进行网络 异步 请求
    private void doRequest(Request request, final BaseCallBack callBack) {
        callBack.OnRequestBefore(request);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBackFailure(callBack, call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack.onResponse(response);
                String result = response.body().string();
                if (response.isSuccessful()) {

                    if (callBack.mType == String.class) {

                        callBackSuccess(callBack, call, response, result);
                    } else {
                        try {
                            Object object = mGson.fromJson(result, callBack.mType);//自动转化为 泛型对象
                            callBackSuccess(callBack, call, response, object);
                        } catch (JsonParseException e) {
                            //json解析错误时调用
                            callBackError(callBack, call, response.code());
                        }

                    }
                } else {
                    callBackError(callBack, call, response.code());
                }

            }

        });


    }

    //创建 Request对象
    private Request buildRequest(String url, Map<String, String> params, HttpMethodType methodType) {

        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (methodType == HttpMethodType.GET) {
            builder.get();
        } else if (methodType == HttpMethodType.POST) {
            RequestBody requestBody = buildFormData(params);
            builder.post(requestBody);
        }
        return builder.build();
    }

    //构建请求所需的参数表单
    private RequestBody buildFormData(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("platform", "android");
        builder.add("version", "1.0");
        builder.add("key", "123456");
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    private void callBackSuccess(final BaseCallBack callBack, final Call call, final Response response, final Object object) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onSuccess(call, response, object);
            }
        });
    }

    private void callBackFailure(final BaseCallBack callBack, final Call call, final IOException e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onFailure(call, e);
            }
        });
    }

    private void callBackError(final BaseCallBack callBack, final Call call, final int code) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onEror(call, code, null);
            }
        });

    }

    private Param[] validateParam(Param[] params) {
        if (params == null)
            return new Param[0];
        else
            return params;
    }

    public static class Param {
        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

        String key;
        String value;
    }

    enum HttpMethodType {
        GET, POST
    }

    /**
     * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
     */
    private static class CacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            // 有网络时 设置缓存超时时间1分钟
            int maxAge = 60;
            // 无网络时，设置超时为1个小时
            int maxStale = 60 * 60;
            Request request = chain.request();
            if (NetWorkUtils.isNetWorkAvailable(MyApp.getInstance())) {
                //有网络时只从网络获取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
            } else {
                //无网络时只从缓存中读取
                //MyToast.getInstance().show("没有网络连接",MyApp.getInstance());
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
               /* Looper.prepare();
                Toast.makeText(MyApp.getInstance(), "走拦截器缓存", Toast.LENGTH_SHORT).show();
                Looper.loop();*/
            }
            Response response = chain.proceed(request);
            if (NetWorkUtils.isNetWorkAvailable(MyApp.getInstance())) {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }

    public static String getRootApiUrl() {
        return "http://ldyh.webapi.syxsoft.net:8801";
    }

}

