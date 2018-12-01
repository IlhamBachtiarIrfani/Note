package com.irfan.ilham.percobaan;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DashboardFragment extends Fragment {
    private CardView moneyDashBoard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myview =  inflater.inflate(R.layout.fragment_dashboard, container, false);

        moneyDashBoard = myview.findViewById(R.id.DashboardMoney);

        moneyDashBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager parent = getActivity().findViewById(R.id.home_viewPager);
                parent.setCurrentItem(2);
            }
        });

        return  myview;
    }
}
