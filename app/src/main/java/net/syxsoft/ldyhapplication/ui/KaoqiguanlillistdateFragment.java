package net.syxsoft.ldyhapplication.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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

import net.syxsoft.ldyhapplication.Adapter.CalendarMonthanalysisAdapter;
import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.KaoqMonthanalysisBean;
import net.syxsoft.ldyhapplication.bean.UserAccountBean;
import net.syxsoft.ldyhapplication.callback.GsonObjectCallback;
import net.syxsoft.ldyhapplication.model.UserModel;
import net.syxsoft.ldyhapplication.utils.DateUtils;
import net.syxsoft.ldyhapplication.utils.OkHttp3Utils;


import java.io.IOException;
import java.util.Calendar;

import butterknife.BindView;

import butterknife.OnClick;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class KaoqiguanlillistdateFragment extends BaseFragment {

    //定义adapter
    private CalendarMonthanalysisAdapter dateAdapter;
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

        setTile();
    }

    private void initKaoqin(){
        //拉取月考勤
        if (!getHoldingActivity().isNetWorkAvailable()) {
            Toast.makeText(getHoldingActivity(), "没有网络连接，请稍后重试", Toast.LENGTH_SHORT).show();
            return;
        }

        //加载个人信息
        UserModel userModel = new UserModel();
        UserAccountBean userAccountBean = userModel.getUserAccountInfo(getContext());

        if (userAccountBean == null || userAccountBean.getUserid() == null || userAccountBean.getUserid().length() == 0) {
            //导航到login
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getHoldingActivity().finish();

            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(getContext());

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("加载中...");
        progressDialog.show();

        //提交信息
        try {
            OkHttp3Utils.getInstance().doGet(getRootApiUrl() + "/api/attendence/monthanalysis/" + userAccountBean.getUserid() + "/" + year + "/" + month,
                    new GsonObjectCallback<KaoqMonthanalysisBean>() {

                        @Override
                        public void onSuccess(KaoqMonthanalysisBean kaoqMonthanalysisBean) {

                            if (kaoqMonthanalysisBean.getRequestCode() != 200) {
                                progressDialog.dismiss();
                                Toast.makeText(getHoldingActivity(), "网络连接失败，请稍后重试", Toast.LENGTH_SHORT).show();
                            } else {
                                dateAdapter = new CalendarMonthanalysisAdapter(getContext(), days, year, month, today, kaoqMonthanalysisBean);//传入当前月的年
                                recyclerView.setAdapter(dateAdapter);
                                dateAdapter.notifyDataSetChanged();
                            }
                            progressDialog.dismiss();
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
    private void setTile() {
        title = year + "年" + month + "月";
        record_title.setText(title);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        //去掉底部导航
        BottomNavigationView navigation = getHoldingActivity().findViewById(R.id.navigation);
        navigation.setVisibility(View.GONE);

        //日历布局
        GridLayoutManager layoutManager = new GridLayoutManager(container.getContext(), 7);
        recyclerView.setLayoutManager(layoutManager);

        //初始化数据
        initData();
        dateAdapter = new CalendarMonthanalysisAdapter(getContext(), days, year, month, today, null);//传入当前月的年
        recyclerView.setAdapter(dateAdapter);

        initKaoqin();

        return view;
    }

    @OnClick(R.id.record_left)
    public void onRecordLeftBtnClicked() {
        days = prevMonth();
        initKaoqin();
        setTile();
    }

    @OnClick(R.id.record_right)
    public void onRecordRightBtnClicked() {
        days = nextMonth();
        initKaoqin();
        setTile();
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


}
