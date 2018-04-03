package net.syxsoft.ldyhapplication.ui.Home;


import net.syxsoft.ldyhapplication.ui.AppBase.BaseActivity;
import net.syxsoft.ldyhapplication.ui.AppBase.BaseFragment;

public class IndexActivity extends BaseActivity {

    @Override
    public BaseFragment getFirstFragment() {
        return new IndexFragment();
    }

}
