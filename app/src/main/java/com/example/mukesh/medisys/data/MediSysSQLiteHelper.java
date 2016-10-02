package com.example.mukesh.medisys.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mukesh on 1/10/16.
 */
public class MediSysSQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MediSys.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " +MediSysContract.MediSysEntry.TABLE_NAME + " (" +
                    MediSysContract.MediSysEntry._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                    MediSysContract.MediSysEntry.COLUMN_NAME_EMAIL +  " TEXT NOT NULL, " +
                    MediSysContract.MediSysEntry.COLUMN_NAME_NAME +  " TEXT NOT NULL, " +
                    MediSysContract.MediSysEntry.COLUMN_NAME_PASSWORD + " TEXT NOT NULL, " +
                    MediSysContract.MediSysEntry.COLUMN_NAME_MOBILE +  " TEXT NOT NULL " +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MediSysContract.MediSysEntry.TABLE_NAME;


    public MediSysSQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
