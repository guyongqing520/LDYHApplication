package net.syxsoft.ldyhapplication.ui;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;

import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.AttendenceBean;
import net.syxsoft.ldyhapplication.callback.GsonObjectCallback;
import net.syxsoft.ldyhapplication.utils.OkHttp3Utils;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static net.syxsoft.ldyhapplication.utils.DateUtils.getSimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class KaoqiDakaWaiqinFragment extends BaseFragment {


    @BindView(R.id.starttime_select)
    TextView starttime_select;

    @BindView(R.id.endtime_select)
    TextView endtime_select;

    @BindView(R.id.remark)
    EditText remark;

    @OnClick(R.id.starttime_select)
    public void onbkbs1Text1BtnClicked() {
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                starttime_select.setText(getSimpleDateFormat(date, null));
            }
        })
                .setSubmitText("确定")
                .setCancelText("取消")
                .setTitleText("请选择时间")
                .setType(new boolean[]{true, true, true, false, false, false})
                .setDate(Calendar.getInstance())
                .build();

        pvTime.show();
    }

    @OnClick(R.id.endtime_select)
    public void onbkbsText1BtnClicked() {
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                endtime_select.setText(getSimpleDateFormat(date, null));
            }
        })
                .setSubmitText("确定")
                .setCancelText("取消")
                .setTitleText("请选择时间")
                .setType(new boolean[]{true, true, true, false, false, false})
                .setDate(Calendar.getInstance())
                .build();

        pvTime.show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kaoqigl_daka_waiqin;
    }

    @Override
    protected void initActionBar(Toolbar actionBar) {
        super.initActionBar(actionBar);

        //设置标题
        TextView textView = actionBar.findViewById(R.id.toolbar_title);
        textView.setText("申请外勤打考勤");

        //启用返回导航
        actionBar.setNavigationIcon(R.mipmap.title_bar_back);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getHoldingActivity().getMenuInflater().inflate(R.menu.menu_submit, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /**
         * 菜单项被点击时调用，也就是菜单项的监听方法。
         * 通过这几个方法，可以得知，对于Activity，同一时间只能显示和监听一个Menu 对象。 TODO Auto-generated
         * method stub
         */

        if (!getHoldingActivity().isNetWorkAvailable()) {
            Toast.makeText(getHoldingActivity(), "没有网络连接，请稍后重试", Toast.LENGTH_SHORT).show();
            return false;
        }

        final ProgressDialog progressDialog = new ProgressDialog(getContext());

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("加载中...");
        progressDialog.show();

        if (item.getItemId()==R.id.action_settings) {
            //提交信息
            try {
                OkHttp3Utils.getInstance().doGet(getRootApiUrl() + "/api/",
                        new GsonObjectCallback<AttendenceBean>() {

                            @Override
                            public void onSuccess(AttendenceBean attendenceBean) {
                                progressDialog.dismiss();

                                if (attendenceBean.getRequestCode() != 200) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getHoldingActivity(), "网络连接失败，请稍后重试", Toast.LENGTH_SHORT).show();
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
        }
        return super.onOptionsItemSelected(item);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //去掉底部导航
        BottomNavigationView navigation = getHoldingActivity().findViewById(R.id.navigation);
        navigation.setVisibility(View.GONE);


        return super.onCreateView(inflater, container, savedInstanceState);
    }


}
