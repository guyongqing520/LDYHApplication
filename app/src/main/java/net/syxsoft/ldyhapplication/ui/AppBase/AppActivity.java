package net.syxsoft.ldyhapplication.ui.AppBase;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.bean.UserAccountBean;
import net.syxsoft.ldyhapplication.ui.Login.LoginActivity;
import net.syxsoft.ldyhapplication.utils.BottomNavigationViewHelper;
import net.syxsoft.ldyhapplication.utils.MyApp;
import net.syxsoft.ldyhapplication.utils.NetWorkUtils;

public abstract class AppActivity extends AppCompatActivity{

    //由于有些跳转无需参数,所以这里无需抽象方法
    public void handleIntent(Intent intent){}

    public abstract int getContentViewId();
    public abstract BaseFragment getFirstFragment();
    public abstract int getFragmentContainerId();
    public abstract UserAccountBean getUserAccount();
    public abstract BottomNavigationView.OnNavigationItemSelectedListener getNavigationItemSelectedListener();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getUserAccount()==null) {  //导航到login
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        //写死竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //处理Intent(主要用来获取其中携带的参数)
        if (getIntent() != null){
            handleIntent(getIntent());
        }
        //设置contentView
        setContentView(getContentViewId());

        //底部导航动画固定
        try {
            BottomNavigationView navigation = findViewById(R.id.navigation);
            BottomNavigationViewHelper.disableShiftMode(navigation);
            navigation.setOnNavigationItemSelectedListener(getNavigationItemSelectedListener());

        }catch(Exception ex) {}

        //添加Activity
        MyApp.addActivity(this);

        //添加栈底的第一个fragment
        if (getSupportFragmentManager().getBackStackEntryCount() == 0){
            if (getFirstFragment() != null){
                pushFragment(getFirstFragment());
            }else {
                throw new NullPointerException("getFirstFragment() cannot be null");
            }
        }
    }

    public void pushFragment(BaseFragment fragment){
        //此方法指在让保存唯一碎片，并让当前压栈的碎片位于最上方
        if (fragment != null){

            FragmentManager fm = getSupportFragmentManager();
            fm.popBackStack(fragment.getClass().getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(getFragmentContainerId(), fragment, fragment.getClass().getName());
            transaction.addToBackStack(null);//将fragment加入返回栈
            transaction.commitAllowingStateLoss();

        }
    }

    public boolean isNetWorkAvailable(){
        return NetWorkUtils.isNetWorkAvailable(this);
    }

    //弹出碎片
    public void popFragment(){
        if (getSupportFragmentManager().getBackStackEntryCount() > 1){
            getSupportFragmentManager().popBackStack();
        }else {
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1){
            getSupportFragmentManager().popBackStack();
            return true;
        }
        return super.onSupportNavigateUp();
    }

    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        Intent intent = super.getSupportParentActivityIntent();
        if (intent == null){
            finish();
        }
        return intent;
    }


    //此方解决了最后0碎片失去，活动也一并消失
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0){
            finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyApp.removeActivity(this);
    }
}