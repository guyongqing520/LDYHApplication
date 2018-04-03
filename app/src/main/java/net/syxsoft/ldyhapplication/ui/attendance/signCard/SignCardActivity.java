package net.syxsoft.ldyhapplication.ui.attendance.signCard;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import net.syxsoft.ldyhapplication.R;
import net.syxsoft.ldyhapplication.ui.AppBase.BaseActivity;
import net.syxsoft.ldyhapplication.ui.AppBase.BaseFragment;

public class SignCardActivity extends BaseActivity {

    @Override
    public BaseFragment getFirstFragment() {
        return new SignCardFragment();
    }

    @Override
    public int getContentViewId() {return R.layout.activity_base_daka;}

    @Override
    public BottomNavigationView.OnNavigationItemSelectedListener getNavigationItemSelectedListener() {

        return new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        pushFragment(new SignCardFragment());
                        return true;
                    case R.id.navigation_message:
                        pushFragment(new SignCardStatisticsFragment());

                        return true;
                    case R.id.navigation_notifications:
                        pushFragment(new SignCardReplenishFragment());
                        return true;
                }
                return false;
            }
        };
    }
}
