package com.irfan.ilham.percobaan;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JobManageFragment extends Fragment {
    private TextView Day, Month, Year;
    private CardView Add, Card1;
    private RecyclerView todayRV;
    private ArrayList<Note> noteTodayList = new ArrayList<>();
    private DBDataSource dataSource;
    private JobRecyclerviewAdapter todayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_job_manage, container, false);

        Day = myview.findViewById(R.id.DateJobManage);
        Month = myview.findViewById(R.id.MonthJobManage);
        Year = myview.findViewById(R.id.YearJobManage);
        Add = myview.findViewById(R.id.JobManageAdd);

        todayRV = myview.findViewById(R.id.JobTodayRecycleView);

        dataSource = new DBDataSource(getActivity());
        dataSource.open();
        noteTodayList = dataSource.getAllNote();

        todayAdapter = new JobRecyclerviewAdapter(noteTodayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        todayRV.setLayoutManager(layoutManager);
        todayRV.setItemAnimator(new DefaultItemAnimator());
        todayRV.setAdapter(todayAdapter);

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        SimpleDateFormat dateFormat = new SimpleDateFormat("LLLL");
        String MonthNow = dateFormat.format(new Date());

        Day.setText(today.monthDay + "");
        Month.setText(MonthNow);
        Year.setText(today.year + "");



        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog addDialog = new Dialog(getActivity());
                addDialog.setContentView(R.layout.job_add_dialog);

                final DBDataSource dataSource = new DBDataSource(getActivity());
                dataSource.open();

                ImageView CloseBtn = addDialog.findViewById(R.id.CloseBtn_JobAddDialog);
                final EditText Title = addDialog.findViewById(R.id.TitleEditText_JobAddDialog);
                CheckBox Job = addDialog.findViewById(R.id.JobCheckBox_JobAddDialog);
                final TextView Deadline = addDialog.findViewById(R.id.DeadlinePicker_JobAddDialog);
                final TextView Time = addDialog.findViewById(R.id.TimePicker_JobAddDialog);
                final TextView Until = addDialog.findViewById(R.id.TimeUntilPicker_JobAddDialog);
                final EditText Note = addDialog.findViewById(R.id.NoteEditText_JobAddDialog);
                final TextView SaveBtn = addDialog.findViewById(R.id.SaveBtn_JobAddDialog);

                SaveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Title.getText() != null && Deadline.getText() != null && Time.getText() != null) {
                            String title = Title.getText().toString();
                            String job = "false";
                            String deadline = Deadline.getText().toString();
                            String time = Time.getText().toString();
                            String timeuntil = Until.getText().toString();
                            String note = Note.getText().toString();

                            Note newnote = null;
                            newnote = dataSource.createNote(title, job, "", deadline, time + " - " + timeuntil, note);

                            Toast.makeText(getActivity(), "Note Id :" + newnote.getId(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                CloseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addDialog.dismiss();
                    }
                });

                Job.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            SaveBtn.setText("Save Job");
                        } else {
                            SaveBtn.setText(getResources().getString(R.string.SaveNote));
                        }
                    }
                });

                Deadline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar newCalendar = Calendar.getInstance();
                        DatePickerDialog DeadlineDate = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar newDate = Calendar.getInstance();
                                newDate.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat dateFormat = new SimpleDateFormat("d LLLL yyyy");
                                Deadline.setText(dateFormat.format(newDate.getTime()));
                            }
                        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                        DeadlineDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                        DeadlineDate.show();
                    }
                });

                Time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                Time.setText( selectedHour + ":" + selectedMinute);
                            }
                        }, hour, minute, true);
                        mTimePicker.setTitle("Select Time");
                        mTimePicker.show();
                    }
                });

                Until.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                Until.setText( selectedHour + ":" + selectedMinute);
                            }
                        }, hour, minute, true);
                        mTimePicker.setTitle("Select Time");
                        mTimePicker.show();
                    }
                });

                addDialog.show();
                addDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                addDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
        });

        return myview;
    }
}
