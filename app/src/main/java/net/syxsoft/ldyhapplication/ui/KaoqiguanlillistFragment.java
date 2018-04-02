package net.syxsoft.ldyhapplication.ui;


import android.os.Binder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
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

import com.bigkoo.pickerview.TimePickerView;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import net.syxsoft.ldyhapplication.Adapter.AttendenceListAdapter;
import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.AttendenceListBean;
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
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static net.syxsoft.ldyhapplication.utils.DateUtils.getSimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class KaoqiguanlillistFragment extends BaseFragment {

    private AttendenceListAdapter attendenceListAdapter;
    private int pageIndex = 1;
    private int total = 0;
    private String starttime=DateUtils.getSimpleDateFormat(Calendar.getInstance().getTime(),"yyyy-MM-01");
    private String endtime=DateUtils.getSimpleDateFormat(Calendar.getInstance().getTime(),null);
    private List<AttendenceListBean.SuccessInfoBean.RowsBean> rows = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kaoqiguanli_list;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                pushFragment(new KaoqiguanlillistdateFragment());
                break;
            case android.R.id.home:
                popFragment(new KaoqiguanlillistdateFragment());
                popFragment(new KaoqiguanlillistmyqijiaFragment());
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
        initData(starttime, endtime,true);

        start_time.setText(DateUtils.getSimpleDateFormat(Calendar.getInstance().getTime(),"yyyy年MM月01日"));
        end_time.setText(DateUtils.getSimpleDateFormat(Calendar.getInstance().getTime(),"yyyy年MM月dd日"));

        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                pullLoadMoreRecyclerView.setRefreshing(true);
                pageIndex = 1;
                rows.clear();
                initData(starttime, endtime,true);
            }

            @Override
            public void onLoadMore() {
                if (rows.size() < total) {
                    initData(starttime, endtime,false);
                } else {
                    pullLoadMoreRecyclerView.setFooterViewText("已经到底啦~");
                    pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                }
            }
        });

        return view;
    }

    //拉取列表
    private void initData(String start, String end,final boolean ispush) {

        //提交信息
        OkHttpManager.getInstance().getRequest(getRootApiUrl() + "/api/attendence/list/" + getHoldingActivity().getUserAccount().getUserid() + "/" + start +
                        "/" + end + "/" + pageIndex + "/" + "10",
                new LoadCallBack<AttendenceListBean>(getContext()) {

                    @Override
                    public void OnRequestBefore(Request request) {
                      //取消加载默认加载框,首页不取消
                        if(ispush){
                            super.OnRequestBefore(request);
                        }
                    }

                    @Override
                    public void onSuccess(Call call, Response response, AttendenceListBean attendenceListBean) {

                        if (getHoldingActivity()!=null) {
                            if (attendenceListBean.getRequestCode() == 200) {
                                AttendenceListBean.SuccessInfoBean successInfoBeans = attendenceListBean.getSuccessInfo();
                                if (successInfoBeans != null && successInfoBeans.getRows().size() >= 0) {

                                    rows.addAll(successInfoBeans.getRows());
                                    attendenceListAdapter = new AttendenceListAdapter(getContext(), rows);
                                    pullLoadMoreRecyclerView.setAdapter(attendenceListAdapter);
                                    attendenceListAdapter.notifyDataSetChanged();

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


    @OnClick({R.id.start_time,R.id.start})
    public void onStartBtnClicked() {
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                start_time.setText(getSimpleDateFormat(date, "yyyy年MM月dd日"));
                starttime=getSimpleDateFormat(date, "yyyy-MM-dd");

                pageIndex = 1;
                rows.clear();
                initData(starttime, endtime,true);

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

    @OnClick({R.id.end_time,R.id.start2})
    public void onEndBtnClicked() {
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                end_time.setText(getSimpleDateFormat(date, "yyyy年MM月dd日"));
                endtime=getSimpleDateFormat(date, "yyyy-MM-dd");

                pageIndex = 1;
                rows.clear();
                initData(starttime, endtime,true);

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


    @OnClick(R.id.tab_text2)
    public void onTabBtnClicked() {
        getHoldingActivity().pushFragment(new KaoqiguanlillistmyqijiaFragment());
    }
}
