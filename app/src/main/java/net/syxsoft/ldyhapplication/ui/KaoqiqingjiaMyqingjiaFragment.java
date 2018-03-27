package net.syxsoft.ldyhapplication.ui;


import android.content.Intent;
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
import com.lzy.imagepicker.ui.ImageGridActivity;

import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.DirectleaderBean;
import net.syxsoft.ldyhapplication.bean.PersoninfoBean;
import net.syxsoft.ldyhapplication.bean.ResultBean;
import net.syxsoft.ldyhapplication.bean.SyskeyvalueBean;
import net.syxsoft.ldyhapplication.callback.LoadCallBack;
import net.syxsoft.ldyhapplication.utils.DateUtils;
import net.syxsoft.ldyhapplication.utils.MyAlert;
import net.syxsoft.ldyhapplication.utils.OkHttpManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
public class KaoqiqingjiaMyqingjiaFragment extends BaseFragment {

    private SyskeyvalueBean syskeyvalueBean;
    private String typeValueId, leaderValueId;
    private DirectleaderBean directleaderBean;

    private double Annualleave = 0;

    @BindView(R.id.type)
    TextView typeText;

    @OnClick(R.id.type)
    public void OnTypeSelectBtnClicked() {

        //条件选择器
        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置

                if (syskeyvalueBean != null && syskeyvalueBean.getSuccessInfo().size() > 0) {
                    String tx = syskeyvalueBean.getSuccessInfo().get(options1).getText();
                    typeValueId = syskeyvalueBean.getSuccessInfo().get(options1).getValue();

                    if (tx.equals("年休假")) {//年休假
                        OkHttpManager.getInstance().getRequest(getRootApiUrl() + "/api/user/getPersonInfo/" + getHoldingActivity().getUserAccount().getUserid(),
                                new LoadCallBack<PersoninfoBean>(getContext()) {

                                    @Override
                                    public void onSuccess(Call call, Response response, PersoninfoBean personinfoBean) {

                                        if (personinfoBean != null && personinfoBean.getRequestCode() == 200) {
                                            Annualleave = personinfoBean.getSuccessInfo().getPersonInfo().getAnnualleave();
                                            longtime.setHint("当前年假还有" + (int) Annualleave * 8 + "小时");

                                        } else {
                                            Toast.makeText(getHoldingActivity(), syskeyvalueBean.getErrorMessage().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    public void onEror(Call call, int statusCode, Exception e) {
                                    }
                                });

                    } else {
                        longtime.setHint("请输入");
                    }

                    typeText.setText(tx);
                }
            }
        })
                .setTitleText("请假类型")
                .setSubmitText("确定")
                .setCancelText("取消").build();

        if (syskeyvalueBean == null || syskeyvalueBean.getSuccessInfo().size() == 0) {
            OkHttpManager.getInstance().getRequest(getRootApiUrl() + "/api/syskeyvalue/list/LeaveType",
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

    @BindView(R.id.start_time)
    TextView starttime;

    @OnClick(R.id.start_time)
    public void OnStartTimeSelectBtnClicked() {
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                starttime.setText(getSimpleDateFormat(date, "yyyy-MM-dd HH:mm"));
                setLongtime();
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

    @BindView(R.id.end_time)
    TextView endtime;

    @OnClick(R.id.end_time)
    public void OnEndTimeSelectBtnClicked() {
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                endtime.setText(getSimpleDateFormat(date, "yyyy-MM-dd HH:mm"));
                setLongtime();
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


    @BindView(R.id.dealperson)
    TextView dealperson;

    @OnClick(R.id.dealperson)
    public void OnDealpersonSelectBtnClicked() {

        //条件选择器
        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (directleaderBean != null && directleaderBean.getSuccessInfo().size() > 0) {
                    //返回的分别是三个级别的选中位置
                    String tx = directleaderBean.getSuccessInfo().get(options1).getName();
                    leaderValueId = directleaderBean.getSuccessInfo().get(options1).getPersonid();
                    dealperson.setText(tx);
                }
            }
        })
                .setTitleText("选择处理人")
                .setSubmitText("确定")
                .setCancelText("取消").build();

        if (directleaderBean == null || directleaderBean.getSuccessInfo().size() == 0) {
            OkHttpManager.getInstance().getRequest(getRootApiUrl() + "/api/job/directleaders/" + getHoldingActivity().getUserAccount().getUserid(),
                    new LoadCallBack<DirectleaderBean>(getContext()) {

                        @Override
                        public void onSuccess(Call call, Response response, DirectleaderBean directleaderBean1) {

                            if (directleaderBean1 != null && directleaderBean1.getRequestCode() == 200) {

                                directleaderBean = directleaderBean1;

                                pvOptions.setPicker(getDirectleaderItemText(directleaderBean.getSuccessInfo()));
                                pvOptions.show();

                            } else {
                                Toast.makeText(getHoldingActivity(), directleaderBean.getErrorMessage().toString(), Toast.LENGTH_SHORT).show();
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

    @BindView(R.id.longtime)
    TextView longtime;

    @BindView(R.id.reason)
    EditText reason;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_qinjiashengpi_qinjia;
    }

    @Override
    protected void initActionBar(Toolbar actionBar) {
        super.initActionBar(actionBar);

        //设置标题
        TextView textView = actionBar.findViewById(R.id.toolbar_title);
        textView.setText("请假");

        //启用返回导航
        actionBar.setNavigationIcon(R.mipmap.title_bar_back);

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getHoldingActivity().getMenuInflater().inflate(R.menu.menu_submit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /**
         * 菜单项被点击时调用，也就是菜单项的监听方法。
         * 通过这几个方法，可以得知，对于Activity，同一时间只能显示和监听一个Menu 对象。 TODO Auto-generated
         * method stub
         */

        if (item.getItemId() == R.id.action_settings) {
            //提交信息

            if (typeValueId == null || typeValueId.length() <= 0) {
                new MyAlert("", "请选择请假类型", true, false, getContext());
            } else if (starttime.getText() == null || starttime.getText().toString().length() <= 0) {
                new MyAlert("", "请选择开始时间", true, false, getContext());
            } else if (endtime.getText() == null || endtime.getText().toString().length() <= 0) {
                new MyAlert("", "请选择结束时间", true, false, getContext());
            } else if (longtime.getText() == null || longtime.getText().toString().length() <= 0) {
                new MyAlert("", "请输入请假时长", true, false, getContext());
            } else if (leaderValueId == null || leaderValueId.toString().length() <= 0) {
                new MyAlert("", "请选择处理人", true, false, getContext());
            } else if (reason.getText() == null || reason.getText().toString().length() <= 0) {
                new MyAlert("", "请输入请假理由", true, false, getContext());
            } else {

                Map<String, String> params = new HashMap<>();
                params.put("personId", getHoldingActivity().getUserAccount().getUserid());
                params.put("type", typeValueId);
                params.put("reason", reason.getText().toString());
                params.put("start", starttime.getText().toString());
                params.put("end", endtime.getText().toString());
                params.put("longtime", longtime.getText().toString());
                params.put("dealpersonid", leaderValueId);

                OkHttpManager.getInstance().postRequest(getRootApiUrl() + "/api/attappply/addleave",
                        new LoadCallBack<ResultBean>(getContext()) {

                            @Override
                            public void onSuccess(Call call, Response response, ResultBean resultBean) {

                                if (resultBean.getRequestCode() != 200) {
                                    Toast.makeText(getHoldingActivity(), resultBean.getErrorMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            public void onEror(Call call, int statusCode, Exception e) {
                            }
                        }, params);
            }
        }
        return super.onOptionsItemSelected(item);
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

    private List<String> getDirectleaderItemText(List<DirectleaderBean.SuccessInfoBean> list) {

        List<String> mlist = new ArrayList<>();

        if (list != null && list.size() > 0) {
            for (DirectleaderBean.SuccessInfoBean item : list) {
                mlist.add(item.getDepname() + "-" + item.getName());
            }
        }
        return mlist;
    }


    private void setLongtime() {
        if (endtime.getText() != null && endtime.getText().toString().length() > 0 && starttime.getText() != null && starttime.getText().toString().length() > 0) {

            Date start = DateUtils.strToDate(starttime.getText().toString(), "yyyy-MM-dd HH:mm");
            Date end = DateUtils.strToDate(endtime.getText().toString(), "yyyy-MM-dd HH:mm");

            if (end.getTime() - start.getTime() <= 0) {
                new MyAlert("", "结束时间必须大于开始时间", true, false, getContext());
            } else {
                longtime.setText(String.valueOf(getHour(end, start)));
            }
        }
    }

    //考勤小时计算
    private long getHour(Date endDate, Date startDate) {
        long diff = endDate.getTime() - startDate.getTime();
        return diff / 1000 / 60 / 60 / 24 * 8 + diff / 1000 / 60 / 60 % 24 + 1;
    }
}
