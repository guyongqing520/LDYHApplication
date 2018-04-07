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


public class DetailsAllotFragment extends BaseFragment {

    //任務內容
    @BindView(R.id.task_detail_content)
    TextView task_detail_content;
    //领导审核
    @BindView(R.id.task_detail_ldsh)
    TextView task_detail_ldsh;

    //开始时间
    @BindView(R.id.task_detail_startTime)
    TextView task_detail_startTime;
    //结束时间
    @BindView(R.id.task_detail_endTime)
    TextView task_detail_endTime;

    //承办人
    @BindView(R.id.task_detail_cbr)
    TextView task_detail_cbr;
    //紧急程度
    @BindView(R.id.task_detail_jjcd)
    TextView task_detail_jjcd;

    //进度催办
    @OnClick(R.id.task_detail_btn_rwcb)
    public void OnJdbgBtnClicked() {
        getHoldingActivity().pushFragment(new MyChargeFragment());
    }
    //任务终止
    @OnClick(R.id.task_detail_btn_stop)
    public void OnStopBtnClicked() {
        getHoldingActivity().pushFragment(new MyChargeFragment());
    }
    //任务移交
    @OnClick(R.id.task_detail_btn_rwyj)
    public void OnRwyjBtnClicked() {
        getHoldingActivity().pushFragment(new MyChargeFragment());
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_kaoqin_renwu_detail_allot;
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
            //启用
        }else if(item.getItemId() ==R.id.action_add){
            //关联
        }else if(item.getItemId() ==R.id.action_add){
            //登记评定
        }
        return super.onOptionsItemSelected(item);
    }

}
