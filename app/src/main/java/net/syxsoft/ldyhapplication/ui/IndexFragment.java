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
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;

import net.syxsoft.ldyhapplication.Adapter.MainAdapter;
import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.MainPanel;
import net.syxsoft.ldyhapplication.callback.GsonArrayCallback;
import net.syxsoft.ldyhapplication.utils.NetWorkUtils;
import net.syxsoft.ldyhapplication.utils.OkHttp3Utils;
import net.syxsoft.ldyhapplication.utils.SliderLayoutHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexFragment extends BaseFragment {

    private List<MainPanel> mainPanelList =new ArrayList<MainPanel>();


    //@BindView(R.id.user_password)
    //TextInputEditText mUserPsw;

    //@OnClick(R.id.register_btn)
    //public void onRegisterBtnClicked(){
        //pushFragment(VerifyFragment.newInstance(VerifyFragment.REGISTER));
    //}

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index;
    }

    @Override
    protected void initActionBar(Toolbar actionBar) {
        super.initActionBar(actionBar);

        //标题
        TextView textView = actionBar.findViewById(R.id.toolbar_title);
        textView.setText("主页");

        //禁用返回导航，因为此页就是首页
        actionBar.setNavigationIcon(null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater,container,  savedInstanceState);

        //启用底部导航
        BottomNavigationView navigation= getHoldingActivity().findViewById(R.id.navigation);
        navigation.setVisibility(View.VISIBLE);

        //滚动图片
        View view= inflater.inflate(R.layout.fragment_index, container, false);
        SliderLayout sliderLayout =view.findViewById(R.id.slider);
        SliderLayoutHelper.SliderLayoutInit(sliderLayout,container.getContext(),view);

        //主功能面板
        final RecyclerView recyclerView= view.findViewById(R.id.recyler_view);
        GridLayoutManager layoutManager= new GridLayoutManager(container.getContext(),3);
        recyclerView.setLayoutManager(layoutManager);


        //OkHttp3Utils.getInstance().doGet("http://192.168.1.105/mian.json", new GsonArrayCallback<MainPanel>() {

        //    @Override
        //    public void onSuccess(List<MainPanel> list) {
        //       recyclerView.setAdapter(new MainAdapter(list));
        //    }

         //   @Override
        //    public void onFailed(Call call, IOException e) {

         //   }
        //});

          mainPanelList.clear();
          mainPanelList.add(new MainPanel(R.mipmap.kqgl01,"考勤管理"));
          mainPanelList.add(new MainPanel(R.mipmap.kqgl02,"日志上报"));
          mainPanelList.add(new MainPanel(R.mipmap.kqgl03,"任务管理"));
          mainPanelList.add(new MainPanel(R.mipmap.kqgl04,"学习培训"));
          mainPanelList.add(new MainPanel(R.mipmap.kqgl05,"绩效考核"));
          mainPanelList.add(new MainPanel(R.mipmap.kqgl06,"投诉举报"));
          mainPanelList.add(new MainPanel(R.mipmap.kqgl07,"督查督办"));

          recyclerView.setAdapter(new MainAdapter(mainPanelList));



        return view;
    }


}
