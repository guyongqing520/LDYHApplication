package net.syxsoft.ldyhapplication.ui;


import net.syxsoft.ldyhapplication.bean.UserAccountBean;
import net.syxsoft.ldyhapplication.model.UserModel;

public class LoginActivity extends BaseActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return new LoginFragment();
    }

    @Override
    protected UserAccountBean getUserAccount() {

        //这里重写主要不需要登录，默认不用写，表示已登录
        return new UserAccountBean();
    }

}
