package net.syxsoft.ldyhapplication.ui;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import net.syxsoft.ldyhapplication.R;

public class KaoqiDakaActivity extends BaseActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return new KaoqiDakaFragment();
    }

    @Override
    protected int getContentViewId() {return R.layout.activity_base_daka;}

    @Override
    protected BottomNavigationView.OnNavigationItemSelectedListener getNavigationItemSelectedListener() {

        return new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        pushFragment(new KaoqiDakaFragment());
                        return true;
                    case R.id.navigation_message:
                        pushFragment(new KaoqiDakaTongjiFragment());

                        return true;
                    case R.id.navigation_notifications:
                        pushFragment(new KaoqiDakaBuqianFragment());
                        return true;
                }
                return false;
            }
        };
    }
}
