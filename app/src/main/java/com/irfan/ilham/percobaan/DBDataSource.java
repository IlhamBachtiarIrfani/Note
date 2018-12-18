package com.irfan.ilham.percobaan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DBDataSource {
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] allColumns = { DBHelper.ID,
            DBHelper.TITLE,DBHelper.JOBLIST, DBHelper.DATE, DBHelper.TIME, DBHelper.NOTE, DBHelper.DONE};

    public DBDataSource(Context context)
    {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Note createNote(String title, String joblist, String date, String time, String note, String done) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.TITLE, title);
        values.put(DBHelper.JOBLIST, joblist);
        values.put(DBHelper.DATE, date);
        values.put(DBHelper.TIME, time);
        values.put(DBHelper.NOTE, note);
        values.put(DBHelper.DONE, done);

        long insertId = database.insert(DBHelper.TABLE_NAME, null,
                values);

        Cursor cursor = database.query(DBHelper.TABLE_NAME,
                allColumns, DBHelper.ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();

        Note newNote= cursorToBarang(cursor);

        cursor.close();

        return newNote;
    }

    private Note cursorToBarang(Cursor cursor)
    {
        Note note = new Note();

        Log.v("info", "The getLONG "+cursor.getLong(0));
        Log.v("info", "The setLatLng "+cursor.getString(1)+","+cursor.getString(5));

        note.setId(cursor.getLong(0));
        note.setTitle(cursor.getString(1));
        note.setJobList(cursor.getString(2));
        note.setDate(cursor.getString(3));
        note.setTime(cursor.getString(4));
        note.setNote(cursor.getString(5));
        note.setDone(cursor.getString(6));

        return note;
    }

    public ArrayList<Note> getAllNote() {
        ArrayList<Note> allnote = new ArrayList<Note>();

        Cursor cursor = database.query(DBHelper.TABLE_NAME,
                allColumns, null, null, null, null, DBHelper.DATE);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Note note = cursorToBarang(cursor);
            allnote.add(note);
            cursor.moveToNext();
        }

        cursor.close();
        return allnote;
    }

    public ArrayList<Note> getNoteByDate(String Day) {
        ArrayList<Note> allnote = new ArrayList<Note>();

        Cursor cursor = database.query(DBHelper.TABLE_NAME,
                allColumns, null, null, null, null, DBHelper.TIME);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Note note = cursorToBarang(cursor);
            if (note.getDate().equals(Day)) {
                allnote.add(note);
            }
            cursor.moveToNext();
        }

        cursor.close();
        return allnote;
    }

    public ArrayList<Note> getNoteByNextDate(String Day, String Day2) {
        ArrayList<Note> allnote = new ArrayList<Note>();

        Cursor cursor = database.query(DBHelper.TABLE_NAME,
                allColumns, null, null, null, null, DBHelper.DATE);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Note note = cursorToBarang(cursor);
            if (note.getDate().equals(Day) || note.getDate().equals(Day2)) {

            } else {
                allnote.add(note);
            }
            cursor.moveToNext();
        }

        cursor.close();
        return allnote;
    }

    public void updateNote(Note n) {
        String strFilter = "_id=" + n.getId();
        ContentValues args = new ContentValues();

        args.put(DBHelper.TITLE, n.getTitle());
        args.put(DBHelper.JOBLIST, n.getJobList());
        args.put(DBHelper.DATE, n.getDate());
        args.put(DBHelper.TIME, n.getTime());
        args.put(DBHelper.NOTE, n.getNote());
        args.put(DBHelper.DONE, n.getDone());

        database.update(DBHelper.TABLE_NAME, args, strFilter, null);
    }

    public Note getNote(long id) {
        Note note = new Note();
        Cursor cursor = database.query(DBHelper.TABLE_NAME, allColumns, "_id ="+id, null, null, null, null);
        cursor.moveToFirst();
        note = cursorToBarang(cursor);
        cursor.close();
        return note;
    }
}
