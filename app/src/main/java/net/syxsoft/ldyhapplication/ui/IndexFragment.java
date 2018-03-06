package net.syxsoft.ldyhapplication.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import net.syxsoft.ldyhapplication.Adapter.MainAdapter;
import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.BeanBase;
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

        RequestBase requestBase=new RequestBase();

        requestBase.doRequest(null,"http://192.168.1.109/mian.json","GET",new BeanCallback(){

            @Override
            public void onSuccess(final String json) {
                getHoldingActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Log.d("成功",json);

                        mainPanelList=GsonUtil.GsonToList(json,MainPanel.class);
                        recyclerView.setAdapter(new MainAdapter(mainPanelList));
                    }

                });
            }

            @Override
            public void onError(String msg) {
                getHoldingActivity().runOnUiThread(new Runnable() {
                   @Override
                    public void run() {
                       //Log.d("失败","失败");
                    }
                });
            }
        });

        return view;
    }
}
