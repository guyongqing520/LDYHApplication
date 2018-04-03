package net.syxsoft.ldyhapplication.ui.AppBase;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import net.syxsoft.ldyhapplication.R;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends AppFragment {

    @Override
    public String getRootApiUrl() {
        return "http://ldyh.webapi.syxsoft.net:8801";
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void initActionBar(android.support.v7.widget.Toolbar actionBar) {
        //默认取消标题，用碎片创建
        getHoldingActivity().getSupportActionBar().setTitle("");
        actionBar.setTitle("");

        getHoldingActivity().getSupportActionBar().setHomeButtonEnabled(true);
        getHoldingActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public Toolbar getToolbar() {
        Toolbar toolbar= getHoldingActivity().findViewById(R.id.toolbar);
        if (toolbar!=null) {
            return toolbar;
        }
        return  new Toolbar(getHoldingActivity());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       getHoldingActivity().getMenuInflater().inflate(R.menu.menu_nosubmit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
