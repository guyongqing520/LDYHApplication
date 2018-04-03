package net.syxsoft.ldyhapplication.ui.complaint;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.ui.AppBase.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;


public class AddFragment extends BaseFragment {

    @BindView(R.id.tousu_type_dw)
    TextView tousu_type_dw;

    @BindView(R.id.tousu_type_dw_bt)
    TextView tousu_type_dw_bt;

    @BindView(R.id.tousu_type_gr)
    TextView tousu_type_gr;

    @BindView(R.id.tousu_type_gr_bt)
    TextView tousu_type_gr_bt;

    @BindView(R.id.tousu_btsr)
    TextView tousu_btsr;

    int navblue=1;
    int word999=1;
    int grey=1;
    @OnClick(R.id.tousu_type_gr)
    public void onGrClicked(){
        tousu_type_dw.setTextColor(word999);
        tousu_type_dw_bt.setBackgroundColor(grey);
        tousu_type_gr.setTextColor(navblue);
        tousu_type_gr_bt.setBackgroundColor(navblue);

        tousu_btsr.setText("被投诉人");
    }
    @OnClick(R.id.tousu_type_dw)
    public void onDwClicked(){
        tousu_type_dw.setTextColor(navblue);
        tousu_type_dw_bt.setBackgroundColor(navblue);
        tousu_type_gr.setTextColor(word999);
        tousu_type_gr_bt.setBackgroundColor(grey);

        tousu_btsr.setText("被投诉单位");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_kaoqin_tousu_new;
    }

    @Override
    public void initActionBar(Toolbar actionBar) {
        super.initActionBar(actionBar);

        //设置标题
        TextView textView = actionBar.findViewById(R.id.toolbar_title);
        textView.setText("投诉举报");

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
        navblue=getResources().getColor( R.color.colornavblue);
        word999=getResources().getColor( R.color.colorWord99);
        grey=getResources().getColor( R.color.grey);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
