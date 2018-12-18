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
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JobManageFragment extends Fragment {
    private TextView Day, Month, Year;
    private CardView Add;
    private RecyclerView todayRV, tomorrowRV, nextRV;
    private ArrayList<Note> noteTodayList = new ArrayList<>();
    private ArrayList<Note> noteTomorrowList = new ArrayList<>();
    private ArrayList<Note> noteNextList = new ArrayList<>();
    private DBDataSource dataSource;
    private JobRecyclerviewAdapter todayAdapter, tomorrowAdapter, nextAdapter;
    private TextView Today, Tomorrow, Next;

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
        tomorrowRV = myview.findViewById(R.id.JobTomorrowRecycleView);
        nextRV = myview.findViewById(R.id.JobNextRecycleView);
        Today = myview.findViewById(R.id.JobTodayText);
        Tomorrow = myview.findViewById(R.id.JobTomorrowText);
        Next = myview.findViewById(R.id.JobNextText);

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        SimpleDateFormat dateFormat = new SimpleDateFormat("LLLL");
        String MonthNow = dateFormat.format(new Date());

        Day.setText(today.monthDay + "");
        Month.setText(MonthNow);
        Year.setText(today.year + "");

        readAllNote();

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog();
            }
        });

        return myview;
    }

    private void addDialog() {
        final Dialog addDialog = new Dialog(getActivity());
        addDialog.setContentView(R.layout.job_add_dialog);

        final DBDataSource dataSource = new DBDataSource(getActivity());
        dataSource.open();

        ImageView CloseBtn = addDialog.findViewById(R.id.CloseBtn_JobAddDialog);
        final EditText Title = addDialog.findViewById(R.id.TitleEditText_JobAddDialog);
        final CheckBox Job = addDialog.findViewById(R.id.JobCheckBox_JobAddDialog);
        final TextView Deadline = addDialog.findViewById(R.id.DeadlinePicker_JobAddDialog);
        final TextView Time = addDialog.findViewById(R.id.TimePicker_JobAddDialog);
        final TextView Until = addDialog.findViewById(R.id.TimeUntilPicker_JobAddDialog);
        final EditText Note = addDialog.findViewById(R.id.NoteEditText_JobAddDialog);
        final TextView SaveBtn = addDialog.findViewById(R.id.SaveBtn_JobAddDialog);
        final RecyclerView JobList = addDialog.findViewById(R.id.jobListRecycleView);
        ImageView addjob = addDialog.findViewById(R.id.JobListAddBtn);

        final ArrayList<String> jobList = new ArrayList<>();
        final JobListRecyclerviewAdapter jobAdapter = new JobListRecyclerviewAdapter(jobList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        JobList.setLayoutManager(layoutManager);
        JobList.setItemAnimator(new DefaultItemAnimator());
        JobList.setAdapter(jobAdapter);

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
                    JobList.setVisibility(View.VISIBLE);
                    jobAdapter.noteList.add("");
                    jobAdapter.notifyDataSetChanged();
                } else {
                    JobList.setVisibility(View.GONE);
                    jobList.clear();
                }
            }
        });

        addjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Job.isChecked()) {
                    jobAdapter.noteList.add("");
                    jobAdapter.notifyDataSetChanged();
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
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd LLLL yyyy");
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
                        String Hour = "", Minute = "";
                        if (selectedHour < 10) {
                            Hour = "0" + selectedHour;
                        } else {
                            Hour = selectedHour + "";
                        }
                        if (selectedMinute < 10) {
                            Minute = "0" + selectedMinute;
                        } else {
                            Minute = selectedMinute + "";
                        }
                        Time.setText(Hour + ":" + Minute);
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
                        String Hour = "", Minute = "";
                        if (selectedHour < 10) {
                            Hour = "0" + selectedHour;
                        } else {
                            Hour = selectedHour + "";
                        }
                        if (selectedMinute < 10) {
                            Minute = "0" + selectedMinute;
                        } else {
                            Minute = selectedMinute + "";
                        }
                        Until.setText(Hour + ":" + Minute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Title.getText().equals("") || Title.getText().equals(" ") || Title.getText() == null) {
                    Title.setError("Judul Kosong");
                } else if (Title.getText().length() > 50) {
                    Title.setError("Karakter Terlalu Panjang");
                } else if (Title.getText().length() <= 50) {
                    Title.setError(null);
                }

                if (Deadline.getText().equals("")) {
                    Deadline.setError("!");
                } else {
                    Deadline.setError(null);
                }

                if (Time.getText().equals("")) {
                    Time.setError("!");
                } else {
                    Time.setError(null);
                }

                if (Job.isChecked()) {
                    String firstNote = jobAdapter.noteList.get(0);
                    if (firstNote.isEmpty() || firstNote.equals("") || firstNote.equals(" ") || jobAdapter.noteList.size() <= 1) {
                        Job.setChecked(false);
                    }
                }

                if (!Title.getText().equals("") && !Title.getText().equals(" ") && !Deadline.getText().equals("") && !Time.getText().equals("")) {
                    String title = Title.getText().toString();
                    String deadline = Deadline.getText().toString();
                    String time = Time.getText().toString();
                    String timeuntil = Until.getText().toString();
                    String note = Note.getText().toString();

                    List<String> finalJobList = jobAdapter.noteList;
                    ArrayList<String> finalJobList2 = new ArrayList<>();

                    for (int i = 0; i < finalJobList.size(); i++) {
                        if (!finalJobList.get(i).isEmpty()) {
                            finalJobList2.add(finalJobList.get(i));
                        }
                    }

                    String finalJob = TextUtils.join(", ", finalJobList2);

                    if (timeuntil.equals("") || timeuntil.equals(" ")) {
                        dataSource.createNote(title, finalJob, deadline, time, note, "false");
                    } else {
                        dataSource.createNote(title, finalJob, deadline, time + " - " + timeuntil, note, "false");
                    }
                    Toast.makeText(getActivity(), "Successfully added", Toast.LENGTH_SHORT).show();
                    readAllNote();
                    addDialog.dismiss();
                }
            }
        });

        addDialog.show();
        addDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        addDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    private void readAllNote() {
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLLL");
        String MonthNow = dateFormat.format(new Date());
        int tomorrow = today.monthDay + 1;

        dataSource = new DBDataSource(getActivity());
        dataSource.open();
        noteTodayList = dataSource.getNoteByDate(today.monthDay + " " + MonthNow + " " + today.year);
        noteTomorrowList = dataSource.getNoteByDate(tomorrow + " " + MonthNow + " " + today.year);
        noteNextList = dataSource.getNoteByNextDate(today.monthDay + " " + MonthNow + " " + today.year, tomorrow + " " + MonthNow + " " + today.year);

        if (noteTodayList.size() <= 0) {
            todayRV.setVisibility(View.GONE);
            Today.setVisibility(View.GONE);
        } else {
            todayRV.setVisibility(View.VISIBLE);
            Today.setVisibility(View.VISIBLE);
            todayAdapter = new JobRecyclerviewAdapter(noteTodayList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            todayRV.setLayoutManager(layoutManager);
            todayRV.setItemAnimator(new DefaultItemAnimator());
            todayRV.setAdapter(todayAdapter);
        }

        if (noteTomorrowList.size() <= 0) {
            tomorrowRV.setVisibility(View.GONE);
            Tomorrow.setVisibility(View.GONE);
        } else {
            tomorrowRV.setVisibility(View.VISIBLE);
            Tomorrow.setVisibility(View.VISIBLE);
            tomorrowAdapter = new JobRecyclerviewAdapter(noteTomorrowList);
            RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getActivity().getApplicationContext());
            tomorrowRV.setLayoutManager(layoutManager2);
            tomorrowRV.setItemAnimator(new DefaultItemAnimator());
            tomorrowRV.setAdapter(tomorrowAdapter);
        }

        if (noteNextList.size() <= 0) {
            nextRV.setVisibility(View.GONE);
            Next.setVisibility(View.GONE);
        } else {
            nextRV.setVisibility(View.VISIBLE);
            Next.setVisibility(View.VISIBLE);
            nextAdapter = new JobRecyclerviewAdapter(noteNextList);
            RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(getActivity().getApplicationContext());
            nextRV.setLayoutManager(layoutManager3);
            nextRV.setItemAnimator(new DefaultItemAnimator());
            nextRV.setAdapter(nextAdapter);
        }
    }
}
