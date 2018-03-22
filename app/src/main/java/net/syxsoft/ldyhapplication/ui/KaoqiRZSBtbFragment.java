package net.syxsoft.ldyhapplication.ui;


import android.app.ProgressDialog;
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
import com.bigkoo.pickerview.lib.WheelView;

import net.syxsoft.ldyhapplication.R;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import net.syxsoft.ldyhapplication.bean.AttendenceBean;
import net.syxsoft.ldyhapplication.callback.GsonObjectCallback;
import net.syxsoft.ldyhapplication.model.rztb_bglx_model;
import net.syxsoft.ldyhapplication.bean.RzsbRztbBean;
import net.syxsoft.ldyhapplication.utils.MD5Utils;
import net.syxsoft.ldyhapplication.utils.OkHttp3Utils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class KaoqiRZSBtbFragment extends BaseFragment {

    private OptionsPickerView  optionsPickerView;
    private ArrayList<rztb_bglx_model> Rztb_bglx_list=new ArrayList<>();

    @BindView(R.id.rzsb_gznr)
    EditText rzsb_gznr;

    @BindView(R.id.rzsb_bglx)
    TextView rzsb_bglx;

    @BindView(R.id.rzsb_bgdd)
    EditText rzsb_bgdd;

    @BindView(R.id.rzsb_glrw)
    TextView rzsb_glrw;

    @OnClick(R.id.rzsb_glrw)
    public void onRzsbGlrwBtnClicked() {
          optionsPickerView=new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                Toast.makeText(getContext(),Rztb_bglx_list.get(options1).getTitle(),Toast.LENGTH_SHORT).show();
            }
        }).build();
        optionsPickerView.setPicker(Rztb_bglx_list);
        optionsPickerView.show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rzsb_rztb;
    }

    @Override
    protected void initActionBar(Toolbar actionBar) {
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
        if (!getHoldingActivity().isNetWorkAvailable()) {
            Toast.makeText(getHoldingActivity(), "没有网络连接，请稍后重试", Toast.LENGTH_SHORT).show();
            return false;
        }
        String jsonParams="{\"personId\":\"11111\",\"type\":\"1\",\"address\":\"四川成都\",\"taskId\":\"12\",\"content\":\"这里是日志的内容部分\" \"}";

        final ProgressDialog progressDialog = new ProgressDialog(getContext());

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("加载中...");
        progressDialog.show();

        if (item.getItemId()==R.id.action_settings) {
            //提交信息
            try {
                OkHttp3Utils.getInstance().doPostJson(getRootApiUrl() + "/api/workdaily/write/",jsonParams,
                        new GsonObjectCallback<RzsbRztbBean>() {

                            @Override
                            public void onSuccess(RzsbRztbBean rzsbRztbBean) {
                                progressDialog.dismiss();

                                if (rzsbRztbBean.getRequestCode() != 200) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getHoldingActivity(), "网络连接失败，请稍后重试", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailed(Call call, IOException e) {
                                progressDialog.dismiss();
                                Toast.makeText(getHoldingActivity(), "网络连接失败，请稍后重试", Toast.LENGTH_SHORT).show();
                            }
                        });
            } catch (Exception ex) {
                progressDialog.dismiss();
                Toast.makeText(getHoldingActivity(), "网络连接失败，请稍后重试", Toast.LENGTH_SHORT).show();
            }
            }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //去掉底部导航
        BottomNavigationView navigation= getHoldingActivity().findViewById(R.id.navigation);
        navigation.setVisibility(View.GONE);

        Rztb_bglx_list.add(new rztb_bglx_model(1,"个人办公"));
        Rztb_bglx_list.add(new rztb_bglx_model(2,"小组办公"));
        Rztb_bglx_list.add(new rztb_bglx_model(3,"企业办公"));
        Rztb_bglx_list.add(new rztb_bglx_model(4,"集团办公"));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
