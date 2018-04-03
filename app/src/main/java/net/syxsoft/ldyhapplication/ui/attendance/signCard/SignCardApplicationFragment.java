package net.syxsoft.ldyhapplication.ui.attendance.signCard;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.ui.AppBase.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignCardApplicationFragment extends BaseFragment {


    //@OnClick(R.id.sq_daka)
    //public void onRegisterBtnClicked(){

        //pushFragment();
    //}


    @Override
    public int getLayoutId() {
        return R.layout.fragment_kaoqi_daka_bksq;
    }

    @Override
    public void initActionBar(Toolbar actionBar) {
        super.initActionBar(actionBar);

        //设置标题
        TextView textView = actionBar.findViewById(R.id.toolbar_title);
        textView.setText("补卡申请");

        //启用返回导航
        actionBar.setNavigationIcon(R.mipmap.title_bar_back);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getHoldingActivity().getMenuInflater().inflate(R.menu.menu_submit, menu);
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
