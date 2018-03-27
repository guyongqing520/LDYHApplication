package net.syxsoft.ldyhapplication.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
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
import net.syxsoft.ldyhapplication.bean.ResultBean;
import net.syxsoft.ldyhapplication.callback.LoadCallBack;
import net.syxsoft.ldyhapplication.utils.MyAlert;
import net.syxsoft.ldyhapplication.utils.OkHttpManager;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

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
                starttime_select.setText(getSimpleDateFormat(date, "yyyy-MM-dd HH:mm"));
            }
        })
                .setSubmitText("确定")
                .setCancelText("取消")
                .setTitleText("请选择时间")
                .setType(new boolean[]{true, true, true, true, true, false})
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
                endtime_select.setText(getSimpleDateFormat(date, "yyyy-MM-dd HH:mm"));
            }
        })
                .setSubmitText("确定")
                .setCancelText("取消")
                .setTitleText("请选择时间")
                .setType(new boolean[]{true, true, true, true, true, false})
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

        if (item.getItemId() == R.id.action_settings) {
            //提交信息

            if (starttime_select.getText() == null || starttime_select.getText().toString().length() <= 0) {
                new MyAlert("", "请选择开始时间", true, false, getContext());
            }

            if (endtime_select.getText() == null || endtime_select.getText().toString().length() <= 0) {
                new MyAlert("", "请选择结束时间", true, false, getContext());
            }

            if (remark.getText() == null || remark.getText().toString().length() <= 0) {
                new MyAlert("", "请输入备注内容", true, false, getContext());
            }

            Map<String, String> params = new HashMap<>();
            params.put("personId", getHoldingActivity().getUserAccount().getUserid());
            params.put("reason", remark.getText().toString());
            params.put("start", starttime_select.getText().toString());
            params.put("end", endtime_select.getText().toString());

            OkHttpManager.getInstance().postRequest(getRootApiUrl() + "/api/attappply/addout",
                    new LoadCallBack<ResultBean>(getContext()) {

                        @Override
                        public void onSuccess(Call call, Response response, ResultBean resultBean) {

                            if (resultBean.getRequestCode() != 200) {
                                Toast.makeText(getHoldingActivity(), resultBean.getErrorMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        public void onEror(Call call, int statusCode, Exception e) {
                        }
                    }, params);

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
