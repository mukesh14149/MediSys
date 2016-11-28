package com.example.mukesh.medisys;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mukesh.medisys.ReminderArch.ReminderArchclass;
import com.example.mukesh.medisys.data.MediSysContract;
import com.example.mukesh.medisys.data.MediSysSQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by mg on 11/28/16.
 */

public class RecycleAllAlarm extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("In the booting","RecycleAllAlarm");
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // Set the alarm here.

            ReSchduleAlarm setReminder=new ReSchduleAlarm(context);
            setReminder.execute();
        }

    }

    public class ReSchduleAlarm extends AsyncTask<Void, Void, Void> {
        Context context;
        public AlarmManager alarmMgr;
        MediSysSQLiteHelper mDbHelper=null;
        public ReSchduleAlarm(Context context){
            this.context=context;
            mDbHelper = new MediSysSQLiteHelper(context);
        }

        public void setalarm(ReminderArchclass reminderArchclass){
            String description=reminderArchclass.getDescription();
            String schedule_duration=reminderArchclass.getSchedule_duration();
            String schedule_days=reminderArchclass.getSchedule_days();
            String unique_id=reminderArchclass.getUnique_id();
            HashMap<String,String> id_reminder_timer=reminderArchclass.getId_reminder_timer();

            Calendar cal = Calendar.getInstance();
            System.out.println("tobha"+Calendar.getInstance().getTime());
            alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, Reciever.class);

            for(Map.Entry<String,String> info:id_reminder_timer.entrySet()){
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                try {
                    cal.setTime(sdf.parse(id_reminder_timer.get(info.getKey())));// all done
                }catch (Exception e){
                    e.printStackTrace();
                }
                cal.set(Calendar.SECOND, 0);
                System.out.println(id_reminder_timer.get(info.getKey())+"codingninja"+7+"ss"+cal.getTime()+"dd"+cal.getTimeInMillis()+" "+Calendar.getInstance().getTime()+" "+Calendar.getInstance().getTimeInMillis());

                System.out.println( "uuuiiii "+description+" "+" "+schedule_duration+" "+schedule_days);

/*
            for(int i = 0; i < description.length() ; i++){   // while counting characters if less than the length add one
                char character = description.charAt(i); // start on the first character
                int ascii = (int) character; //convert the first character
                tempvalue+=ascii;

            }*/

                Log.i("Check value id_reminder",info.getKey()+"aaa"+id_reminder_timer.get(info.getKey()));
                intent.putExtra("unique_timer_id",Integer.parseInt(info.getKey()));
                intent.putExtra("Description",description);
                intent.putExtra("Time",cal.getTimeInMillis());
                intent.putExtra("schedule_duration", schedule_duration);
                intent.putExtra("schedule_days",schedule_days);
                intent.putExtra("Reminder_timer",id_reminder_timer.get(info.getKey()));
                intent.putExtra("Unique_id",unique_id);
                addReminder(context,Integer.parseInt(info.getKey()),intent,0,cal.getTimeInMillis(),1);

            }

        }
        public void addReminder(Context context,int requestcode, Intent intent, int flag, long triggerAtMills, int type){
            if(type==1) {
                PendingIntent alarmIntent = PendingIntent.getBroadcast(context, requestcode, intent, flag);
                alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtMills, AlarmManager.INTERVAL_DAY, alarmIntent);
            }
        }
        @Override
        protected Void doInBackground(Void... voids) {
            SQLiteDatabase db = mDbHelper.getReadableDatabase();


            String sortOrder =
                    MediSysContract.MedicationEntry.COLUMN_NAME_EMAIL + " DESC";

            String[] projection = {
                    MediSysContract.MedicationEntry.COLUMN_NAME_DESCRIPTION,
                    MediSysContract.MedicationEntry.COLUMN_NAME_SCHEDULE_DAYS,
                    MediSysContract.MedicationEntry.COLUMN_NAME_SCHEDULE_DURAtION,
                    MediSysContract.MedicationEntry.COLUMN_NAME_SKIP,
                    MediSysContract.MedicationEntry.COLUMN_NAME_UNIQUE_ID,
                    MediSysContract.MedicationEntry.COLUMN_NAME_ALARM_STATUS,

            };

            Cursor cursor = db.query(
                    MediSysContract.MedicationEntry.TABLE_NAME,                     // The table to query
                    projection,                               // The columns to return
                    null,                                // The columns for the WHERE clause
                    null,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    sortOrder                                 // The sort order
            );
            if (cursor.getCount() > 0) {

                while(cursor.moveToNext()) {
                    ReminderArchclass reminderArchclass = new ReminderArchclass();
                    if(cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationEntry.COLUMN_NAME_ALARM_STATUS)).equals("true")) {

                        String schedule_duration = cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationEntry.COLUMN_NAME_SCHEDULE_DURAtION));
                        String schedule_days = cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationEntry.COLUMN_NAME_SCHEDULE_DAYS));
                        String description = cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationEntry.COLUMN_NAME_DESCRIPTION));
                        String skip = cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationEntry.COLUMN_NAME_SKIP));
                        String unique_id = cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationEntry.COLUMN_NAME_UNIQUE_ID));

                        reminderArchclass.setSchedule_duration(schedule_duration);
                        reminderArchclass.setDescription(description);
                        reminderArchclass.setSchedule_days(schedule_days);
                        reminderArchclass.setskip(skip);
                        reminderArchclass.setUnique_id(unique_id);


                        String selection_reminder_timer = MediSysContract.MedicationReminders.COLUMN_NAME_UNIQUE_ID + " = ?";
                        String[] selectionArgs_reminder_timer = {unique_id};
                        String sortOrder_reminder_timer =
                                MediSysContract.MedicationReminders.COLUMN_NAME_UNIQUE_TIMER_ID + " DESC";

                        String[] projection_reminder_timer = {
                                MediSysContract.MedicationReminders.COLUMN_NAME_REMINDER_TIMER,
                                MediSysContract.MedicationReminders.COLUMN_NAME_UNIQUE_TIMER_ID,

                        };


                        Cursor cursor_reminder_timer = db.query(
                                MediSysContract.MedicationReminders.TABLE_NAME,                     // The table to query
                                projection_reminder_timer,                               // The columns to return
                                selection_reminder_timer,                                // The columns for the WHERE clause
                                selectionArgs_reminder_timer,                            // The values for the WHERE clause
                                null,                                     // don't group the rows
                                null,                                     // don't filter by row groups
                                sortOrder_reminder_timer                                 // The sort order
                        );


                        if (cursor_reminder_timer.getCount() > 0) {
                            // System.out.println("aaa" + remArc.size());
                            HashMap<String,String>tempreminder=new HashMap<String, String>();
                            while (cursor_reminder_timer.moveToNext()) {
                                String timer = cursor_reminder_timer.getString(cursor_reminder_timer.getColumnIndex(MediSysContract.MedicationReminders.COLUMN_NAME_REMINDER_TIMER));
                                String unique_timer_id=cursor_reminder_timer.getString(cursor_reminder_timer.getColumnIndex(MediSysContract.MedicationReminders.COLUMN_NAME_UNIQUE_TIMER_ID));
                                // timer="Sun Nov 27 04:37:00 GMT+05:30 2016";
                                 tempreminder.put(unique_timer_id,timer);
                            }
                            reminderArchclass.setId_reminder_timer(tempreminder);

                            setalarm(reminderArchclass);

                        }


                    }
                }
            }



            return null;
        }
    }
}