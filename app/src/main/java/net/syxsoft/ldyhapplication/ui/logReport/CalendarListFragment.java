package net.syxsoft.ldyhapplication.ui.logReport;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import net.syxsoft.ldyhapplication.Adapter.CalendarDayWorkdailyAdapter;
import net.syxsoft.ldyhapplication.Adapter.CalendarMonthWorkdailyAdapter;
import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.KaoqDayworkBean;
import net.syxsoft.ldyhapplication.bean.KaoqMonthworkBean;
import net.syxsoft.ldyhapplication.callback.LoadCallBack;
import net.syxsoft.ldyhapplication.ui.AppBase.BaseFragment;
import net.syxsoft.ldyhapplication.utils.DateUtils;
import net.syxsoft.ldyhapplication.utils.OkHttpManager;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarListFragment extends BaseFragment {

    //定义adapter
    private CalendarMonthWorkdailyAdapter dateAdapter;
    private CalendarDayWorkdailyAdapter calendarDayWorkdailyAdapter;
    private String title;
    private int year;
    private int month;
    private int today;
    private int[][] days = new int[6][7];


    @Override
    public int getLayoutId() {
        return R.layout.fragment_zrlb_list_date;
    }

    @Override
    public void initActionBar(Toolbar actionBar) {
        super.initActionBar(actionBar);

        //设置标题
        TextView textView = actionBar.findViewById(R.id.toolbar_title);
        textView.setText("日志列表");

        //启用返回导航
        actionBar.setNavigationIcon(R.mipmap.title_bar_back);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getHoldingActivity().getMenuInflater().inflate(R.menu.menu_chang_add,menu);
    }

    private void initData() {

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        today=calendar.get(Calendar.DATE);
        days = DateUtils.getDayOfMonthFormat(year, month);

        setTitle();
    }

    //拉取月任务
    private void initMonthData() {
        //提交信息
        OkHttpManager.getInstance().getRequest(getRootApiUrl() + "/api/workdaily/calendarlist/" + getHoldingActivity().getUserAccount().getUserid() + "/" + year + "-" + month,
                new LoadCallBack<KaoqMonthworkBean>(getContext()) {

                    @Override
                    public void onSuccess(Call call, Response response, KaoqMonthworkBean kaoqMonthworkBean) {

                        if (getHoldingActivity() != null) {
                            if (kaoqMonthworkBean.getRequestCode() == 200 && !getHoldingActivity().isFinishing()) {
                                dateAdapter = new CalendarMonthWorkdailyAdapter(getContext(), days, year, month, today, kaoqMonthworkBean, recylerviewdatedayworkView,
                                        getHoldingActivity().getUserAccount().getUserid());//传入当前月的年
                                recyclerView.setAdapter(dateAdapter);
                                dateAdapter.notifyDataSetChanged();

                            }
                        }
                    }
                });
    }

    //拉取天任务
    public void initDayData(int year, int month, int day) {

        //提交信息
        OkHttpManager.getInstance().getRequest(getRootApiUrl() + "/api/workdaily/list/" + getHoldingActivity().getUserAccount().getUserid() + "/" + year +
                        "-" + month + "-" + today+"/" + year +
                        "-" + month + "-" + today+"/"+""+"/1/10",
                new LoadCallBack<KaoqDayworkBean>(getContext()) {

                    @Override
                    public void onSuccess(Call call, Response response, KaoqDayworkBean kaoqDayworkBean) {

                        if (getHoldingActivity() != null) {
                            if (kaoqDayworkBean.getRequestCode() == 200 && !getHoldingActivity().isFinishing()) {
                                KaoqDayworkBean.SuccessInfoBean successInfoBean = kaoqDayworkBean.getSuccessInfo();
                                if (successInfoBean != null && successInfoBean.getRows().size() > 0) {
                                    calendarDayWorkdailyAdapter = new CalendarDayWorkdailyAdapter(getContext(),successInfoBean);
                                    recylerviewdatedayworkView.setAdapter(calendarDayWorkdailyAdapter);
                                    calendarDayWorkdailyAdapter.notifyDataSetChanged();
                                }
                            }
                        }
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        //去掉底部导航
        BottomNavigationView navigation = getHoldingActivity().findViewById(R.id.navigation);
        navigation.setVisibility(View.GONE);

        //初始化数据
        initData();

        //日历布局及初始化日历数据
        GridLayoutManager layoutManager = new GridLayoutManager(container.getContext(), 7);
        recyclerView.setLayoutManager(layoutManager);
        initMonthData();

        //布局及初始化个人考勤情况
        recylerviewdatedayworkView.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, true));
        //initDayData(year, month, today);

        return view;
    }

    @OnClick(R.id.record_left)
    public void onRecordLeftBtnClicked(){
        days = prevMonth();
        initMonthData();
        setTitle();
    }

    @OnClick(R.id.record_right)
    public void onRecordRightBtnClicked(){
        days = nextMonth();
        initMonthData();
        setTitle();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                pushFragment(new ListFragment());
                break;

            case R.id.action_add:
                pushFragment(new ReplenishFragment());
                break;

        }
        return false;
    }


    //搜索
    @OnEditorAction(R.id.search_text)
    boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {

        }

        return true;
    }

    @BindView(R.id.record_title)
    TextView record_title;

    @BindView(R.id.recyler_view_date)
    RecyclerView recyclerView;


    @BindView(R.id.recyler_view_date_daywork)
    RecyclerView recylerviewdatedayworkView;

}
