package net.syxsoft.ldyhapplication.ui;


import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.utils.DistanceUtil;

import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.AttendenceBean;
import net.syxsoft.ldyhapplication.bean.LoginBean;
import net.syxsoft.ldyhapplication.bean.MainPanel;
import net.syxsoft.ldyhapplication.bean.Personinfo;
import net.syxsoft.ldyhapplication.bean.UserAccountBean;
import net.syxsoft.ldyhapplication.callback.GsonObjectCallback;
import net.syxsoft.ldyhapplication.model.UserModel;
import net.syxsoft.ldyhapplication.utils.DateUtils;
import net.syxsoft.ldyhapplication.utils.GeoLocationUtils;
import net.syxsoft.ldyhapplication.utils.OkHttp3Utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class KaoqiDakaFragment extends BaseFragment {

    protected LocationClient mlocaltionClient;
    protected boolean attrid=false;
    protected double currentlon;
    protected double currentlat;

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


    @OnClick(R.id.daka_btn)
    public void OnDakaBtnClicked() {

        if (!attrid) {
            Toast.makeText(getContext(), "当前时间无法打卡", Toast.LENGTH_SHORT).show();
        }

        //double s=currentlon;

       // else if (GeoLocationUtils.GetShortDistance())

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        //启用底部导航
        BottomNavigationView navigation = getHoldingActivity().findViewById(R.id.navigation);
        navigation.setVisibility(View.VISIBLE);


        //加载个人信息
        UserModel userModel = new UserModel();
        UserAccountBean userAccountBean = userModel.getUserAccountInfo(getContext());

        if (userAccountBean == null || userAccountBean.getUserid() == null || userAccountBean.getUserid().length() == 0) {
            //导航到login
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getHoldingActivity().finish();

            return view;
        }

        if (!getHoldingActivity().isNetWorkAvailable()) {
            Toast.makeText(getHoldingActivity(), "没有网络连接，请稍后重试", Toast.LENGTH_SHORT).show();
            return view;
        }

        final ProgressDialog progressDialog = new ProgressDialog(getContext());

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("加载中...");
        progressDialog.show();

        //更新时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        String datestr = sdf.format(date);
        today.setText(datestr + "(" + DateUtils.dateToWeek(datestr) + ")");

        //获取用户并更新用户信息
        try {

            OkHttp3Utils.getInstance().doGet(getRootApiUrl() + "/api/user/getpersoninfo/" + userAccountBean.getUserid(),
                    new GsonObjectCallback<Personinfo>() {

                        @Override
                        public void onSuccess(Personinfo personinfo) {
                            progressDialog.dismiss();

                            if (personinfo.getRequestCode() != 200) {
                                progressDialog.dismiss();
                                Toast.makeText(getHoldingActivity(), "网络连接失败，请稍后重试", Toast.LENGTH_SHORT).show();
                            }

                            //更新用户信息
                            username.setText(personinfo.getSuccessInfo().getPersonInfo().getName());
                            position.setText(personinfo.getSuccessInfo().getPersonInfo().getRemark());
                        }

                        @Override
                        public void onFailed(Call call, IOException e) {
                            progressDialog.dismiss();
                            Toast.makeText(getHoldingActivity(), "网络连接失败，请稍后重试", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception ex) {
            progressDialog.dismiss();
            Toast.makeText(getHoldingActivity(), "网络连接失败，请稍后重试", Toast.LENGTH_SHORT).show();
        }

        //获取打卡信息
        try {

            OkHttp3Utils.getInstance().doGet(getRootApiUrl() + "/api/attendence/init/" + userAccountBean.getUserid(),
                    new GsonObjectCallback<AttendenceBean>() {

                        @Override
                        public void onSuccess(AttendenceBean attendenceBean) {
                            progressDialog.dismiss();

                            if (attendenceBean.getRequestCode() != 200) {
                                progressDialog.dismiss();
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
                                attrid=successInfoBean.isIsAtt();

                            } else {
                                dakatext.setText(String.valueOf(attendenceBean.getErrorMessage()));
                                if (String.valueOf(attendenceBean.getErrorMessage()).length() > 6) {
                                    dakatext.setTextSize(14);
                                }
                                dakadate.setText("无打卡时间段");
                                attrid=false;
                            }
                        }

                        @Override
                        public void onFailed(Call call, IOException e) {
                            progressDialog.dismiss();
                            Toast.makeText(getHoldingActivity(), "网络连接失败，请稍后重试", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception ex) {
            progressDialog.dismiss();
            Toast.makeText(getHoldingActivity(), "网络连接失败，请稍后重试", Toast.LENGTH_SHORT).show();
        }

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

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            address.setText("位置：" + location.getAddrStr());
            currentlat=location.getLatitude();
            currentlon=location.getLongitude();
        }
    }

}
