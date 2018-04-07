package net.syxsoft.ldyhapplication.ui.complaint;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;

import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.ResultBean;
import net.syxsoft.ldyhapplication.bean.SyskeyvalueBean;
import net.syxsoft.ldyhapplication.callback.LoadCallBack;
import net.syxsoft.ldyhapplication.ui.AppBase.BaseFragment;
import net.syxsoft.ldyhapplication.utils.MyAlert;
import net.syxsoft.ldyhapplication.utils.MyToast;
import net.syxsoft.ldyhapplication.utils.OkHttpManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;


public class AddFragment extends BaseFragment {

    @BindView(R.id.tousu_type_dw)
    TextView tousu_type_dw;

    @BindView(R.id.tousu_type_dw_bt)
    TextView tousu_type_dw_bt;

    @BindView(R.id.tousu_type_gr)
    TextView tousu_type_gr;

    @BindView(R.id.tousu_type_gr_bt)
    TextView tousu_type_gr_bt;

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
        isDw=false;
    }
    @OnClick(R.id.tousu_type_dw)
    public void onDwClicked(){
        tousu_type_dw.setTextColor(navblue);
        tousu_type_dw_bt.setBackgroundColor(navblue);
        tousu_type_gr.setTextColor(word999);
        tousu_type_gr_bt.setBackgroundColor(grey);
        tousu_btsr.setText("被投诉单位");
        isDw=true;
    }


    //表单内容

    public boolean isDw=false;  //默认进来是个人投诉

    //被投诉人/单位
    @BindView(R.id.tousu_btsr)
    TextView tousu_btsr;
    private  int tousu_user_id=0;
    SyskeyvalueBean user_syskeyvalueBean;
    //干部问题
    @BindView(R.id.tousu_gbwt)
    TextView tousu_gbwt;
    private  int tousu_gbwt_id=0;
    SyskeyvalueBean gbwt_syskeyvalueBean;

    //匿名投诉
    @BindView(R.id.tousu_nmts)
    TextView tousu_nmts;
    private  int tousu_nmts_type=0;
    SyskeyvalueBean nmts_syskeyvalueBean;

    //投诉内容
    @BindView(R.id.tousu_content)
    EditText tousu_content;

    public  String getDwName(){
        if(isDw){
            return  "投诉单位";
        }else
            return  "投诉人";
    }
    //投诉人/单位
    @OnClick
    public  void  OnTsrClicked() {
        String url = "/api/syskeyvalue/list/OfficeType";
        if (isDw) {
            url = "/api/syskeyvalue/list/OfficeType";
        }
        //条件选择器
        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if (user_syskeyvalueBean != null && user_syskeyvalueBean.getSuccessInfo().size() > 0) {
                    String tx = user_syskeyvalueBean.getSuccessInfo().get(options1).getText();
                    tousu_gbwt_id = Integer.valueOf(user_syskeyvalueBean.getSuccessInfo().get(options1).getValue());
                    tousu_gbwt.setText(tx);
                }
            }
        })
                .setTitleText(getDwName())
                .setSubmitText("确定")
                .setCancelText("取消").build();

        if (user_syskeyvalueBean == null || user_syskeyvalueBean.getSuccessInfo().size() == 0) {
            OkHttpManager.getInstance().getRequest(getRootApiUrl() + url,
                    new LoadCallBack<SyskeyvalueBean>(getContext()) {
                        @Override
                        public void onSuccess(Call call, Response response, SyskeyvalueBean syskeyvalueBean1) {
                            if (syskeyvalueBean1 != null && syskeyvalueBean1.getRequestCode() == 200) {
                                user_syskeyvalueBean = syskeyvalueBean1;
                                pvOptions.setPicker(getPickDateItemText(user_syskeyvalueBean.getSuccessInfo()));
                                pvOptions.show();
                            } else {
                                MyToast.getInstance().show(user_syskeyvalueBean.getErrorMessage().toString(), getContext());
                            }
                        }
                    });
        } else {

            pvOptions.setPicker(getPickDateItemText(user_syskeyvalueBean.getSuccessInfo()));
            pvOptions.show();
        }

    }


    //干部问题
    @OnClick
    public  void  OnGbwtClicked(){
        //条件选择器
        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if (gbwt_syskeyvalueBean != null && gbwt_syskeyvalueBean.getSuccessInfo().size() > 0) {
                    String tx = gbwt_syskeyvalueBean.getSuccessInfo().get(options1).getText();
                    tousu_gbwt_id = Integer.valueOf( gbwt_syskeyvalueBean.getSuccessInfo().get(options1).getValue());
                    tousu_gbwt.setText(tx);
                }
            }
        })
                .setTitleText("干部问题")
                .setSubmitText("确定")
                .setCancelText("取消").build();

        if (gbwt_syskeyvalueBean == null || gbwt_syskeyvalueBean.getSuccessInfo().size() == 0) {
            OkHttpManager.getInstance().getRequest(getRootApiUrl() + "/api/syskeyvalue/list/OfficeType",
                    new LoadCallBack<SyskeyvalueBean>(getContext()) {
                        @Override
                        public void onSuccess(Call call, Response response, SyskeyvalueBean syskeyvalueBean1) {
                            if (syskeyvalueBean1 != null && syskeyvalueBean1.getRequestCode() == 200) {
                                gbwt_syskeyvalueBean = syskeyvalueBean1;
                                pvOptions.setPicker(getPickDateItemText(gbwt_syskeyvalueBean.getSuccessInfo()));
                                pvOptions.show();
                            } else {
                                MyToast.getInstance().show( gbwt_syskeyvalueBean.getErrorMessage().toString(),getContext());
                            }
                        }
                    });
        } else {

            pvOptions.setPicker(getPickDateItemText(gbwt_syskeyvalueBean.getSuccessInfo()));
            pvOptions.show();
        }

    }

    //选择是否匿名投诉
    @OnClick(R.id.tousu_nmts)
    public void OnNmtsSelectBtnClicked() {
        //条件选择器
        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if (nmts_syskeyvalueBean != null && nmts_syskeyvalueBean.getSuccessInfo().size() > 0) {
                    String tx = nmts_syskeyvalueBean.getSuccessInfo().get(options1).getText();
                    tousu_nmts_type =Integer.valueOf( nmts_syskeyvalueBean.getSuccessInfo().get(options1).getValue());
                    tousu_nmts.setText(tx);
                }
            }
        })
                .setTitleText("是否匿名")
                .setSubmitText("确定")
                .setCancelText("取消").build();

        if (nmts_syskeyvalueBean == null || nmts_syskeyvalueBean.getSuccessInfo().size() == 0) {
            List<String> mlist = new ArrayList<>();
            mlist.add(0,"否");
            mlist.add(1,"是");
            pvOptions.setPicker(mlist);
            pvOptions.show();
        } else {
            pvOptions.setPicker(getPickDateItemText(nmts_syskeyvalueBean.getSuccessInfo()));
            pvOptions.show();
        }
    }

    private List<String> getPickDateItemText(List<SyskeyvalueBean.SuccessInfoBean> list) {

        List<String> mlist = new ArrayList<>();

        if (list != null && list.size() > 0) {
            for (SyskeyvalueBean.SuccessInfoBean item : list) {
                mlist.add(item.getText());
            }
        }

        return mlist;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.action_settings) {
            //提交信息
            if (tousu_content.getText() == null || tousu_content.getText().toString().length() <= 0) {
                MyAlert.getInstance().show("", "请填写投诉内容", true, false, getContext());
            }else if(tousu_user_id >0) {
                MyAlert.getInstance().show("", "请选择"+getDwName(), true, false, getContext());
            }else if(tousu_gbwt_id >0) {
                MyAlert.getInstance().show("", "请选择干部问题", true, false, getContext());
            }else if(tousu_nmts_type >0) {
                MyAlert.getInstance().show("", "请选择是否匿名投诉", true, false, getContext());
            }
            else {

                Map<String, String> params = new HashMap<String, String>();
                params.put("personId", getHoldingActivity().getUserAccount().getUserid());
                params.put("content", tousu_content.getText().toString());
                params.put("jsuser",String.valueOf(tousu_user_id));
                params.put("jsuser",String.valueOf(tousu_gbwt_id));
                params.put("jsuser",String.valueOf(tousu_nmts_type));
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
