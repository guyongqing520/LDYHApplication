package net.syxsoft.ldyhapplication.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.utils.BottomNavigationViewHelper;
import net.syxsoft.ldyhapplication.utils.NetWorkUtils;

public abstract class AppActivity extends AppCompatActivity{

    //由于有些跳转无需参数,所以这里无需抽象方法
    protected void handleIntent(Intent intent){}

    protected abstract int getContentViewId();
    protected abstract BaseFragment getFirstFragment();
    protected abstract int getFragmentContainerId();
    protected abstract BottomNavigationView.OnNavigationItemSelectedListener getNavigationItemSelectedListener();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
            int num = getSupportFragmentManager().getBackStackEntryCount();
            for (int i = 0; i < num; i++) {
                FragmentManager.BackStackEntry backstatck = getSupportFragmentManager().getBackStackEntryAt(i);

                if (backstatck.getName().equals(fragment.getClass().getSimpleName())){
                    getSupportFragmentManager().popBackStack(fragment.getClass().getSimpleName(),1);
                }
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContainerId(), fragment)
                    .addToBackStack(((Object)fragment).getClass().getSimpleName())
                    .commitAllowingStateLoss();
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
}