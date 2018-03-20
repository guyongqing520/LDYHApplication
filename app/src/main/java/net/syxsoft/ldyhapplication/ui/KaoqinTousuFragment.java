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

public class KaoqinRenwuFragment extends BaseFragment {

    @OnClick(R.id.renwu_new)
    public void onNewRenwuBtnClicked(){getHoldingActivity().pushFragment(new KaoqinRenwuNewFragment());}
    @OnClick(R.id.renwu_fz_list)
    public void onFzBtnClicked(){getHoldingActivity().pushFragment(new KaoqinRenwuFzListFragment());}
    @OnClick(R.id.renwu_pf_list)
    public void onPfBtnClicked(){
        getHoldingActivity().pushFragment(new KaoqinRenwuPfListFragment());
    }
    @OnClick(R.id.renwu_cy_list)
    public void onCyBtnClicked(){
        getHoldingActivity().pushFragment(new KaoqinRenwuCyListFragment());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kaoqin_renwu;
    }

    @Override
    protected void initActionBar(Toolbar actionBar) {
        super.initActionBar(actionBar);

        //设置标题
        TextView textView = actionBar.findViewById(R.id.toolbar_title);
        textView.setText("任务管理");

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
