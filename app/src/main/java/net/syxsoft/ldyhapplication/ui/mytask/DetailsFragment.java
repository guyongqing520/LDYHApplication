package net.syxsoft.ldyhapplication.ui.mytask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.ui.AppBase.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;


public class DetailsFragment extends BaseFragment {

    //任務內容
    @BindView(R.id.task_detail_content)
    TextView task_detail_content;
    //請假類型
    @BindView(R.id.task_detail_lx)
    TextView task_detail_lx;

    //开始时间
    @BindView(R.id.task_detail_startTime)
    TextView task_detail_startTime;
    //结束时间
    @BindView(R.id.task_detail_endTime)
    TextView task_detail_endTime;

    //时长
    @BindView(R.id.task_detail_sc)
    TextView task_detail_sc;
    //紧急程度
    @BindView(R.id.task_detail_jjcd)
    TextView task_detail_jjcd;

    //进度报告
    @OnClick(R.id.task_detail_btn_jdbg)
    public void OnJdbgBtnClicked() {
        getHoldingActivity().pushFragment(new MyChargeFragment());
    }
    //任务终止
    @OnClick(R.id.task_detail_btn_stop)
    public void OnStopBtnClicked() {
        getHoldingActivity().pushFragment(new MyChargeFragment());
    }
    //任务完成
    @OnClick(R.id.task_detail_btn_over)
    public void OnOverBtnClicked() {
        getHoldingActivity().pushFragment(new MyChargeFragment());
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_kaoqin_renwu_detail;
    }

    @Override
    public void initActionBar(Toolbar actionBar) {
        super.initActionBar(actionBar);

        //设置标题
        TextView textView = actionBar.findViewById(R.id.toolbar_title);
        textView.setText("任务详情");

        //启用返回导航
        actionBar.setNavigationIcon(R.mipmap.title_bar_back);

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getHoldingActivity().getMenuInflater().inflate(R.menu.navigation_renwu, menu);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //去掉底部导航
        BottomNavigationView navigation= getHoldingActivity().findViewById(R.id.navigation);
        navigation.setVisibility(View.GONE);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.action_settings) {
            //请示
        }else if(item.getItemId() ==R.id.action_add){
            //移交
        }else if(item.getItemId() ==R.id.action_add){
            //任务分解
        }
        else if(item.getItemId() ==R.id.action_add) {
            //关联日志
        }
        return super.onOptionsItemSelected(item);
    }

}
