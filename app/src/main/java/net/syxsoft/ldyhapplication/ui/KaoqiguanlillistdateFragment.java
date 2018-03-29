package net.syxsoft.ldyhapplication.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.syxsoft.ldyhapplication.Adapter.CalendarDayanalysisAdapter;
import net.syxsoft.ldyhapplication.Adapter.CalendarMonthanalysisAdapter;
import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.KaoqDayanalysisBean;
import net.syxsoft.ldyhapplication.bean.KaoqMonthanalysisBean;
import net.syxsoft.ldyhapplication.bean.UserAccountBean;
import net.syxsoft.ldyhapplication.callback.GsonObjectCallback;
import net.syxsoft.ldyhapplication.callback.LoadCallBack;
import net.syxsoft.ldyhapplication.model.UserModel;
import net.syxsoft.ldyhapplication.utils.DateUtils;
import net.syxsoft.ldyhapplication.utils.OkHttp3Utils;
import net.syxsoft.ldyhapplication.utils.OkHttpManager;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class KaoqiguanlillistdateFragment extends BaseFragment {

    //定义adapter
    private CalendarMonthanalysisAdapter dateAdapter;
    private CalendarDayanalysisAdapter calendarDayanalysisAdapter;
    private String title;
    private int year;
    private int month;
    private int today;
    private int[][] days = new int[6][7];


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kaoqiguanli_list_date;
    }

    @Override
    protected void initActionBar(Toolbar actionBar) {
        super.initActionBar(actionBar);

        //设置标题
        TextView textView = actionBar.findViewById(R.id.toolbar_title);
        textView.setText("考勤列表");

        //启用返回导航
        actionBar.setNavigationIcon(R.mipmap.title_bar_back);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getHoldingActivity().getMenuInflater().inflate(R.menu.menu_chang, menu);
    }

    private void initData() {

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        today = calendar.get(Calendar.DATE);
        days = DateUtils.getDayOfMonthFormat(year, month);

        setTitle();

        setSelectDateTitle(year, month, today, DateUtils.dateToWeek(calendar.getTime().toString()));
    }

    //拉取月考勤
    private void initKaoqin() {
        //提交信息
        OkHttpManager.getInstance().getRequest(getRootApiUrl() + "/api/attendence/monthanalysis/" + getHoldingActivity().getUserAccount().getUserid() + "/" + year + "/" + month,
                new LoadCallBack<KaoqMonthanalysisBean>(getContext()) {

                    @Override
                    public void onSuccess(Call call, Response response, KaoqMonthanalysisBean kaoqMonthanalysisBean) {

                        if (getHoldingActivity() != null) {
                            if (kaoqMonthanalysisBean.getRequestCode() == 200 && !getHoldingActivity().isFinishing()) {
                                dateAdapter = new CalendarMonthanalysisAdapter(getContext(), days, year, month, today, kaoqMonthanalysisBean, recylerviewdatedayanalysisView,
                                        getHoldingActivity().getUserAccount().getUserid(), select_date);//传入当前月的年
                                recyclerView.setAdapter(dateAdapter);
                                dateAdapter.notifyDataSetChanged();

                            }
                        }
                    }

                    @Override
                    public void onEror(Call call, int statusCode, Exception e) {

                    }
                });
    }

    //拉取个人考勤情况
    public void initDayanalysis(int year, int month, int day) {

        //提交信息
        OkHttpManager.getInstance().getRequest(getRootApiUrl() + "/api/attendence/dayanalysis/" + getHoldingActivity().getUserAccount().getUserid() + "/" + year +
                        "-" + month + "-" + today,
                new LoadCallBack<KaoqDayanalysisBean>(getContext()) {

                    @Override
                    public void onSuccess(Call call, Response response, KaoqDayanalysisBean kaoqDayanalysisBean) {

                        if (getHoldingActivity() != null) {
                            if (kaoqDayanalysisBean.getRequestCode() == 200 && !getHoldingActivity().isFinishing()) {
                                List<KaoqDayanalysisBean.SuccessInfoBean> successInfoBeans = kaoqDayanalysisBean.getSuccessInfo();
                                if (successInfoBeans != null && successInfoBeans.size() > 0) {
                                    calendarDayanalysisAdapter = new CalendarDayanalysisAdapter(getContext(), successInfoBeans);
                                    recylerviewdatedayanalysisView.setAdapter(calendarDayanalysisAdapter);
                                    calendarDayanalysisAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }

                    @Override
                    public void onEror(Call call, int statusCode, Exception e) {

                    }
                });
    }

    //日历下一个月
    private int[][] nextMonth() {
        if (month == 12) {
            month = 1;
            year++;
        } else {
            month++;
        }
        days = DateUtils.getDayOfMonthFormat(year, month);
        return days;
    }

    //日历上一个月
    private int[][] prevMonth() {
        if (month == 1) {
            month = 12;
            year--;
        } else {
            month--;
        }
        days = DateUtils.getDayOfMonthFormat(year, month);
        return days;
    }

    //设置日历标题
    private void setTitle() {
        title = year + "年" + month + "月";
        record_title.setText(title);
    }

    private void setSelectDateTitle(int year, int month, int day, String week) {
        String title = year + "年" + month + "月" + day + "（" + week + "）";
        select_date.setText(title);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        //去掉底部导航
        BottomNavigationView navigation = getHoldingActivity().findViewById(R.id.navigation);
        navigation.setVisibility(View.GONE);

        initData();

        //日历布局及初始化日历数据
        GridLayoutManager layoutManager = new GridLayoutManager(container.getContext(), 7);
        recyclerView.setLayoutManager(layoutManager);
        initKaoqin();

        //布局及初始化个人考勤情况
        recylerviewdatedayanalysisView.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, true));
        //initDayanalysis(year, month, today);


        return view;
    }

    @OnClick(R.id.record_left)
    public void onRecordLeftBtnClicked() {
        days = prevMonth();
        initKaoqin();
        setTitle();
    }

    @OnClick(R.id.record_right)
    public void onRecordRightBtnClicked() {
        days = nextMonth();
        initKaoqin();
        setTitle();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                pushFragment(new KaoqiguanlillistFragment());
                break;

        }
        return false;
    }

    @BindView(R.id.record_title)
    TextView record_title;

    @BindView(R.id.recyler_view_date)
    RecyclerView recyclerView;

    @BindView(R.id.recyler_view_date_dayanalysis)
    RecyclerView recylerviewdatedayanalysisView;

    @BindView(R.id.select_date)
    TextView select_date;


}
