package com.irfan.ilham.percobaan;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private HomeViewPagerAdapter viewPagerAdapter;
    private TextView idTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.home_viewPager);
        tabLayout = findViewById(R.id.home_tabLayout);
        idTab = findViewById(R.id.IDHomeTab);

        viewPagerAdapter = new HomeViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.icon_dashboard);
        tabLayout.getTabAt(1).setIcon(R.drawable.icon_job);
        tabLayout.getTabAt(2).setIcon(R.drawable.icon_money);
        tabLayout.getTabAt(3).setIcon(R.drawable.icon_setting);

        if (getIntent().hasExtra("IDTab")) {
            int IDTab = getIntent().getExtras().getInt("IDTab");
            idTab.setText(R.string.Setting);
            viewPager.setCurrentItem(IDTab);
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    idTab.setText(R.string.Dasboard);
                } else if (position == 1) {
                    idTab.setText(R.string.Workbench);
                } else if (position == 2) {
                    idTab.setText(R.string.Finance);
                } else if (position == 3) {
                    idTab.setText(R.string.Setting);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
