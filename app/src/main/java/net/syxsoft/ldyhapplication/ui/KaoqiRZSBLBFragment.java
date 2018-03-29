package net.syxsoft.ldyhapplication.ui;


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
import com.bigkoo.pickerview.TimePickerView;

import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.ResultBean;
import net.syxsoft.ldyhapplication.bean.SyskeyvalueBean;
import net.syxsoft.ldyhapplication.callback.LoadCallBack;
import net.syxsoft.ldyhapplication.utils.MyAlert;
import net.syxsoft.ldyhapplication.utils.OkHttpManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static net.syxsoft.ldyhapplication.utils.DateUtils.getSimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class KaoqiRZSBLBFragment extends BaseFragment {
    //办公类型
    private SyskeyvalueBean syskeyvalueBean;
    private String bflxTypeValueId="0";  //办公类型

    private  String glrwTypeValueId="0"; //关联任务

    @BindView(R.id.rzsb_bl_gznr)
    EditText rzsb_bl_gznr;

    @BindView(R.id.rzsb_bl_bglx)
    TextView rzsb_bl_bglx;

    @BindView(R.id.rzsb_bl_bgdd)
    EditText rzsb_bl_bgdd;

    @BindView(R.id.rzsb_bl_glrw)
    TextView rzsb_bl_glrw;

    @BindView(R.id.rzsb_bl_xzrq)
    TextView rzsb_bl_xzrq;

    //选择办公类型
    @OnClick(R.id.rzsb_bl_bglx)
    public void OnBglxSelectBtnClicked() {

        //条件选择器
        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置

                if (syskeyvalueBean != null && syskeyvalueBean.getSuccessInfo().size() > 0) {
                    String tx = syskeyvalueBean.getSuccessInfo().get(options1).getText();
                    bflxTypeValueId = syskeyvalueBean.getSuccessInfo().get(options1).getValue();
                    rzsb_bl_bglx.setText(tx);
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
                                Toast.makeText(getHoldingActivity(), syskeyvalueBean.getErrorMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        public void onEror(Call call, int statusCode, Exception e) {
                        }
                    });

        } else {

            pvOptions.setPicker(getPickDateItemText(syskeyvalueBean.getSuccessInfo()));
            pvOptions.show();
        }
    }

    //选择关联任务
    @OnClick(R.id.rzsb_bl_glrw)
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
    //选择日期
    @OnClick(R.id.rzsb_bl_xzrq)
    public void OnXzrqSelectBtnClicked() {

        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                rzsb_bl_xzrq.setText(getSimpleDateFormat(date, "yyyy-MM-dd HH:mm"));
            }
        })
                .setSubmitText("确定")
                .setCancelText("取消")
                .setTitleText("请选择时间")
                .setType(new boolean[]{true, true, true, true, true, false})
                .setDate(Calendar.getInstance())
                .build();

        pvTime.show();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getHoldingActivity().getMenuInflater().inflate(R.menu.menu_submit, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        if (!getHoldingActivity().isNetWorkAvailable()) {
            Toast.makeText(getHoldingActivity(), "没有网络连接，请稍后重试", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (item.getItemId() == R.id.action_settings) {
            //提交信息
            if (rzsb_bl_gznr.getText() == null || rzsb_bl_gznr.getText().toString().length() <= 0) {
                new MyAlert("", "请填写工作内容", true, false, getContext());
            }
            else if (bflxTypeValueId == null || bflxTypeValueId.toString().length() <= 0) {
                new MyAlert("", "请选择办公类型", true, false, getContext());
            }
            else if (rzsb_bl_bgdd.getText() == null || rzsb_bl_bgdd.getText().toString().length() <= 0) {
                new MyAlert("", "请填写办公地点", true, false, getContext());
            }else if (rzsb_bl_xzrq.getText() == null || rzsb_bl_xzrq.getText().toString().length() <= 0) {
                new MyAlert("", "请选择日期", true, false, getContext());
            }else {
                Map<String, String> params = new HashMap<String, String>();
                params.put("personId", getHoldingActivity().getUserAccount().getUserid());
                params.put("type", bflxTypeValueId);
                params.put("address", rzsb_bl_bgdd.getText().toString());
                params.put("taskId", glrwTypeValueId);
                params.put("time", rzsb_bl_xzrq.getText().toString());
                params.put("content", rzsb_bl_gznr.getText().toString());
                OkHttpManager.getInstance().postRequest(getRootApiUrl() + "/api/workdaily/rewrite",
                        new LoadCallBack<ResultBean>(getContext()) {
                            @Override
                            public void onSuccess(Call call, Response response, ResultBean resultBean) {

                                if (getHoldingActivity()!=null) {
                                    if (resultBean.getRequestCode() != 200) {
                                        Toast.makeText(getHoldingActivity(), resultBean.getErrorMessage().toString(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getHoldingActivity(), resultBean.getErrorMessage().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                                bflxTypeValueId="0";
                            }
                            @Override
                            public void onEror(Call call, int statusCode, Exception e) {
                                bflxTypeValueId="0";
                                Toast.makeText(getHoldingActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }, params);

            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zrlb_rzbl;
    }

    @Override
    protected void initActionBar(Toolbar actionBar) {
        super.initActionBar(actionBar);

        //设置标题
        TextView textView = actionBar.findViewById(R.id.toolbar_title);
        textView.setText("日志补录");

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
