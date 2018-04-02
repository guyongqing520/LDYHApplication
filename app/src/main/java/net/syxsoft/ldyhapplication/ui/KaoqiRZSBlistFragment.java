package net.syxsoft.ldyhapplication.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import net.syxsoft.ldyhapplication.Adapter.AttendenceListAdapter;
import net.syxsoft.ldyhapplication.Adapter.CalendarDayWorkdailyAdapter;
import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.AttendenceListBean;
import net.syxsoft.ldyhapplication.bean.KaoqDayworkBean;
import net.syxsoft.ldyhapplication.callback.LoadCallBack;
import net.syxsoft.ldyhapplication.utils.DateUtils;
import net.syxsoft.ldyhapplication.utils.OkHttpManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnFocusChange;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static net.syxsoft.ldyhapplication.utils.DateUtils.getSimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class KaoqiRZSBlistFragment extends BaseFragment {


    private CalendarDayWorkdailyAdapter calendarDayWorkdailyAdapter;
    private int pageIndex = 1;
    private int total = 0;
    private String starttime = DateUtils.getSimpleDateFormat(Calendar.getInstance().getTime(), "yyyy-MM-01");
    private String endtime = DateUtils.getSimpleDateFormat(Calendar.getInstance().getTime(), null);
    private List<KaoqDayworkBean.SuccessInfoBean.RowsBean> rows = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zrlb_list;
    }

    @Override
    protected void initActionBar(Toolbar actionBar) {
        super.initActionBar(actionBar);

        //设置标题
        TextView textView = actionBar.findViewById(R.id.toolbar_title);
        textView.setText("日志列表");

        //启用返回导航
        actionBar.setNavigationIcon(R.mipmap.title_bar_back);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getHoldingActivity().getMenuInflater().inflate(R.menu.menu_chang_add, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                pushFragment(new KaoqiRZSBlistdateFragment());
                break;
            case android.R.id.home:
                popFragment(new KaoqiRZSBlistFragment());
                popFragment(new KaoqiRZSBlistdateFragment());
                break;

            case R.id.action_add:
                pushFragment(new KaoqiRZSBLBFragment());
                break;

        }
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);

        //去掉底部导航
        BottomNavigationView navigation = getHoldingActivity().findViewById(R.id.navigation);
        navigation.setVisibility(View.GONE);

        //布局及初始化
        pullLoadMoreRecyclerView.setLinearLayout();
        initData(starttime, endtime, true, search_text.getText() == null ? "" : search_text.getText().toString());

        start_time.setText(DateUtils.getSimpleDateFormat(Calendar.getInstance().getTime(), "yyyy年MM月01日"));
        end_time.setText(DateUtils.getSimpleDateFormat(Calendar.getInstance().getTime(), "yyyy年MM月dd日"));

        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                pullLoadMoreRecyclerView.setRefreshing(true);
                pageIndex = 1;
                rows.clear();
                initData(starttime, endtime, true, search_text.getText() == null ? "" : search_text.getText().toString());
            }

            @Override
            public void onLoadMore() {
                if (rows.size() < total) {
                    initData(starttime, endtime, false, search_text.getText() == null ? "" : search_text.getText().toString());
                } else {
                    pullLoadMoreRecyclerView.setFooterViewText("已经到底啦~");
                    pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                }
            }
        });


        return view;
    }

    //拉取列表
    private void initData(String start, String end, final boolean ispush, String key) {

        if (key == null || key.length() == 0) {
            key = "\"\"";
        }
        String url = getRootApiUrl() + "/api/workdaily/list/" + getHoldingActivity().getUserAccount().getUserid() + "/" + start + "/" + end + "/" + key + "/1/10";
        //提交信息
        OkHttpManager.getInstance().getRequest(url,
                new LoadCallBack<KaoqDayworkBean>(getContext()) {

                    @Override
                    public void OnRequestBefore(Request request) {
                        //取消加载默认加载框,首页不取消
                        if (ispush) {
                            super.OnRequestBefore(request);
                        }
                    }

                    @Override
                    public void onSuccess(Call call, Response response, KaoqDayworkBean kaoqDayworkBean) {

                        if (getHoldingActivity() != null) {
                            if (kaoqDayworkBean.getRequestCode() == 200) {
                                KaoqDayworkBean.SuccessInfoBean successInfoBeans = kaoqDayworkBean.getSuccessInfo();
                                if (successInfoBeans != null && successInfoBeans.getRows().size() >= 0) {

                                    rows.addAll(successInfoBeans.getRows());
                                    calendarDayWorkdailyAdapter = new CalendarDayWorkdailyAdapter(getContext(), successInfoBeans);
                                    pullLoadMoreRecyclerView.setAdapter(calendarDayWorkdailyAdapter);
                                    calendarDayWorkdailyAdapter.notifyDataSetChanged();

                                    pageIndex++;
                                    total = successInfoBeans.getTotal();

                                    pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                                    pullLoadMoreRecyclerView.setRefreshing(false);

                                }
                            } else {
                                pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                                pullLoadMoreRecyclerView.setRefreshing(false);
                            }
                        }
                    }

                    @Override
                    public void onEror(Call call, int statusCode, Exception e) {
                        super.onEror(call,statusCode,e);
                        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                        pullLoadMoreRecyclerView.setRefreshing(false);
                    }
                });
    }

    @BindView(R.id.pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView pullLoadMoreRecyclerView;

    @BindView(R.id.start_time)
    TextView start_time;

    @BindView(R.id.end_time)
    TextView end_time;

    @BindView(R.id.search_text)
    EditText search_text;

    @BindView(R.id.cancel_action)
    TextView cancel_action;


    @OnClick({R.id.start_time, R.id.start})
    public void onStartBtnClicked() {
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                start_time.setText(getSimpleDateFormat(date, "yyyy年MM月dd日"));
                starttime = getSimpleDateFormat(date, "yyyy-MM-dd");

                pageIndex = 1;
                rows.clear();
                initData(starttime, endtime, true, search_text.getText() == null ? "" : search_text.getText().toString());

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

    @OnClick({R.id.end_time, R.id.start2})
    public void onEndBtnClicked() {
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                end_time.setText(getSimpleDateFormat(date, "yyyy年MM月dd日"));
                endtime = getSimpleDateFormat(date, "yyyy-MM-dd");

                pageIndex = 1;
                rows.clear();
                initData(starttime, endtime, true, search_text.getText() == null ? "" : search_text.getText().toString());

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

    //取消点击
    @OnClick(R.id.cancel_action)
    public void onCancelClicked() {
        cancel_action.setVisibility(View.GONE);
        search_text.setText(null);
    }

    //搜索点击
    @OnClick(R.id.search_text)
    void onSearchClicked() {
        cancel_action.setVisibility(View.VISIBLE);
    }

    //搜索焦点获取与失去
    @OnFocusChange(R.id.search_text)
    void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            cancel_action.setVisibility(View.VISIBLE);
        } else {
            cancel_action.setVisibility(View.GONE);
            search_text.setText(null);
        }
    }

    //搜索回车
    @OnEditorAction(R.id.search_text)
    boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            initData(starttime, endtime, true, v.getText() == null ? "" : v.getText().toString());
        }

        return true;
    }

}
