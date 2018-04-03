package net.syxsoft.ldyhapplication.ui.attendance.signCard;


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

import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignCardReplenishFragment extends BaseFragment {


    @OnClick(R.id.dasj)
    public void onRegisterBtnClicked(){
        pushFragment(new SignCardApplicationFragment());
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_kaoqi_daka_buqian;
    }

    @Override
    public void initActionBar(Toolbar actionBar) {
        super.initActionBar(actionBar);

        //设置标题
        TextView textView = actionBar.findViewById(R.id.toolbar_title);
        textView.setText("补签");

        //启用返回导航
        actionBar.setNavigationIcon(R.mipmap.title_bar_back);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //启用底部导航
        BottomNavigationView navigation= getHoldingActivity().findViewById(R.id.navigation);
        navigation.setVisibility(View.VISIBLE);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
