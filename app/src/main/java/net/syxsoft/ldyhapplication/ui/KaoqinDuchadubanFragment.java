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

import butterknife.BindView;
import butterknife.OnClick;

public class KaoqinDuchadubanFragment extends BaseFragment {

    @BindView(R.id.duchaduban_rtj)
     TextView duchaduban_rtj;

    @BindView(R.id.duchaduban_ztj)
      TextView duchaduban_ztj;

    @BindView(R.id.duchaduban_ytj)
      TextView duchaduban_ytj;

    @BindView(R.id.duchaduban_counts)
      TextView duchaduban_counts;

    @BindView(R.id.duchaduban_rtj_bt)
    TextView duchaduban_rtj_bt;

    @BindView(R.id.duchaduban_ztj_bt)
    TextView duchaduban_ztj_bt;

    @BindView(R.id.duchaduban_ytj_bt)
    TextView duchaduban_ytj_bt;

    int navblue=1;
    int word999=1;
    int grey=1;

    @OnClick(R.id.duchaduban_rtj)
    public void onDuchadubanRtjClicked(){
        duchaduban_rtj.setTextColor(navblue);
        duchaduban_ztj.setTextColor(word999);
        duchaduban_ytj.setTextColor(word999);
        duchaduban_rtj_bt.setBackgroundColor(navblue);
        duchaduban_ztj_bt.setBackgroundColor(grey);
        duchaduban_ytj_bt.setBackgroundColor(grey);
        duchaduban_counts.setText("考情人数15人");
    }
    @OnClick(R.id.duchaduban_ztj)
    public void onDuchadubanZtjClicked(){
        duchaduban_rtj.setTextColor(word999);
        duchaduban_ztj.setTextColor(navblue);
        duchaduban_ytj.setTextColor(word999);
        duchaduban_rtj_bt.setBackgroundColor(grey);
        duchaduban_ztj_bt.setBackgroundColor(navblue);
        duchaduban_ytj_bt.setBackgroundColor(grey);
        duchaduban_counts.setText("考情人数18人");
    }
    @OnClick(R.id.duchaduban_ytj)
    public void onDuchadubanYtjClicked(){
        duchaduban_rtj.setTextColor(word999);
        duchaduban_ztj.setTextColor(word999);
        duchaduban_ytj.setTextColor(navblue);
        duchaduban_rtj_bt.setBackgroundColor(grey);
        duchaduban_ztj_bt.setBackgroundColor(grey);
        duchaduban_ytj_bt.setBackgroundColor(navblue);
        duchaduban_counts.setText("考情人数30人");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kaoqin_duchaduban;
    }

    @Override
    protected void initActionBar(Toolbar actionBar) {
        super.initActionBar(actionBar);

        //设置标题
        TextView textView = actionBar.findViewById(R.id.toolbar_title);
        textView.setText("督察督办");

        //启用返回导航
        actionBar.setNavigationIcon(R.mipmap.title_bar_back);

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
