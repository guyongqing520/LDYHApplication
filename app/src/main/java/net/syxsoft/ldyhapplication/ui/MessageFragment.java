package net.syxsoft.ldyhapplication.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;

import net.syxsoft.ldyhapplication.Adapter.MainAdapter;
import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.MainPanel;
import net.syxsoft.ldyhapplication.callback.BeanCallback;
import net.syxsoft.ldyhapplication.model.RequestBase;
import net.syxsoft.ldyhapplication.utils.GsonUtil;
import net.syxsoft.ldyhapplication.utils.SliderLayoutHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends BaseFragment {

    //@OnClick(R.id.register_btn)
    //public void onRegisterBtnClicked(){
        //pushFragment(VerifyFragment.newInstance(VerifyFragment.REGISTER));
    //}

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initActionBar(Toolbar actionBar) {
        super.initActionBar(actionBar);

        //标题
        TextView textView = actionBar.findViewById(R.id.toolbar_title);
        textView.setText("消息");

        //禁用返回导航，因为此页就是首页
        actionBar.setNavigationIcon(null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //启用底部导航
        BottomNavigationView navigation= getHoldingActivity().findViewById(R.id.navigation);
        navigation.setVisibility(View.VISIBLE);

        return  super.onCreateView(inflater,container,  savedInstanceState);
    }
}
