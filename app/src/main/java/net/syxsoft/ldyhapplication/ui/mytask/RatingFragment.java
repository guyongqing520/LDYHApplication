package net.syxsoft.ldyhapplication.ui.mytask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.ResultBean;
import net.syxsoft.ldyhapplication.callback.LoadCallBack;
import net.syxsoft.ldyhapplication.ui.AppBase.BaseFragment;
import net.syxsoft.ldyhapplication.utils.MyAlert;
import net.syxsoft.ldyhapplication.utils.MyToast;
import net.syxsoft.ldyhapplication.utils.OkHttpManager;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;


public class RatingFragment extends BaseFragment {

    //等级评定
    @BindView(R.id.djpd_content)
    EditText djpd_content;
//很好
    @BindView(R.id.djpd_good)
    CheckBox djpd_good;
//较好
    @BindView(R.id.djpd_preferably)
CheckBox djpd_preferably;
//一般
    @BindView(R.id.djpd_commonly)
CheckBox djpd_commonly;
//很差
    @BindView(R.id.djpd_bad)
CheckBox djpd_bad;

    @OnClick(R.id.djpd_good)
    private  void  OnGoodClicked(){
        djpd_good.setChecked(true);
        djpd_preferably.setChecked(false);
        djpd_commonly.setChecked(false);
        djpd_bad.setChecked(false);
    }
    @OnClick(R.id.djpd_preferably)
    private  void  OnPreferablyClicked(){
        djpd_good.setChecked(false);
        djpd_preferably.setChecked(true);
        djpd_commonly.setChecked(false);
        djpd_bad.setChecked(false);
    }
    @OnClick(R.id.djpd_commonly)
    private  void  OnCommonlyClicked(){
        djpd_good.setChecked(false);
        djpd_preferably.setChecked(false);
        djpd_commonly.setChecked(true);
        djpd_bad.setChecked(false);
    }
    @OnClick(R.id.djpd_bad)
    private  void  OnBadClicked(){
        djpd_good.setChecked(false);
        djpd_preferably.setChecked(false);
        djpd_commonly.setChecked(false);
        djpd_bad.setChecked(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_kaoqin_renwu_djpd;
    }

    @Override
    public void initActionBar(Toolbar actionBar) {
        super.initActionBar(actionBar);

        //设置标题
        TextView textView = actionBar.findViewById(R.id.toolbar_title);
        textView.setText("等级评定");

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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.action_settings) {
            //提交信息
            if (djpd_content.getText() == null || djpd_content.getText().toString().length() <= 0) {
                MyAlert.getInstance().show("", "请填写事件描述", true, false, getContext());
            } else {
                String ckId="0";
                if(djpd_good.isChecked()){
                    ckId="1";
                }else if(djpd_preferably.isChecked()){
                    ckId="2";
                }else if(djpd_commonly.isChecked()){
                    ckId="3";
                }else {
                    ckId="4";
                }
                Map<String, String> params = new HashMap<String, String>();
                params.put("personId", getHoldingActivity().getUserAccount().getUserid());
                params.put("jd",  ckId);
                params.put("content", djpd_content.getText().toString());
                OkHttpManager.getInstance().postRequest(getRootApiUrl() + "/api/mytask/write/",
                        new LoadCallBack<ResultBean>(getContext()) {
                            @Override
                            public void onSuccess(Call call, Response response, ResultBean resultBean) {

                                if (getHoldingActivity() != null) {
                                    MyToast.getInstance().show(resultBean.getErrorMessage().toString(), getContext());
                                }
                            }
                        }, params);

            }
        }
        return super.onOptionsItemSelected(item);
    }

}
