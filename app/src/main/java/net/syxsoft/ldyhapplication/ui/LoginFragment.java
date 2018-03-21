package net.syxsoft.ldyhapplication.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;
import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.LoginBean;
import net.syxsoft.ldyhapplication.callback.GsonObjectCallback;
import net.syxsoft.ldyhapplication.model.UserModel;
import net.syxsoft.ldyhapplication.utils.MD5Utils;
import net.syxsoft.ldyhapplication.utils.OkHttp3Utils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment {


    @BindView(R.id.user)
    EditText mobile_txt;

    @BindView(R.id.password)
    EditText password_txt;

    @OnClick(R.id.login_btn)
    public void onRegisterBtnClicked(){

        final String username=mobile_txt.getText().toString();
        final String password=password_txt.getText().toString();

        //!CharTools.isPhoneLegal(username）
        if (username==null||username.length()==0||password==null||password.length()==0) {

            new AlertDialog.Builder(getContext())
                    .setMessage("手机号码和密码不正确")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create().show();

            return;

        }

        if(!getHoldingActivity().isNetWorkAvailable()){
            Toast.makeText(getHoldingActivity(), "没有网络连接，请稍后重试", Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        try {

            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("登录中...");
            progressDialog.show();

            String jsonParams="{\"username\":\""+username+"\",\"userpwd\":\""+ MD5Utils.getMd5Value(password)+"\"}";

            OkHttp3Utils.getInstance().doPostJson(getRootApiUrl()+"/api/user/login",jsonParams,
                    new GsonObjectCallback<LoginBean>() {
                        @Override
                        public void onSuccess(LoginBean loginBean) {

                            if (loginBean.getRequestCode() != 200) {
                                new AlertDialog.Builder(getContext())
                                        .setMessage(loginBean.getErrorMessage().toString())
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        }).create().show();
                                return;
                            }

                            //增加用户信息，以后都要利用此帐号和密码进行对比用户，防止此用户信息有新的更新作用
                            UserModel userModel = new UserModel();

                            if (loginBean.getSuccessInfo()!=null) {
                                userModel.addUserAccountInfo(username, password, loginBean.getSuccessInfo().getPersonId(), getContext());
                            }
                            //导航到主面板

                            progressDialog.dismiss();

                            Intent intent = new Intent(getContext(), IndexActivity.class);
                            startActivity(intent);
                            getHoldingActivity().finish();
                        }

                        @Override
                        public void onFailed(Call call, IOException e) {
                            progressDialog.dismiss();
                            Toast.makeText(getHoldingActivity(), "网络连接失败，请稍后重试", Toast.LENGTH_SHORT).show();
                        }
                    });
        }catch (Exception ex){
            progressDialog.dismiss();
            Toast.makeText(getHoldingActivity(), "网络连接失败，请稍后重试", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initActionBar(Toolbar actionBar) {
        super.initActionBar(actionBar);

        //标题
        TextView textView = actionBar.findViewById(R.id.toolbar_title);
        textView.setText("");

        //禁用返回导航，因为此页就是首页
        actionBar.setNavigationIcon(null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //启用底部导航
        BottomNavigationView navigation = getHoldingActivity().findViewById(R.id.navigation);
        navigation.setVisibility(View.GONE);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
