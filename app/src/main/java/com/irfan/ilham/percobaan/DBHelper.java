package com.irfan.ilham.percobaan;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    /** deklarasi konstanta-konstanta yang digunakan pada database, seperti nama tabel,
     nama-nama kolom, nama database, dan versi dari database **/
    public static final String TABLE_NAME = "notes";
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String JOB = "job";
    public static final String JOBLIST = "joblist";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String NOTE = "note";
    private static final String db_name ="notes.db";
    private static final int db_version=1;

    // Perintah SQL untuk membuat tabel database baru
    private static final String db_create = "create table "
            + TABLE_NAME + "("
            + ID +" integer primary key autoincrement, "
            + TITLE + " varchar(50) not null, "
            + JOB + " varchar(6) not null, "
            + JOBLIST + " varchar(1000) not null, "
            + DATE + " varchar(50) not null, "
            + TIME + " varchar(20) not null, "
            + NOTE + " varchar(5000) not null);";

    public DBHelper(Context context) {
        super(context, db_name, null, db_version);
        // Auto generated
    }

    //mengeksekusi perintah SQL di atas untuk membuat tabel database baru
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(db_create);
    }

    // dijalankan apabila ingin mengupgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(),"Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
