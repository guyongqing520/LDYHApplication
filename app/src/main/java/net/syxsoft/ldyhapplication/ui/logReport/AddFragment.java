package net.syxsoft.ldyhapplication.ui.logReport;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends BaseFragment {

    //办公类型
    private SyskeyvalueBean syskeyvalueBean;
    private String bflxTypeValueId = "0";  //办公类型

    private String glrwTypeValueId = "1"; //关联任务

    @BindView(R.id.rzsb_bl_gznr)
    EditText rzsb_gznr;

    @BindView(R.id.rzsb_bglx)
    TextView rzsb_bglx;

    @BindView(R.id.rzsb_bgdd)
    EditText rzsb_bgdd;

    @BindView(R.id.rzsb_glrw)
    TextView rzsb_glrw;

    //选择办公类型
    @OnClick(R.id.rzsb_bglx)
    public void OnBglxSelectBtnClicked() {

        //条件选择器
        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置

                if (syskeyvalueBean != null && syskeyvalueBean.getSuccessInfo().size() > 0) {
                    String tx = syskeyvalueBean.getSuccessInfo().get(options1).getText();
                    bflxTypeValueId = syskeyvalueBean.getSuccessInfo().get(options1).getValue();
                    rzsb_bglx.setText(tx);
                }
            }
        })
                .setTitleText("办公类型")
                .setSubmitText("确定")
                .setCancelText("取消").build();

        if (syskeyvalueBean == null || syskeyvalueBean.getSuccessInfo().size() == 0) {
            OkHttpManager.getInstance().getRequest(getRootApiUrl() + "/api/syskeyvalue/list/OfficeType",
                    new LoadCallBack<SyskeyvalueBean>(getContext()) {

                        @Override
                        public void onSuccess(Call call, Response response, SyskeyvalueBean syskeyvalueBean1) {

                            if (syskeyvalueBean1 != null && syskeyvalueBean1.getRequestCode() == 200) {
                                syskeyvalueBean = syskeyvalueBean1;
                                pvOptions.setPicker(getPickDateItemText(syskeyvalueBean.getSuccessInfo()));
                                pvOptions.show();

                            } else {
                                MyToast.getInstance().show( syskeyvalueBean.getErrorMessage().toString(),getContext());
                            }
                        }

                    });

        } else {

            pvOptions.setPicker(getPickDateItemText(syskeyvalueBean.getSuccessInfo()));
            pvOptions.show();
        }
    }

    //选择关联任务
    @OnClick(R.id.rzsb_glrw)
    public void OnGlrwSelectBtnClicked() {
        Toast.makeText(getHoldingActivity(), "没有关联任务！", Toast.LENGTH_SHORT).show();
       /* //条件选择器
        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置

                if (syskeyvalueBean != null && syskeyvalueBean.getSuccessInfo().size() > 0) {
                    String tx = syskeyvalueBean.getSuccessInfo().get(options1).getText();
                    glrwTypeValueId = syskeyvalueBean.getSuccessInfo().get(options1).getValue();
                    rzsb_glrw.setText(tx);
                }
            }
        })
                .setTitleText("关联任务")
                .setSubmitText("确定")
                .setCancelText("取消").build();

        if (syskeyvalueBean == null || syskeyvalueBean.getSuccessInfo().size() == 0) {
            OkHttpManager.getInstance().getRequest(getRootApiUrl() + "/api/syskeyvalue/list/OfficeType",
                    new LoadCallBack<SyskeyvalueBean>(getContext()) {

                        @Override
                        public void onSuccess(Call call, Response response, SyskeyvalueBean syskeyvalueBean1) {

                            if (syskeyvalueBean1 != null && syskeyvalueBean1.getRequestCode() == 200) {
                                syskeyvalueBean = syskeyvalueBean1;
                                pvOptions.setPicker(getPickDateItemText(syskeyvalueBean.getSuccessInfo()));
                                pvOptions.show();

                            } else {
                                Toast.makeText(getHoldingActivity(), syskeyvalueBean.getErrorMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        public void onEror(Call call, int statusCode, Exception e) {
                        }
                    });

        } else {

            pvOptions.setPicker(getPickDateItemText(syskeyvalueBean.getSuccessInfo()));
            pvOptions.show();
        }*/
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_rzsb_rztb;
    }

    @Override
    public void initActionBar(Toolbar actionBar) {
        super.initActionBar(actionBar);

        //设置标题
        TextView textView = actionBar.findViewById(R.id.toolbar_title);
        textView.setText("日志填报");

        //启用返回导航
        actionBar.setNavigationIcon(R.mipmap.title_bar_back);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getHoldingActivity().getMenuInflater().inflate(R.menu.menu_submit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.action_settings) {
            //提交信息
            if (rzsb_gznr.getText() == null || rzsb_gznr.getText().toString().length() <= 0) {
                MyAlert.getInstance().show("", "请填写工作内容", true, false, getContext());
            } else if (bflxTypeValueId == null || bflxTypeValueId.toString().length() <= 0) {
                MyAlert.getInstance().show("", "请选择办公类型", true, false, getContext());
            } else if (rzsb_bgdd.getText() == null || rzsb_bgdd.getText().toString().length() <= 0) {
                MyAlert.getInstance().show("", "请填写办公地点", true, false, getContext());
            } else {
                Map<String, String> params = new HashMap<String, String>();
                params.put("personId", getHoldingActivity().getUserAccount().getUserid());
                params.put("type", bflxTypeValueId);
                params.put("address", rzsb_bgdd.getText().toString());
                params.put("taskId", glrwTypeValueId);
                params.put("content", rzsb_gznr.getText().toString());
                OkHttpManager.getInstance().postRequest(getRootApiUrl() + "/api/workdaily/write/",
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //去掉底部导航
        BottomNavigationView navigation = getHoldingActivity().findViewById(R.id.navigation);
        navigation.setVisibility(View.GONE);
        return super.onCreateView(inflater, container, savedInstanceState);
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
}
