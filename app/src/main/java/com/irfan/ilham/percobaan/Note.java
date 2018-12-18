package com.irfan.ilham.percobaan;

public class Note {
    private long id;
    private String Title;
    private String JobList;
    private String Date;
    private String Time;
    private String Note;
    private String Done;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
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
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getDone() {
        return Done;
    }

    public void setDone(String done) {
        Done = done;
    }

    @Override
    public String toString()
    {
        return "Note : "+ id + " " + Title +" "+ " "+ JobList + " "+ Date + " "+ Time + " "+ Note +" " + Done;
    }
}
