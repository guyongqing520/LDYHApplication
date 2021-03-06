package net.syxsoft.ldyhapplication.ui.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.LoginBean;
import net.syxsoft.ldyhapplication.callback.LoadCallBack;
import net.syxsoft.ldyhapplication.model.UserModel;
import net.syxsoft.ldyhapplication.ui.AppBase.BaseFragment;
import net.syxsoft.ldyhapplication.ui.Home.IndexActivity;
import net.syxsoft.ldyhapplication.utils.MD5Utils;
import net.syxsoft.ldyhapplication.utils.MyAlert;
import net.syxsoft.ldyhapplication.utils.OkHttpManager;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment {


    @BindView(R.id.user)
    EditText mobile_txt;

    @BindView(R.id.password)
    EditText password_txt;

    @OnClick(R.id.login_btn)
    public void onRegisterBtnClicked() {

        final String username = mobile_txt.getText().toString();
        final String password = password_txt.getText().toString();

        //!CharTools.isPhoneLegal(username）
        if (username == null || username.length() == 0 || password == null || password.length() == 0) {

            MyAlert.getInstance().show("", "手机号码和密码不正确", true, false, getContext());
            return;
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("userpwd", MD5Utils.getMd5Value(password));

        OkHttpManager.getInstance().postRequest(getRootApiUrl() + "/api/user/login", new LoadCallBack<LoginBean>(getActivity()) {
                    @Override
                    public void onSuccess(Call call, Response response, LoginBean loginBean) {

                        if (getHoldingActivity()!=null) {

                            if (loginBean.getRequestCode() != 200) {

                                MyAlert.getInstance().show("", loginBean.getErrorMessage().toString(), true, false, getContext());
                                return;
                            }

                            //增加用户信息，以后都要利用此帐号和密码进行对比用户，防止此用户信息有新的更新作用
                            UserModel userModel = new UserModel();

                            if (loginBean.getSuccessInfo() != null) {
                                userModel.addUserAccountInfo(username, password, loginBean.getSuccessInfo().getPersonId(), getContext());
                            }
                            //导航到主面板
                            Intent intent = new Intent(getContext(), IndexActivity.class);
                            startActivity(intent);
                            getHoldingActivity().finish();
                        }
                    }
                }
                , params);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initActionBar(Toolbar actionBar) {
        super.initActionBar(actionBar);

        //标题
        TextView textView = actionBar.findViewById(R.id.toolbar_title);
        textView.setText("");

        //禁用返回导航，因为此页就是首页
        actionBar.setNavigationIcon(null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //启用底部导航
        BottomNavigationView navigation = getHoldingActivity().findViewById(R.id.navigation);
        navigation.setVisibility(View.GONE);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
