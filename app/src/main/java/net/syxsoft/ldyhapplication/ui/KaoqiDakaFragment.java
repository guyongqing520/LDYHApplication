package net.syxsoft.ldyhapplication.ui;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.AttendenceBean;
import net.syxsoft.ldyhapplication.bean.PersoninfoBean;
import net.syxsoft.ldyhapplication.bean.ResultBean;
import net.syxsoft.ldyhapplication.bean.UserAccountBean;
import net.syxsoft.ldyhapplication.callback.GsonObjectCallback;
import net.syxsoft.ldyhapplication.callback.LoadCallBack;
import net.syxsoft.ldyhapplication.model.UserModel;
import net.syxsoft.ldyhapplication.utils.DateUtils;
import net.syxsoft.ldyhapplication.utils.GeoLocationUtils;
import net.syxsoft.ldyhapplication.utils.MyAlert;
import net.syxsoft.ldyhapplication.utils.OkHttp3Utils;
import net.syxsoft.ldyhapplication.utils.OkHttpManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class KaoqiDakaFragment extends BaseFragment {

    protected LocationClient mlocaltionClient;
    protected boolean attrid = false;
    protected double currentlon;
    protected double currentlat;
    protected String addressCenter;

    @OnClick(R.id.sq_daka)
    public void onRegisterBtnClicked() {
        pushFragment(new KaoqiDakaWaiqinFragment());
    }


    @BindView(R.id.username)
    TextView username;

    @BindView(R.id.position)
    TextView position;

    @BindView(R.id.today)
    TextView today;

    @BindView(R.id.dakatext)
    TextView dakatext;

    @BindView(R.id.dakadate)
    TextView dakadate;

    @BindView(R.id.address)
    TextView address;


    //打卡
    @OnClick(R.id.daka_btn)
    public void OnDakaBtnClicked() {

        if (!attrid || dakadate.getText() == null || dakadate.getText().toString().length() <= 0) {

            new MyAlert("", "当前时间无法打卡", true, false, getContext());

        } else if (addressCenter == null || addressCenter.length() == 0 || address.getText() == null ||
                address.getText().toString().length() <= 0 || currentlat == 0 || currentlon == 0) {

            new MyAlert("", "定位失败无法打卡，请打开定位", true, false, getContext());

        } else {

            android.location.Address maddress = GeoLocationUtils.addressTolatlng(addressCenter, getContext());
            if (maddress == null) {
                new MyAlert("", "定位失败无法打卡，请打开定位", true, false, getContext());
            } else {

                double centerLat = maddress.getLatitude();
                double centerLng = maddress.getLongitude();

                double ds = GeoLocationUtils.GetShortDistance(centerLng, centerLat, currentlon, currentlat);

                if (ds > 1000) {
                    new MyAlert("", "打卡距离超过1千米，无法打卡", true, false, getContext());
                } else {

                    Map<String, String> params = new HashMap<>();
                    params.put("personId", getHoldingActivity().getUserAccount().getUserid());
                    params.put("timeAreaId", dakadate.getText().toString());
                    params.put("address", address.getText().toString());
                    params.put("imgUrl", "");
                    params.put("isout", String.valueOf(false));


                    OkHttpManager.getInstance().postRequest(getRootApiUrl() + "/api/attendence/sign",
                            new LoadCallBack<ResultBean>(getContext()) {

                                @Override
                                public void onSuccess(Call call, Response response, ResultBean resultBean) {

                                    if (resultBean.getRequestCode() != 200) {
                                        Toast.makeText(getHoldingActivity(), resultBean.getErrorMessage().toString(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        new MyAlert("", "打卡成功", true, false, getContext());
                                    }
                                }

                                public void onEror(Call call, int statusCode, Exception e) {
                                }
                            }, params);

                }
            }
        }
    }


    //上传图片
    @OnClick(R.id.upimg_btn)
    public void OnUpimgBtnClicked() {

        Intent intent = new Intent(getContext(), ImageGridActivity.class);
        startActivityForResult(intent, 2);

    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kaoqi_daka;
    }

    @Override
    protected void initActionBar(Toolbar actionBar) {
        super.initActionBar(actionBar);

        //设置标题
        TextView textView = actionBar.findViewById(R.id.toolbar_title);
        textView.setText("打卡");

        //启用返回导航
        actionBar.setNavigationIcon(R.mipmap.title_bar_back);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            getHoldingActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        //上传图片初始化
        initImageSet();

        //启用底部导航
        BottomNavigationView navigation = getHoldingActivity().findViewById(R.id.navigation);
        navigation.setVisibility(View.VISIBLE);


        //更新时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        String datestr = sdf.format(date);
        today.setText(datestr + "(" + DateUtils.dateToWeek(datestr) + ")");

        //获取用户并更新用户信息
        OkHttpManager.getInstance().getRequest(getRootApiUrl() + "/api/user/getpersoninfo/" + getHoldingActivity().getUserAccount().getUserid(),
                new LoadCallBack<PersoninfoBean>(getContext()) {

                    @Override
                    public void onSuccess(Call call, Response response, PersoninfoBean personinfo) {

                        if (personinfo.getRequestCode() != 200) {
                            Toast.makeText(getHoldingActivity(), "网络连接失败，请稍后重试", Toast.LENGTH_SHORT).show();
                        }

                        //更新用户信息
                        username.setText(personinfo.getSuccessInfo().getPersonInfo().getName());
                        position.setText(personinfo.getSuccessInfo().getPersonInfo().getPosition() == null ? "" :
                                personinfo.getSuccessInfo().getPersonInfo().getPosition().toString());
                    }

                    public void onEror(Call call, int statusCode, Exception e) {
                    }
                });


        //获取打卡信息
        OkHttpManager.getInstance().getRequest(getRootApiUrl() + "/api/attendence/init/" + getHoldingActivity().getUserAccount().getUserid(),
                new LoadCallBack<AttendenceBean>(getContext()) {

                    @Override
                    public void onSuccess(Call call, Response response, AttendenceBean attendenceBean) {

                        if (attendenceBean.getRequestCode() != 200) {
                            Toast.makeText(getHoldingActivity(), "网络连接失败，请稍后重试", Toast.LENGTH_SHORT).show();
                        }

                        //更新打卡信息
                        AttendenceBean.SuccessInfoBean successInfoBean = attendenceBean.getSuccessInfo();

                        if (successInfoBean != null) {
                            dakatext.setText(successInfoBean.getResultMsg());
                            if (successInfoBean.getResultMsg().length() > 6) {
                                dakatext.setTextSize(14);
                            }

                            if (successInfoBean.getTimeAreaId().length() == 0) {
                                dakadate.setText("无打卡时间段");
                            } else {
                                dakadate.setText(successInfoBean.getTimeAreaId());
                            }
                            attrid = successInfoBean.isIsAtt();
                            addressCenter = successInfoBean.getAddressCenter();

                        } else {
                            dakatext.setText(String.valueOf(attendenceBean.getErrorMessage()));
                            if (String.valueOf(attendenceBean.getErrorMessage()).length() > 6) {
                                dakatext.setTextSize(14);
                            }
                            dakadate.setText("无打卡时间段");
                            attrid = false;
                        }
                    }

                    @Override
                    public void onEror(Call call, int statusCode, Exception e) {
                    }
                });


        //定位信息
        try {
            mlocaltionClient = new LocationClient(getContext());
            initLocation();
            mlocaltionClient.registerLocationListener(new MyLocationListener());

            List<String> permssionlist = new ArrayList<>();

            if (ContextCompat.checkSelfPermission(getHoldingActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                permssionlist.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (ContextCompat.checkSelfPermission(getHoldingActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                permssionlist.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (ContextCompat.checkSelfPermission(getHoldingActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                permssionlist.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            if (!permssionlist.isEmpty()) {
                String[] permissions = permssionlist.toArray(new String[permssionlist.size()]);
                ActivityCompat.requestPermissions(getHoldingActivity(), permissions, 1);
            } else {
                requestLocation();
            }
        } catch (Exception ex) {
            Toast.makeText(getHoldingActivity(), "定位失败，请打开网络或GPS重试", Toast.LENGTH_SHORT).show();
        }

        return view;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getContext(), "必须同意所有权限后才能使用打卡功能", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(getContext(), "发生未知错误", Toast.LENGTH_SHORT).show();
                }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 2) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ImageItem a = images.get(0);
                String s = a.path;
                // MyAdapter adapter = new MyAdapter(images);
                // gridView.setAdapter(adapter);
            } else {
                Toast.makeText(getContext(), "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void requestLocation() {
        mlocaltionClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        //就是这个方法设置为true，才能获取当前的位置信息
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("gcj02");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 5000;
        //option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        mlocaltionClient.setLocOption(option);
    }

    private void initImageSet() {
        //上传图片初始化
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            address.setText("位置：" + location.getAddrStr());
            currentlat = location.getLatitude();
            currentlon = location.getLongitude();
        }
    }

    private class PicassoImageLoader implements ImageLoader {

        @Override
        public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
            Picasso.with(activity)//
                    .load(Uri.fromFile(new File(path)))//
                    .placeholder(R.mipmap.default_image)//
                    .error(R.mipmap.default_image)//
                    .resize(width, height)//
                    .centerInside()//
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//
                    .into(imageView);
        }

        @Override
        public void clearMemoryCache() {
            //这里是清除缓存的方法,根据需要自己实现
        }
    }

}
