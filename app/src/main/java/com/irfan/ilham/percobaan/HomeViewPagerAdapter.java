package com.irfan.ilham.percobaan;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class HomeViewPagerAdapter extends FragmentStatePagerAdapter {

    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new DashboardFragment();
        } else if (position == 1) {
            fragment = new JobManageFragment();
        } else if (position == 2) {
            fragment = new MoneyManageFragment();
        } else if (position == 3) {
            fragment = new SettingFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}

