package net.syxsoft.ldyhapplication.ui;

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
import net.syxsoft.ldyhapplication.Adapter.CalendarAdapter;
import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.utils.DateUtils;


import java.util.Calendar;

import butterknife.BindView;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class KaoqiguanlillistdateFragment extends BaseFragment{

    //定义adapter
    private CalendarAdapter dateAdapter;
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
        getHoldingActivity().getMenuInflater().inflate(R.menu.menu_chang,menu);
    }

    private void initData() {

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        today=calendar.get(Calendar.DATE);
        days = DateUtils.getDayOfMonthFormat(year, month);

        setTile();
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        //去掉底部导航
        BottomNavigationView navigation = getHoldingActivity().findViewById(R.id.navigation);
        navigation.setVisibility(View.GONE);

        //日历布局
        GridLayoutManager layoutManager = new GridLayoutManager(container.getContext(), 7);
        recyclerView.setLayoutManager(layoutManager);

        //初始化数据
        initData();

        dateAdapter = new CalendarAdapter(container.getContext(), days, year, month,today);//传入当前月的年
        recyclerView.setAdapter(dateAdapter);

        return view;
    }

    @OnClick(R.id.record_left)
    public void onRecordLeftBtnClicked(){
        days = prevMonth();
        dateAdapter = new CalendarAdapter(getContext(),days, year, month,today);
        recyclerView.setAdapter(dateAdapter);
        dateAdapter.notifyDataSetChanged();
        setTile();
    }

    @OnClick(R.id.record_right)
    public void onRecordRightBtnClicked(){
        days = nextMonth();
        dateAdapter = new CalendarAdapter(getContext(),days, year, month,today);
        recyclerView.setAdapter(dateAdapter);
        dateAdapter.notifyDataSetChanged();
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
