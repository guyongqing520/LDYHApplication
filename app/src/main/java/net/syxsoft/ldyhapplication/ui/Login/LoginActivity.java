package net.syxsoft.ldyhapplication.ui.Login;


import net.syxsoft.ldyhapplication.bean.UserAccountBean;
import net.syxsoft.ldyhapplication.ui.AppBase.BaseActivity;
import net.syxsoft.ldyhapplication.ui.AppBase.BaseFragment;

public class LoginActivity extends BaseActivity {

    @Override
    public BaseFragment getFirstFragment() {
        return new LoginFragment();
    }

    @Override
    public UserAccountBean getUserAccount() {

        //这里重写主要不需要登录，默认不用写，表示已登录
        return new UserAccountBean();
    }

}
