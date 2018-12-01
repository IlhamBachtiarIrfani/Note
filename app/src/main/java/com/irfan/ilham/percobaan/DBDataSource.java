package com.irfan.ilham.percobaan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DBDataSource {
    //inisialiasi SQLite Database
    private SQLiteDatabase database;

    //inisialisasi kelas DBHelper
    private DBHelper dbHelper;

    //ambil semua nama kolom
    private String[] allColumns = { DBHelper.ID,
            DBHelper.TITLE, DBHelper.JOB,DBHelper.JOBLIST, DBHelper.DATE, DBHelper.TITLE, DBHelper.NOTE};

    //DBHelper diinstantiasi pada constructor
    public DBDataSource(Context context)
    {
        dbHelper = new DBHelper(context);
    }

    //membuka/membuat sambungan baru ke database
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    //menutup sambungan ke database
    public void close() {
        dbHelper.close();
    }

    //method untuk create/insert barang ke database
    public Note createNote(String title, String job, String joblist, String date, String time, String note) {

        // membuat sebuah ContentValues, yang berfungsi
        // untuk memasangkan data dengan nama-nama
        // kolom pada database
        ContentValues values = new ContentValues();
        values.put(DBHelper.TITLE, title);
        values.put(DBHelper.JOB, job);
        values.put(DBHelper.JOBLIST, joblist);
        values.put(DBHelper.DATE, date);
        values.put(DBHelper.TIME, time);
        values.put(DBHelper.NOTE, note);

        // mengeksekusi perintah SQL insert data
        // yang akan mengembalikan sebuah insert ID
        long insertId = database.insert(DBHelper.TABLE_NAME, null,
                values);

        // setelah data dimasukkan, memanggil
        // perintah SQL Select menggunakan Cursor untuk
        // melihat apakah data tadi benar2 sudah masuk
        // dengan menyesuaikan ID = insertID
        Cursor cursor = database.query(DBHelper.TABLE_NAME,
                allColumns, DBHelper.ID + " = " + insertId, null,
                null, null, null);

        // pindah ke data paling pertama
        cursor.moveToFirst();

        // mengubah objek pada kursor pertama tadi
        // ke dalam objek barang
        Note newNote= cursorToBarang(cursor);

        // close cursor
        cursor.close();

        // mengembalikan barang baru
        return newNote;
    }

    private Note cursorToBarang(Cursor cursor)
    {
        // buat objek barang baru
        Note note = new Note();
        // debug LOGCAT
        Log.v("info", "The getLONG "+cursor.getLong(0));
        Log.v("info", "The setLatLng "+cursor.getString(1)+","+cursor.getString(5));

        /* Set atribut pada objek barang dengan
         * data kursor yang diambil dari database*/
        note.setId(cursor.getLong(0));
        note.setTitle(cursor.getString(1));
        note.setJob(cursor.getString(2));
        note.setJobList(cursor.getString(3));
        note.setDate(cursor.getString(4));
        note.setTime(cursor.getString(5));
        note.setNote(cursor.getString(6));

        //kembalikan sebagai objek barang
        return note;
    }

    //mengambil semua data barang
    public ArrayList<Note> getAllNote() {
        ArrayList<Note> allnote = new ArrayList<Note>();

        // select all SQL query
        Cursor cursor = database.query(DBHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);

        // pindah ke data paling pertama
        cursor.moveToFirst();
        // jika masih ada data, masukkan data barang ke
        // daftar barang
        while (!cursor.isAfterLast()) {
            Note note = cursorToBarang(cursor);
            allnote.add(note);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return allnote;
    }
}
