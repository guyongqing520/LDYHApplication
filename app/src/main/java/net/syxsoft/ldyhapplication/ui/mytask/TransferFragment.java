package net.syxsoft.ldyhapplication.ui.mytask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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


public class TransferFragment extends BaseFragment {

    //移交说明
    @BindView(R.id.yj_content)
    EditText yj_content;

    //接收人
    @BindView(R.id.yj_jsuser)
    TextView yj_jsuser;
    private  int yj_jsuser_id=0;
    private SyskeyvalueBean syskeyvalueBean;

    @OnClick
    public  void  OnYjJsuserClicked(){
        //条件选择器
        final OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if (syskeyvalueBean != null && syskeyvalueBean.getSuccessInfo().size() > 0) {
                    String tx = syskeyvalueBean.getSuccessInfo().get(options1).getText();
                    yj_jsuser_id = Integer.valueOf( syskeyvalueBean.getSuccessInfo().get(options1).getValue());
                    yj_jsuser.setText(tx);
                }
            }
        })
                .setTitleText("接收人")
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
        return R.layout.fragment_kaoqin_renwu_yj;
    }

    @Override
    public void initActionBar(Toolbar actionBar) {
        super.initActionBar(actionBar);

        //设置标题
        TextView textView = actionBar.findViewById(R.id.toolbar_title);
        textView.setText("移交");

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
            if (yj_content.getText() == null || yj_content.getText().toString().length() <= 0) {
                MyAlert.getInstance().show("", "请填写移交内容", true, false, getContext());
            }else if(yj_jsuser_id >0) {
                MyAlert.getInstance().show("", "请选择接收人", true, false, getContext());
            }
            else {

                Map<String, String> params = new HashMap<String, String>();
                params.put("personId", getHoldingActivity().getUserAccount().getUserid());
                params.put("content", yj_content.getText().toString());
                params.put("jsuser",String.valueOf(yj_jsuser_id));
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
