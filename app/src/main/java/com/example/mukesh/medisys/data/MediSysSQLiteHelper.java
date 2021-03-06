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


    private static final String SQL_CREATE_MEDICATIONENTRY =
            "CREATE TABLE " +MediSysContract.MedicationEntry.TABLE_NAME + " (" +
                    MediSysContract.MedicationEntry._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                    MediSysContract.MedicationEntry.COLUMN_NAME_EMAIL +  " TEXT NOT NULL, " +
                    MediSysContract.MedicationEntry.COLUMN_NAME_PRESCRIPTION_UNIQUE_ID +  " TEXT NOT NULL, " +
                    MediSysContract.MedicationEntry.COLUMN_NAME_UNIQUE_ID +  " TEXT NOT NULL, " +
                    MediSysContract.MedicationEntry.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, " +
                    MediSysContract.MedicationEntry.COLUMN_NAME_SCHEDULE_DURAtION + " TEXT NOT NULL, " +
                    MediSysContract.MedicationEntry.COLUMN_NAME_SCHEDULE_DAYS + " TEXT NOT NULL, " +
                    MediSysContract.MedicationEntry.COLUMN_NAME_SKIP+  " TEXT NOT NULL, " +
                    MediSysContract.MedicationEntry.COLUMN_NAME_ALARM_STATUS +  " TEXT NOT NULL " +
                    " )";


    private static final String SQL_CREATE_MEDICATIONREMINDERS =
            "CREATE TABLE " +MediSysContract.MedicationReminders.TABLE_NAME + " (" +
                    MediSysContract.MedicationReminders._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                    MediSysContract.MedicationReminders.COLUMN_NAME_UNIQUE_ID +  " TEXT NOT NULL, " +
                    MediSysContract.MedicationReminders.COLUMN_NAME_UNIQUE_TIMER_ID +  " TEXT NOT NULL, " +
                    MediSysContract.MedicationReminders.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, " +
                    MediSysContract.MedicationReminders.COLUMN_NAME_REMINDER_TIMER+  " TEXT NOT NULL, " +
                    MediSysContract.MedicationEntry.COLUMN_NAME_SKIP+  " TEXT NOT NULL " +
                    " )";

    private static final String SQL_CREATE_MEDICALHISTORY =
            "CREATE TABLE " +MediSysContract.MedicalHistory.TABLE_NAME + " (" +
                    MediSysContract.MedicalHistory.COLUMN_NAME_UNIQUE_ID +  " TEXT NOT NULL, " +
                    MediSysContract.MedicalHistory.COLUMN_NAME_DOCTOR +  " TEXT NOT NULL, " +
                    MediSysContract.MedicalHistory.COLUMN_NAME_SPECIALITY + " TEXT NOT NULL, " +
                    MediSysContract.MedicalHistory.COLUMN_NAME_ADVISE+  " TEXT NOT NULL " +

                    " )";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MediSysContract.MediSysEntry.TABLE_NAME;


    private static final String SQL_DELETE_MEDICATIONENTRY =
            "DROP TABLE IF EXISTS " + MediSysContract.MedicationEntry.TABLE_NAME;

    private static final String SQL_DELETE_MEDICATIOREMINDERS =
            "DROP TABLE IF EXISTS " + MediSysContract.MedicationReminders.TABLE_NAME;
    private static final String SQL_DELETE_MEDICALHISTORY =
            "DROP TABLE IF EXISTS " + MediSysContract.MedicalHistory.TABLE_NAME;

    public MediSysSQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_MEDICATIONENTRY);
        db.execSQL(SQL_CREATE_MEDICATIONREMINDERS);
        db.execSQL(SQL_CREATE_MEDICALHISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_MEDICATIONENTRY);
        db.execSQL(SQL_DELETE_MEDICATIOREMINDERS);
        db.execSQL(SQL_DELETE_MEDICALHISTORY);
        onCreate(db);
    }
}
