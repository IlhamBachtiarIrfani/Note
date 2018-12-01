package com.irfan.ilham.percobaan;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MoneyManageFragment extends Fragment {
    private CardView Card2, AddTarget, AddPayment;
    private ImageView DetailBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview =  inflater.inflate(R.layout.fragment_money_manage, container, false);

        Card2 =  myview.findViewById(R.id.Card2);
        AddTarget = myview.findViewById(R.id.AddTargetMoneyManage);
        AddPayment = myview.findViewById(R.id.AddPaymentMoneyManage);
        DetailBtn = myview.findViewById(R.id.DetailBtnMoneyManage);

        DetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PaymentDetailActivity.class));
            }
        });

        AddTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog addDialog = new Dialog(getActivity());
                addDialog.setContentView(R.layout.saving_target_add_dialog);
                addDialog.show();
                addDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                addDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
        });

        AddPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog addDialog = new Dialog(getActivity());
                addDialog.setContentView(R.layout.payment_add_dialog);

                Spinner type = addDialog.findViewById(R.id.TypeSpinnerAddPayment);

                List<String> value = new ArrayList<String>();
                value.add(getResources().getString(R.string.Income));
                value.add(getResources().getString(R.string.Spending));

                ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, value);
                typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                type.setAdapter(typeAdapter);

                addDialog.show();
                addDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                addDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
        });

        Card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog addDialog = new Dialog(getActivity());
                addDialog.setContentView(R.layout.saving_target_detail_dialog);
                addDialog.show();
                addDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                addDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
        });

        return  myview;
    }
}
