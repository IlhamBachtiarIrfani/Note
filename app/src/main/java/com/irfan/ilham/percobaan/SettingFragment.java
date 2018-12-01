package com.irfan.ilham.percobaan;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Locale;

public class SettingFragment extends Fragment {

    private Switch darkModeSwitch;
    private Spinner lang;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            getActivity().setTheme(R.style.DarkTheme);
        } else {
            getActivity().setTheme(R.style.AppTheme);
        }
        View myview = inflater.inflate(R.layout.fragment_setting, container, false);

        darkModeSwitch = myview.findViewById(R.id.darkModeSwitch);
        lang = myview.findViewById(R.id.languageSetting);

        ArrayAdapter<CharSequence> langAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.lang, R.layout.spinner_item);
        langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lang.setAdapter(langAdapter);

        String DefaultLanguageCode = Locale.getDefault().toString();
        if (DefaultLanguageCode.equals("en")) {
            lang.setSelection(0);
        } else if (DefaultLanguageCode.equals("in")) {
            lang.setSelection(1);
        } else if (DefaultLanguageCode.equals("in_ID")) {
            lang.setSelection(1);
        }

        lang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String DefaultLanguageCode = Locale.getDefault().toString();
                if (position == 0) {
                    if (!DefaultLanguageCode.equals("en")) {
                        changeLanguage("en");
                    }
                } else if (position == 1) {
                    if (!DefaultLanguageCode.equals("in") && !DefaultLanguageCode.equals("in_ID")) {
                        changeLanguage("in");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            darkModeSwitch.setChecked(true);
        }
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    restartApp();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    restartApp();
                }
            }
        });

        return myview;
    }

    public void restartApp() {
        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        intent.putExtra("IDTab", 3);
        startActivity(intent);
        getActivity().finish();
    }

    public void changeLanguage(String LanguageCode) {
        Locale locale = new Locale(LanguageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());
        restartApp();
    }
}
