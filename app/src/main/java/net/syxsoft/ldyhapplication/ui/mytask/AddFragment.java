package net.syxsoft.ldyhapplication.ui.mytask;

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
import com.bigkoo.pickerview.TimePickerView;

import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.ResultBean;
import net.syxsoft.ldyhapplication.bean.SyskeyvalueBean;
import net.syxsoft.ldyhapplication.callback.LoadCallBack;
import net.syxsoft.ldyhapplication.ui.AppBase.BaseFragment;
import net.syxsoft.ldyhapplication.utils.MyAlert;
import net.syxsoft.ldyhapplication.utils.MyToast;
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


public class AddFragment extends BaseFragment {

    //任务分类
    private SyskeyvalueBean rwfl_syskeyvalueBean;
    //任务分类
    private String addtask_rwfl_type = "";
    @BindView(R.id.addtask_rwfl)
    private TextView addtask_rwfl;

    //紧急程度
    private SyskeyvalueBean jjcd_syskeyvalueBean;
    private String addtask_jjcd_type = "";
    @BindView(R.id.addtask_jjcd)
    private TextView addtask_jjcd;

    //重要程度
    private SyskeyvalueBean zycd_syskeyvalueBean;
    private String addtask_zycd_type = "";
    @BindView(R.id.addtask_zycd)
    private TextView addtask_zycd;
    //工种
    private SyskeyvalueBean gz_syskeyvalueBean;
    private String addtask_gz_type = "";
    @BindView(R.id.addtask_gz)
    private TextView addtask_gz;


    //开始时间
    private String addtask_starttime_type = "";
    @BindView(R.id.addtask_starttime)
    private TextView addtask_starttime;

    //结束时间
    private String addtask_endtime_type = "";
    @BindView(R.id.addtask_endtime)
    private TextView addtask_endtime;

    //创建者
    private SyskeyvalueBean create_syskeyvalueBean;
    private String addtask_create_user_type = "";
    @BindView(R.id.addtask_create_user)
    private TextView addtask_create_user;
    //执行者
    private SyskeyvalueBean execute_syskeyvalueBean;
    private String addtask_execute_user_type = "";
    @BindView(R.id.addtask_execute_user)
    private TextView addtask_execute_user;
    //参与者
    private SyskeyvalueBean partake_syskeyvalueBean;
    private String addtask_partake_user_type = "";
    @BindView(R.id.addtask_partake_user)
    private TextView addtask_partake_user;

    //是否抄送
    private SyskeyvalueBean cc_syskeyvalueBean;
    private String addtask_CC_type = "";
    @BindView(R.id.addtask_CC)
    private TextView addtask_CC;

