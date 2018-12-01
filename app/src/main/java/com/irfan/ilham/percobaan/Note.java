package com.irfan.ilham.percobaan;

public class Note {
    private long id;
    private String title;
    private String job;
    private String JobList;
    private String Date;
    private String time;
    private String Note;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String isJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getJobList() {
        return JobList;
    }

    public void setJobList(String jobList) {
        JobList = jobList;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    @Override
    public String toString()
    {
        return "Note "+ id + " " + title +" "+ job + " "+ JobList + " "+ Date + " "+ time + " "+ Note;
    }
}
