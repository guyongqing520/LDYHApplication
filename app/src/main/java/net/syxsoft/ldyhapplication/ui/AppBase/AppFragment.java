package net.syxsoft.ldyhapplication.ui.AppBase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class AppFragment extends Fragment {

    public abstract int getLayoutId();

    public abstract Toolbar getToolbar();

    public abstract String getRootApiUrl();

    public abstract void initView(View view, Bundle savedInstanceState);

    public void releaseView() {

    }

    public abstract void initActionBar(Toolbar actionBar);

    public BaseActivity getHoldingActivity() {

        if (getActivity() == null || getActivity().isFinishing()) {
            return null;
        }

        if (getActivity() instanceof BaseActivity) {
            return (BaseActivity) getActivity();
        } else {
            throw new ClassCastException("activity must extends BaseActivity");
        }
    }

    public void pushFragment(BaseFragment fragment) {
        getHoldingActivity().pushFragment(fragment);
    }

    public void popFragment(BaseFragment fragment) {
        getHoldingActivity().popFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view, savedInstanceState);
        setHasOptionsMenu(true);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        Toolbar toolbar = getToolbar();
        getHoldingActivity().setSupportActionBar(toolbar);

        if (toolbar != null) {
            initActionBar(toolbar);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releaseView();
    }
}