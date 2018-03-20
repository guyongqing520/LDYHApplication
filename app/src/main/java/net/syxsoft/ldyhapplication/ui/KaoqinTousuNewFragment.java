package net.syxsoft.ldyhapplication.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import net.syxsoft.ldyhapplication.R;

import java.util.List;


public class KaoqinRenwuNewFragment extends BaseFragment {

    private List<String> zycd_list=null;
    private ArrayAdapter<String> zycd_Adapter=null;
    private Spinner zycd_spinner=null;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kaoqin_renwu_new;
    }

    @Override
    protected void initActionBar(Toolbar actionBar) {
        super.initActionBar(actionBar);

        //设置标题
        TextView textView = actionBar.findViewById(R.id.toolbar_title);
        textView.setText("创建任务");

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

        //重要程度
//        zycd_spinner=(Spinner)container.findViewById(R.id.renwu_spinner_zycd);
//        zycd_spinner.setPrompt("请选择重要程度");
//        zycd_list=new ArrayList<String>();
//        zycd_list.add("很重要哦");
//        zycd_list.add("重要");
//        zycd_list.add("一般");
//
//        zycd_Adapter=new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,zycd_list);
//        zycd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        zycd_spinner.setAdapter(zycd_Adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
