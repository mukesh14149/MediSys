package com.example.mukesh.medisys;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mukesh.medisys.ReminderArch.ReminderArchclass;
import com.example.mukesh.medisys.data.MediSysContract;
import com.example.mukesh.medisys.data.MediSysSQLiteHelper;

public class NotificationReceiverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_receiver);
        String Action= getIntent().getStringExtra("ACTION");
        Log.i("Viski",Action);
        String skip="";



            Intent intent = new Intent(this, Reciever.class);
            intent.putExtra("unique_timer_id",getIntent().getIntExtra("unique_temp_timer_id",1));
            intent.putExtra("Description",getIntent().getStringExtra("Description"));



            final MediSysSQLiteHelper mDbHelper = new MediSysSQLiteHelper(getApplication().getApplicationContext());








            try {
                SQLiteDatabase db = mDbHelper.getReadableDatabase();

                String selection = MediSysContract.MedicationEntry.COLUMN_NAME_DESCRIPTION+"=?";
                String[] selectionArgs = {getIntent().getStringExtra("Description")};


                String[] projection = {
                        MediSysContract.MedicationEntry.COLUMN_NAME_DESCRIPTION,

                        MediSysContract.MedicationEntry.COLUMN_NAME_SKIP,


                };


                Cursor cursor = db.query(
                        MediSysContract.MedicationEntry.TABLE_NAME,                     // The table to query
                        projection,                               // The columns to return
                        selection,                                // The columns for the WHERE clause
                        selectionArgs,                            // The values for the WHERE clause
                        null,                                     // don't group the rows
                        null,                                     // don't filter by row groups
                        null                                // The sort order
                );
                Log.i("Reminders","cursor count");
                System.out.println(cursor.getCount());

                if (cursor.getCount() > 0) {

                    while(cursor.moveToNext()) {


                         skip=cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationEntry.COLUMN_NAME_SKIP));

                    }
                }
            }catch (Exception e) {
                Log.i("Reminders", "exception");
            }




































            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            String selection =  MediSysContract.MedicationEntry.COLUMN_NAME_DESCRIPTION+"=?";
            String[] selectionArgs = {getIntent().getStringExtra("Description")};


            ContentValues cv = new ContentValues();

        if(Action.equals("Action_Skip")){

            cv.put("skip",skip+"0");

            db.update(MediSysContract.MedicationEntry.TABLE_NAME,cv,selection,selectionArgs);
           System.out.println(skip+"0"+"sss"+getIntent().getStringExtra("Description"));
            //  intent.putExtra("Time",intent.getIntExtra("Time",1));
            System.out.println("voooooooo"+ getIntent().getIntExtra("unique_temp_timer_id",1));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                    getIntent().getIntExtra("unique_temp_timer_id",1), intent, 0);
            pendingIntent.cancel();
            AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmMgr.cancel(pendingIntent);

        } else if(Action.equals("Action_Take")){

            cv.put("skip",skip+"1");

            db.update(MediSysContract.MedicationEntry.TABLE_NAME,cv,selection,selectionArgs);
            System.out.println(skip+"1"+"sss"+getIntent().getStringExtra("Description"));
            //  intent.putExtra("Time",intent.getIntExtra("Time",1));
            System.out.println("voooooooo"+ getIntent().getIntExtra("unique_temp_timer_id",1));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                    getIntent().getIntExtra("unique_temp_timer_id",1), intent, 0);
            pendingIntent.cancel();
            AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmMgr.cancel(pendingIntent);

        }
        else if(Action.equals("Action_Snooze")){


            cv.put("skip",skip+"2");

            db.update(MediSysContract.MedicationEntry.TABLE_NAME,cv,selection,selectionArgs);
        }



    }
}
