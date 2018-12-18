package com.irfan.ilham.percobaan;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class JobRecyclerviewAdapter extends RecyclerView.Adapter<JobRecyclerviewAdapter.JobViewHolder> {

    private List<Note> noteList;

    public class JobViewHolder extends RecyclerView.ViewHolder {
        public TextView title, date, time;
        public View upline, bottomline;
        public CardView card;
        public LinearLayout gradient;

        public JobViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.TitleJobItem);
            date = view.findViewById(R.id.DateJobItem);
            time = view.findViewById(R.id.TimeJobItem);
            upline = view.findViewById(R.id.UpLineJobItem);
            bottomline = view.findViewById(R.id.BottomLineJobItem);
            card = view.findViewById(R.id.CardJobItem);
            gradient = view.findViewById(R.id.GradientJobItem);
        }
    }

    public JobRecyclerviewAdapter(List<Note> noteList) {
        this.noteList = noteList;
    }

    @Override
    public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_item, parent, false);

        return new JobViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final JobViewHolder holder, int position) {
        final Note note = noteList.get(position);
        holder.title.setText(note.getTitle());
        holder.date.setText(note.getDate());
        holder.time.setText(note.getTime());

        if (position == 0 && position == (noteList.size() - 1)) {
            holder.upline.setVisibility(View.INVISIBLE);
            holder.bottomline.setVisibility(View.INVISIBLE);
        } else if (position == 0) {
            holder.upline.setVisibility(View.INVISIBLE);
            holder.bottomline.setVisibility(View.VISIBLE);
        } else if (position == (noteList.size() - 1)) {
            holder.upline.setVisibility(View.VISIBLE);
            holder.bottomline.setVisibility(View.INVISIBLE);
        } else {
            holder.upline.setVisibility(View.VISIBLE);
            holder.bottomline.setVisibility(View.VISIBLE);
        }

        for (int i = 0; i <= 24; i++) {
            for (int j = 0; j < 60; j++) {
                String time[] = note.getTime().split("\\s*- \\s*");
                String timedetect = i + ":" + j;
                if (time[0].equals(timedetect)) {
                    if (i <= 4) {
                        holder.gradient.setBackground(holder.gradient.getContext().getResources().getDrawable(R.drawable.night_job_gradient));
                    } else if (i <= 7) {
                        holder.gradient.setBackground(holder.gradient.getContext().getResources().getDrawable(R.drawable.afternoon2_job_gradient));
                    } else if (i <= 10) {
                        holder.gradient.setBackground(holder.gradient.getContext().getResources().getDrawable(R.drawable.afternoon_job_gradient));
                    } else if (i <= 14) {
                        holder.gradient.setBackground(holder.gradient.getContext().getResources().getDrawable(R.drawable.day_job_gradient));
                    } else if (i <= 17) {
                        holder.gradient.setBackground(holder.gradient.getContext().getResources().getDrawable(R.drawable.afternoon_job_gradient));
                    } else if (i <= 20) {
                        holder.gradient.setBackground(holder.gradient.getContext().getResources().getDrawable(R.drawable.afternoon2_job_gradient));
                    } else if (i <= 24) {
                        holder.gradient.setBackground(holder.gradient.getContext().getResources().getDrawable(R.drawable.night_job_gradient));
                    }
                }
            }
        }

        holder.card.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                final Dialog DetailDialog = new Dialog(holder.card.getContext());
                DetailDialog.setContentView(R.layout.job_detail_dialog);
                TextView Title = DetailDialog.findViewById(R.id.TitleJobDetail);
                TextView Date = DetailDialog.findViewById(R.id.DateJobDetail);
                TextView Time = DetailDialog.findViewById(R.id.TimeJobDetail);
                TextView Note = DetailDialog.findViewById(R.id.NoteJobDetail);
                RecyclerView Job = DetailDialog.findViewById(R.id.ListJobDetailDialog);
                final TextView edit = DetailDialog.findViewById(R.id.EditJobDetailDialog);
                TextView done = DetailDialog.findViewById(R.id.DoneJobDetailDialog);

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateDialog(note.getId(), edit.getContext());
                    }
                });

                List<String> JobList = Arrays.asList(note.getJobList().split("\\s*, \\s*"));
                JobListDetailRecyclerviewAdapter todayAdapter = new JobListDetailRecyclerviewAdapter(JobList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(holder.card.getContext());
                Job.setLayoutManager(layoutManager);
                Job.setItemAnimator(new DefaultItemAnimator());
                Job.setAdapter(todayAdapter);

                Title.setText(note.getTitle());
                Date.setText(note.getDate());
                Time.setText(note.getTime());
                Note.setText(note.getNote());

                DetailDialog.show();
                DetailDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                DetailDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    private void updateDialog(final long id, final Context con) {
        final Dialog addDialog = new Dialog(con);
        addDialog.setContentView(R.layout.job_update_dialog);

        final DBDataSource dataSource = new DBDataSource(con);
        dataSource.open();

        ImageView CloseBtn = addDialog.findViewById(R.id.CloseBtn_JobUpdateDialog);
        final EditText Title = addDialog.findViewById(R.id.TitleEditText_JobUpdateDialog);
        final CheckBox Job = addDialog.findViewById(R.id.JobCheckBox_JobUpdateDialog);
        final TextView Deadline = addDialog.findViewById(R.id.DeadlinePicker_JobUpdateDialog);
        final TextView Time = addDialog.findViewById(R.id.TimePicker_JobUpdateDialog);
        final TextView Until = addDialog.findViewById(R.id.TimeUntilPicker_JobUpdateDialog);
        final EditText Note = addDialog.findViewById(R.id.NoteEditText_JobUpdateDialog);
        final TextView SaveBtn = addDialog.findViewById(R.id.SaveBtn_JobUpdateDialog);
        final RecyclerView JobList = addDialog.findViewById(R.id.jobListUpdateRecycleView);
        ImageView addjob = addDialog.findViewById(R.id.JobUpdateListAddBtn);

        Note note = dataSource.getNote(id);

        Title.setText(note.getTitle());
        Deadline.setText(note.getDate());
        Note.setText(note.getNote());
        String time[] = note.getTime().split("\\s*- \\s*");
        Time.setText(time[0]);
        if (time.length == 2) {
            Until.setText(time[1]);
        }

        final List<String> jobList = Arrays.asList(note.getJobList().split("\\s*, \\s*"));

        if (jobList.size() > 0) {
            Job.setChecked(true);
        } else {
            Job.setChecked(false);
        }

        final JobListRecyclerviewAdapter jobAdapter = new JobListRecyclerviewAdapter(jobList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(con);
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
                DatePickerDialog DeadlineDate = new DatePickerDialog(con, new DatePickerDialog.OnDateSetListener() {
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
                mTimePicker = new TimePickerDialog(con, new TimePickerDialog.OnTimeSetListener() {
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
                mTimePicker = new TimePickerDialog(con, new TimePickerDialog.OnTimeSetListener() {
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

                    Note note2 = new Note();
                    note2.setId(id);
                    note2.setTitle(title);
                    note2.setJobList(finalJob);
                    note2.setDate(deadline);
                    note2.setNote(note);
                    note2.setDone("false");

                    if (timeuntil.equals("") || timeuntil.equals(" ")) {
                        note2.setTime(time);
                        dataSource.updateNote(note2);
                    } else {
                        note2.setTime(time + " - " + timeuntil);
                        dataSource.updateNote(note2);
                    }
                    Toast.makeText(con, "Successfully added", Toast.LENGTH_SHORT).show();
                    addDialog.dismiss();
                }
            }
        });

        addDialog.show();
        addDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        addDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
}
