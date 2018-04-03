package net.syxsoft.ldyhapplication.ui.attendance;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.ui.AppBase.BaseFragment;
import net.syxsoft.ldyhapplication.ui.attendance.leaveApproval.ListFragment;
import net.syxsoft.ldyhapplication.ui.attendance.management.MySignCardCalendarListFragment;
import net.syxsoft.ldyhapplication.ui.attendance.signCard.SignCardActivity;

import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationListFragment extends BaseFragment {


    //@BindView(R.id.user_password)
    //TextInputEditText mUserPsw;

    @OnClick(R.id.daka)
    public void onRegisterBtnClicked(){

        Intent intent = new Intent();
        intent.setClass(getHoldingActivity(), SignCardActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.kaoqinlist)
    public void onKaoqinlistBtnClicked(){
        getHoldingActivity().pushFragment(new MySignCardCalendarListFragment());
    }

    @OnClick(R.id.qinjashengpi)
    public void onQinjashengpiBtnClicked(){
        getHoldingActivity().pushFragment(new ListFragment());
    }

    @OnClick(R.id.qinjashenqi)
    public void onQinjashenqiBtnClicked(){
        getHoldingActivity().pushFragment(new LeaveApplicationFragment());
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_kaoqi_daka_kaoqiguli;
    }

    @Override
    public void initActionBar(Toolbar actionBar) {
        super.initActionBar(actionBar);

        //设置标题
        TextView textView = actionBar.findViewById(R.id.toolbar_title);
        textView.setText("考勤管理");

        //启用返回导航
        actionBar.setNavigationIcon(R.mipmap.title_bar_back);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //去掉底部导航
        BottomNavigationView navigation= getHoldingActivity().findViewById(R.id.navigation);
        navigation.setVisibility(View.GONE);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
