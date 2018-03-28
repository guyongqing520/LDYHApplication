package net.syxsoft.ldyhapplication.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.Toast;

import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.UserAccountBean;
import net.syxsoft.ldyhapplication.model.UserModel;

public abstract class BaseActivity extends AppActivity {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_base;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    @Override
    protected UserAccountBean getUserAccount() {

        //加载个人信息
        UserModel userModel = new UserModel();
        UserAccountBean userAccountBean = userModel.getUserAccountInfo(this);
        userAccountBean.setUsername("huzhimin");
        userAccountBean.setPassword("123456");
        userAccountBean.setUserid("101");
        if (userAccountBean == null || userAccountBean.getUserid() == null || userAccountBean.getUserid().length() == 0) {
            return null;
        }
        return userAccountBean;
    }

    @Override
    protected BottomNavigationView.OnNavigationItemSelectedListener getNavigationItemSelectedListener() {

        return new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        pushFragment(new IndexFragment());
                        return true;
                    case R.id.navigation_message:
                        pushFragment(new MessageFragment());
                        return true;
                    case R.id.navigation_myaddress:
                        pushFragment(new MyAdressFragment());
                        return true;
                    case R.id.navigation_my:
                        pushFragment(new UserCenterFragment());
                        return true;
                }
                return false;
            }
        };
    }
}