package net.syxsoft.ldyhapplication.ui.AppBase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.UserAccountBean;
import net.syxsoft.ldyhapplication.model.UserModel;
import net.syxsoft.ldyhapplication.ui.Home.IndexFragment;
import net.syxsoft.ldyhapplication.ui.Login.LoginActivity;
import net.syxsoft.ldyhapplication.ui.message.NavigationListFragment;
import net.syxsoft.ldyhapplication.ui.user.MyAdressFragment;
import net.syxsoft.ldyhapplication.ui.user.UserCenterFragment;

public abstract class BaseActivity extends AppActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_base;
    }

    @Override
    public int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    @Override
    public UserAccountBean getUserAccount() {

        //加载个人信息
        UserModel userModel = new UserModel();
        UserAccountBean userAccountBean = userModel.getUserAccountInfo(this);

        if (userAccountBean == null || userAccountBean.getUserid() == null || userAccountBean.getUserid().length() == 0) {
             Intent intent = new Intent(this, LoginActivity.class);
             startActivity(intent);
             finish();
        }
        return userAccountBean;
    }

    @Override
    public BottomNavigationView.OnNavigationItemSelectedListener getNavigationItemSelectedListener() {

        return new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        pushFragment(new IndexFragment());
                        return true;
                    case R.id.navigation_message:
                        pushFragment(new NavigationListFragment());
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