    //目标任务
    @BindView(R.id.addtask_mbrw)
    private EditText addtask_mbrw;
    //任务标题
    @BindView(R.id.addtask_title)
    private EditText addtask_title;
    //任务描述
    @BindView(R.id.addtask_content)
    private EditText addtask_content;
    //任务来源
    @BindView(R.id.addtask_rwly)
    private EditText addtask_rwly;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_kaoqin_renwu_new;
    }
    //选择任务开始日期
    @OnClick(R.id.addtask_starttime)
    public void OnStatrTimeSelectBtnClicked() {

        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                addtask_starttime.setText(getSimpleDateFormat(date, "yyyy-MM-dd HH:mm:ss"));
                addtask_starttime_type=addtask_starttime.getText().toString();
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
    //选择任务结束日期
    @OnClick(R.id.addtask_endtime)
    public void OnEndTimeSelectBtnClicked() {

        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                addtask_endtime.setText(getSimpleDateFormat(date, "yyyy-MM-dd HH:mm:ss"));
                addtask_endtime_type=addtask_endtime.getText().toString();
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
    //选择任务分类
    @OnClick(R.id.addtask_rwfl)
    public void OnRwflSelectBtnClicked() {
        //条件选择器
        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if (rwfl_syskeyvalueBean != null && rwfl_syskeyvalueBean.getSuccessInfo().size() > 0) {
                    String tx = rwfl_syskeyvalueBean.getSuccessInfo().get(options1).getText();
                    addtask_rwfl_type = rwfl_syskeyvalueBean.getSuccessInfo().get(options1).getValue();
                    addtask_rwfl.setText(tx);
                }
            }
        })
                .setTitleText("任务分类")
                .setSubmitText("确定")
                .setCancelText("取消").build();

        if (rwfl_syskeyvalueBean == null || rwfl_syskeyvalueBean.getSuccessInfo().size() == 0) {
            OkHttpManager.getInstance().getRequest(getRootApiUrl() + "/api/syskeyvalue/list/OfficeType",
                    new LoadCallBack<SyskeyvalueBean>(getContext()) {
                        @Override
                        public void onSuccess(Call call, Response response, SyskeyvalueBean syskeyvalueBean1) {
                            if (syskeyvalueBean1 != null && syskeyvalueBean1.getRequestCode() == 200) {
                                rwfl_syskeyvalueBean = syskeyvalueBean1;
                                pvOptions.setPicker(getPickDateItemText(rwfl_syskeyvalueBean.getSuccessInfo()));
                                pvOptions.show();
                            } else {
                                MyToast.getInstance().show( rwfl_syskeyvalueBean.getErrorMessage().toString(),getContext());
                            }
                        }
                    });
        } else {

            pvOptions.setPicker(getPickDateItemText(rwfl_syskeyvalueBean.getSuccessInfo()));
            pvOptions.show();
        }
    }

    //选择紧急程度
    @OnClick(R.id.addtask_jjcd)
    public void OnJjcdSelectBtnClicked() {
        //条件选择器
        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if (jjcd_syskeyvalueBean != null && jjcd_syskeyvalueBean.getSuccessInfo().size() > 0) {
                    String tx = jjcd_syskeyvalueBean.getSuccessInfo().get(options1).getText();
                    addtask_jjcd_type = jjcd_syskeyvalueBean.getSuccessInfo().get(options1).getValue();
                    addtask_jjcd.setText(tx);
                }
            }
        })
                .setTitleText("紧急程度")
                .setSubmitText("确定")
                .setCancelText("取消").build();

        if (jjcd_syskeyvalueBean == null || jjcd_syskeyvalueBean.getSuccessInfo().size() == 0) {
            OkHttpManager.getInstance().getRequest(getRootApiUrl() + "/api/syskeyvalue/list/OfficeType",
                    new LoadCallBack<SyskeyvalueBean>(getContext()) {
                        @Override
                        public void onSuccess(Call call, Response response, SyskeyvalueBean syskeyvalueBean1) {
                            if (syskeyvalueBean1 != null && syskeyvalueBean1.getRequestCode() == 200) {
                                jjcd_syskeyvalueBean = syskeyvalueBean1;
                                pvOptions.setPicker(getPickDateItemText(jjcd_syskeyvalueBean.getSuccessInfo()));
                                pvOptions.show();
                            } else {
                                MyToast.getInstance().show( jjcd_syskeyvalueBean.getErrorMessage().toString(),getContext());
                            }
                        }
                    });
        } else {

            pvOptions.setPicker(getPickDateItemText(jjcd_syskeyvalueBean.getSuccessInfo()));
            pvOptions.show();
        }
    }

    //选择重要程度
    @OnClick(R.id.addtask_zycd)
    public void OnZycdSelectBtnClicked() {
        //条件选择器
        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if (zycd_syskeyvalueBean != null && zycd_syskeyvalueBean.getSuccessInfo().size() > 0) {
                    String tx = zycd_syskeyvalueBean.getSuccessInfo().get(options1).getText();
                    addtask_zycd_type = zycd_syskeyvalueBean.getSuccessInfo().get(options1).getValue();
                    addtask_zycd.setText(tx);
                }
            }
        })
                .setTitleText("重要程度")
                .setSubmitText("确定")
                .setCancelText("取消").build();

        if (zycd_syskeyvalueBean == null || zycd_syskeyvalueBean.getSuccessInfo().size() == 0) {
            OkHttpManager.getInstance().getRequest(getRootApiUrl() + "/api/syskeyvalue/list/OfficeType",
                    new LoadCallBack<SyskeyvalueBean>(getContext()) {
                        @Override
                        public void onSuccess(Call call, Response response, SyskeyvalueBean syskeyvalueBean1) {
                            if (syskeyvalueBean1 != null && syskeyvalueBean1.getRequestCode() == 200) {
                                zycd_syskeyvalueBean = syskeyvalueBean1;
                                pvOptions.setPicker(getPickDateItemText(zycd_syskeyvalueBean.getSuccessInfo()));
                                pvOptions.show();
                            } else {
                                MyToast.getInstance().show( zycd_syskeyvalueBean.getErrorMessage().toString(),getContext());
                            }
                        }
                    });
        } else {

            pvOptions.setPicker(getPickDateItemText(zycd_syskeyvalueBean.getSuccessInfo()));
            pvOptions.show();
        }
    }
    //选择工种
    @OnClick(R.id.addtask_gz)
    public void OnGzSelectBtnClicked() {
        //条件选择器
        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if (gz_syskeyvalueBean != null && gz_syskeyvalueBean.getSuccessInfo().size() > 0) {
                    String tx = gz_syskeyvalueBean.getSuccessInfo().get(options1).getText();
                    addtask_gz_type = gz_syskeyvalueBean.getSuccessInfo().get(options1).getValue();
                    addtask_gz.setText(tx);
                }
            }
        })
                .setTitleText("工种")
                .setSubmitText("确定")
                .setCancelText("取消").build();

        if (gz_syskeyvalueBean == null || gz_syskeyvalueBean.getSuccessInfo().size() == 0) {
            OkHttpManager.getInstance().getRequest(getRootApiUrl() + "/api/syskeyvalue/list/OfficeType",
                    new LoadCallBack<SyskeyvalueBean>(getContext()) {
                        @Override
                        public void onSuccess(Call call, Response response, SyskeyvalueBean syskeyvalueBean1) {
                            if (syskeyvalueBean1 != null && syskeyvalueBean1.getRequestCode() == 200) {
                                gz_syskeyvalueBean = syskeyvalueBean1;
                                pvOptions.setPicker(getPickDateItemText(gz_syskeyvalueBean.getSuccessInfo()));
                                pvOptions.show();
                            } else {
                                MyToast.getInstance().show( gz_syskeyvalueBean.getErrorMessage().toString(),getContext());
                            }
                        }
                    });
        } else {

            pvOptions.setPicker(getPickDateItemText(gz_syskeyvalueBean.getSuccessInfo()));
            pvOptions.show();
        }
    }

    //选择创建者
    @OnClick(R.id.addtask_create_user)
    public void OnCreateuserSelectBtnClicked() {
        //条件选择器
        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if (create_syskeyvalueBean != null && create_syskeyvalueBean.getSuccessInfo().size() > 0) {
                    String tx = create_syskeyvalueBean.getSuccessInfo().get(options1).getText();
                    addtask_create_user_type = create_syskeyvalueBean.getSuccessInfo().get(options1).getValue();
                    addtask_create_user.setText(tx);
                }
            }
        })
                .setTitleText("创建者")
                .setSubmitText("确定")
                .setCancelText("取消").build();

        if (create_syskeyvalueBean == null || create_syskeyvalueBean.getSuccessInfo().size() == 0) {
            OkHttpManager.getInstance().getRequest(getRootApiUrl() + "/api/syskeyvalue/list/OfficeType",
                    new LoadCallBack<SyskeyvalueBean>(getContext()) {
                        @Override
                        public void onSuccess(Call call, Response response, SyskeyvalueBean syskeyvalueBean1) {
                            if (syskeyvalueBean1 != null && syskeyvalueBean1.getRequestCode() == 200) {
                                create_syskeyvalueBean = syskeyvalueBean1;
                                pvOptions.setPicker(getPickDateItemText(create_syskeyvalueBean.getSuccessInfo()));
                                pvOptions.show();
                            } else {
                                MyToast.getInstance().show( create_syskeyvalueBean.getErrorMessage().toString(),getContext());
                            }
                        }
                    });
        } else {

            pvOptions.setPicker(getPickDateItemText(create_syskeyvalueBean.getSuccessInfo()));
            pvOptions.show();
        }
    }


    //选择执行者
    @OnClick(R.id.addtask_execute_user)
    public void OnExecuteuserSelectBtnClicked() {
        //条件选择器
        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if (execute_syskeyvalueBean != null && execute_syskeyvalueBean.getSuccessInfo().size() > 0) {
                    String tx = execute_syskeyvalueBean.getSuccessInfo().get(options1).getText();
                    addtask_execute_user_type = execute_syskeyvalueBean.getSuccessInfo().get(options1).getValue();
                    addtask_execute_user.setText(tx);
                }
            }
        })
                .setTitleText("执行者")
                .setSubmitText("确定")
                .setCancelText("取消").build();

        if (execute_syskeyvalueBean == null || execute_syskeyvalueBean.getSuccessInfo().size() == 0) {
            OkHttpManager.getInstance().getRequest(getRootApiUrl() + "/api/syskeyvalue/list/OfficeType",
                    new LoadCallBack<SyskeyvalueBean>(getContext()) {
                        @Override
                        public void onSuccess(Call call, Response response, SyskeyvalueBean syskeyvalueBean1) {
                            if (syskeyvalueBean1 != null && syskeyvalueBean1.getRequestCode() == 200) {
                                execute_syskeyvalueBean = syskeyvalueBean1;
                                pvOptions.setPicker(getPickDateItemText(execute_syskeyvalueBean.getSuccessInfo()));
                                pvOptions.show();
                            } else {
                                MyToast.getInstance().show( execute_syskeyvalueBean.getErrorMessage().toString(),getContext());
                            }
                        }
                    });
        } else {

            pvOptions.setPicker(getPickDateItemText(execute_syskeyvalueBean.getSuccessInfo()));
            pvOptions.show();
        }
    }


    //选择参与者
    @OnClick(R.id.addtask_partake_user)
    public void OnPartaskuserSelectBtnClicked() {
        //条件选择器
        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if (partake_syskeyvalueBean != null && partake_syskeyvalueBean.getSuccessInfo().size() > 0) {
                    String tx = partake_syskeyvalueBean.getSuccessInfo().get(options1).getText();
                    addtask_partake_user_type = partake_syskeyvalueBean.getSuccessInfo().get(options1).getValue();
                    addtask_partake_user.setText(tx);
                }
            }
        })
                .setTitleText("参与者")
                .setSubmitText("确定")
                .setCancelText("取消").build();

        if (partake_syskeyvalueBean == null || partake_syskeyvalueBean.getSuccessInfo().size() == 0) {
            OkHttpManager.getInstance().getRequest(getRootApiUrl() + "/api/syskeyvalue/list/OfficeType",
                    new LoadCallBack<SyskeyvalueBean>(getContext()) {
                        @Override
                        public void onSuccess(Call call, Response response, SyskeyvalueBean syskeyvalueBean1) {
                            if (syskeyvalueBean1 != null && syskeyvalueBean1.getRequestCode() == 200) {
                                partake_syskeyvalueBean = syskeyvalueBean1;
                                pvOptions.setPicker(getPickDateItemText(partake_syskeyvalueBean.getSuccessInfo()));
                                pvOptions.show();
                            } else {
                                MyToast.getInstance().show( partake_syskeyvalueBean.getErrorMessage().toString(),getContext());
                            }
                        }
                    });
        } else {

            pvOptions.setPicker(getPickDateItemText(partake_syskeyvalueBean.getSuccessInfo()));
            pvOptions.show();
        }
    }

    //选择是否抄送
    @OnClick(R.id.addtask_CC)
    public void OnCCSelectBtnClicked() {
        //条件选择器
        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if (cc_syskeyvalueBean != null && cc_syskeyvalueBean.getSuccessInfo().size() > 0) {
                    String tx = cc_syskeyvalueBean.getSuccessInfo().get(options1).getText();
                    addtask_CC_type = cc_syskeyvalueBean.getSuccessInfo().get(options1).getValue();
                    addtask_CC.setText(tx);
                }
            }
        })
                .setTitleText("是否抄送")
                .setSubmitText("确定")
                .setCancelText("取消").build();

        if (cc_syskeyvalueBean == null || cc_syskeyvalueBean.getSuccessInfo().size() == 0) {
            List<String> mlist = new ArrayList<>();
            mlist.add(0,"否");
            mlist.add(1,"是");
            pvOptions.setPicker(mlist);
            pvOptions.show();
        } else {
            pvOptions.setPicker(getPickDateItemText(cc_syskeyvalueBean.getSuccessInfo()));
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
    public void initActionBar(Toolbar actionBar) {
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
        BottomNavigationView navigation = getHoldingActivity().findViewById(R.id.navigation);
        navigation.setVisibility(View.GONE);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.action_settings) {
            //提交信息
            if (addtask_title.getText() == null || addtask_title.getText().toString().length() <= 0) {
                MyAlert.getInstance().show("", "请填写任务标题", true, false, getContext());
            } else if (addtask_content == null || addtask_content.toString().length() <= 0) {
                MyAlert.getInstance().show("", "请填写任务描述", true, false, getContext());
            } else if (addtask_mbrw.getText() == null || addtask_mbrw.getText().toString().length() <= 0) {
                MyAlert.getInstance().show("", "请填写目标任务", true, false, getContext());
            }else if (addtask_rwly.getText() == null || addtask_rwly.getText().toString().length() <= 0) {
                MyAlert.getInstance().show("", "请填写任务来源", true, false, getContext());
            } else if (addtask_rwfl_type == null || addtask_rwfl_type.toString().length() <= 0) {
                MyAlert.getInstance().show("", "请选择任务分类", true, false, getContext());
            }else if (addtask_jjcd_type == null || addtask_jjcd_type.toString().length() <= 0) {
                MyAlert.getInstance().show("", "请选择紧急程度", true, false, getContext());
            }else if (addtask_zycd_type == null || addtask_zycd_type.toString().length() <= 0) {
                MyAlert.getInstance().show("", "请选择重要程度", true, false, getContext());
            }else if (addtask_gz_type == null || addtask_gz_type.toString().length() <= 0) {
                MyAlert.getInstance().show("", "请选择工种", true, false, getContext());
            }else if (addtask_starttime_type == null || addtask_starttime_type.toString().length() <= 0) {
                MyAlert.getInstance().show("", "请选择任务开始时间", true, false, getContext());
            }else if (addtask_endtime_type == null || addtask_endtime_type.toString().length() <= 0) {
                MyAlert.getInstance().show("", "请选择任务结束时间", true, false, getContext());
            }else if (addtask_create_user_type == null || addtask_create_user_type.toString().length() <= 0) {
                MyAlert.getInstance().show("", "请选择创建者", true, false, getContext());
            }else if (addtask_execute_user_type == null || addtask_execute_user_type.toString().length() <= 0) {
                MyAlert.getInstance().show("", "请选择执行者", true, false, getContext());
            }else if (addtask_partake_user_type == null || addtask_partake_user_type.toString().length() <= 0) {
                MyAlert.getInstance().show("", "请选择参与者", true, false, getContext());
            }else if (addtask_execute_user_type == null || addtask_execute_user_type.toString().length() <= 0) {
                MyAlert.getInstance().show("", "请选择是否抄送", true, false, getContext());
            }else {
                Map<String, String> params = new HashMap<String, String>();
                params.put("personId", getHoldingActivity().getUserAccount().getUserid());
                params.put("title", addtask_title.getText().toString());
                params.put("content", addtask_content.getText().toString());
                params.put("mbrw", addtask_mbrw.getText().toString());
                params.put("rwly", addtask_rwly.getText().toString());
                params.put("rwfl", addtask_rwfl_type);
                params.put("jjcd", addtask_jjcd_type);
                params.put("rwfl", addtask_zycd_type);
                params.put("rwfl", addtask_gz_type);
                params.put("rwfl", addtask_starttime_type);
                params.put("rwfl", addtask_endtime_type);
                params.put("rwfl", addtask_create_user_type);
                params.put("rwfl", addtask_execute_user_type);
                params.put("rwfl", addtask_partake_user_type);
                params.put("rwfl", addtask_CC_type);
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
