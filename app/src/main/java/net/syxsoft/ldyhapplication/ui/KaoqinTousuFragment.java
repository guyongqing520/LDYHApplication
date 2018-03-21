package net.syxsoft.ldyhapplication.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.syxsoft.ldyhapplication.R;

import butterknife.OnClick;

public class KaoqinTousuFragment extends BaseFragment {

    @OnClick(R.id.tousu_new)
    public void onNewRenwuBtnClicked(){getHoldingActivity().pushFragment(new KaoqinTousuNewFragment());}
    @OnClick(R.id.tousu_list)
    public void onPfBtnClicked(){
        //getHoldingActivity().pushFragment(new KaoqinRenwuPfListFragment());
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kaoqin_tousu;
    }

    @Override
    protected void initActionBar(Toolbar actionBar) {
        super.initActionBar(actionBar);

        //设置标题
        TextView textView = actionBar.findViewById(R.id.toolbar_title);
        textView.setText("投诉举报");

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
