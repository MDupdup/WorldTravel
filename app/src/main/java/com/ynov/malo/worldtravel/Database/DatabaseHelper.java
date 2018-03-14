package com.ynov.malo.worldtravel.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Malo on 14/03/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "countries.db";
    private static final int VERSION = 1;

    public DatabaseHelper(Context context) {super(context, DB_NAME, null, VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE countries ( _id integer primary key autoincrement," +
                "name TEXT," +
                "capital_city TEXT," +
                "continent TEXT," +
                "flag BLOB," +
                "date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int previousVersion, int lastVersion) {
        db.execSQL("DROP TABLE IF EXISTS countries");
        onCreate(db);
    }
}
