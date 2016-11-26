package com.example.mukesh.medisys;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mukesh.medisys.ReminderArch.ReminderArchclass;
import com.example.mukesh.medisys.data.MediSysContract;
import com.example.mukesh.medisys.data.MediSysSQLiteHelper;

public class NotificationReceiverActivity extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent_1) {
        String action = intent_1.getAction();

        String skip="";

        Intent intent = new Intent(context, Reciever.class);
        intent.putExtra("unique_timer_id",intent_1.getIntExtra("unique_temp_timer_id",1));
        intent.putExtra("Description",intent_1.getStringExtra("Description"));

        System.out.println(intent_1.getIntExtra("unique_temp_timer_id",1)-2014149+"ss"+intent_1.getStringExtra("Unique_id"));





        if(action.equals("Action_Skip")){

            Toast.makeText(context, "I am in", Toast.LENGTH_SHORT).show();
            Update_skip_take update_skip_take=new Update_skip_take(context,intent_1,"0");
            update_skip_take.execute();
             System.out.println(skip+"0"+"sss"+intent_1.getStringExtra("Description"));
            //  intent.putExtra("Time",intent.getIntExtra("Time",1));
            System.out.println("voooooooo"+ intent_1.getIntExtra("unique_temp_timer_id",1));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                intent_1.getIntExtra("unique_temp_timer_id",1), intent, 0);
            pendingIntent.cancel();
            AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmMgr.cancel(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(intent_1.getIntExtra("notification_unique_id",0));



        } else if(action.equals("Action_Take")){

            Update_skip_take update_skip_take=new Update_skip_take(context,intent_1,"1");
            update_skip_take.execute();
            System.out.println(skip+"1"+"sss"+intent_1.getStringExtra("Description"));
            //  intent.putExtra("Time",intent.getIntExtra("Time",1));
            System.out.println("voooooooo"+ intent_1.getIntExtra("unique_temp_timer_id",1));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, intent_1.getIntExtra("unique_temp_timer_id",1), intent, 0);
            pendingIntent.cancel();
            AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmMgr.cancel(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(intent_1.getIntExtra("notification_unique_id",0));

        }
        else if(action.equals("Action_Snooze")){


            Update_skip_take update_skip_take=new Update_skip_take(context,intent_1,"2");
            update_skip_take.execute();

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, intent_1.getIntExtra("unique_temp_timer_id",1), intent, 0);
            pendingIntent.cancel();
            AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmMgr.cancel(pendingIntent);


            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(intent_1.getIntExtra("notification_unique_id",0));
            displayAlert(context,intent_1);

        }
      //

    }


    private void displayAlert(Context context,Intent intent)
    {
        Intent alarmIntent = new Intent("android.intent.action.MAIN");
        alarmIntent.setClass(context,AlertDialogClass.class);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        alarmIntent.putExtra("unique_temp_timer_id",intent.getIntExtra("unique_temp_timer_id",1));
        alarmIntent.putExtra("Description",intent.getStringExtra("Description"));
        alarmIntent.putExtra("Time",intent.getStringExtra("Time"));
        alarmIntent.putExtra("schedule_duration", intent.getStringExtra("schedule_duration"));
        alarmIntent.putExtra("schedule_days",intent.getStringExtra("schedule_days"));
        alarmIntent.putExtra("Reminder_timer",intent.getStringExtra("Reminder_timer"));
        alarmIntent.putExtra("Unique_id",intent.getStringExtra("Unique_id"));

        context.startActivity(alarmIntent);
    }


    public class Update_skip_take extends AsyncTask<Void,Void,Void>{
        Context context;
        Intent intent_1;
        String Action;
        MediSysSQLiteHelper mDbHelper;
        public  Update_skip_take(Context context,Intent intent_1,String Action){
            this.context=context;
            this.intent_1=intent_1;
            this.Action=Action;
            mDbHelper = new MediSysSQLiteHelper(context);
        }

        public String getSkip(){
            String skip="";
            try {
                SQLiteDatabase db = mDbHelper.getReadableDatabase();

                String selection = MediSysContract.MedicationReminders.COLUMN_NAME_UNIQUE_TIMER_ID+"=?";
                String[] selectionArgs = {Integer.toString(intent_1.getIntExtra("unique_temp_timer_id",1)-2014149)};


                String[] projection = {
                        MediSysContract.MedicationReminders.COLUMN_NAME_UNIQUE_TIMER_ID,

                        MediSysContract.MedicationReminders.COLUMN_NAME_SKIP,


                };


                Cursor cursor = db.query(
                        MediSysContract.MedicationReminders.TABLE_NAME,                     // The table to query
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


                        skip=cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationReminders.COLUMN_NAME_SKIP));

                    }
                }
            }catch (Exception e) {
                Log.i("Reminders", "exception");
            }



            return  skip;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            String skip=getSkip();

            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            String selection =  MediSysContract.MedicationReminders.COLUMN_NAME_UNIQUE_TIMER_ID+"=?";
            String[] selectionArgs = {Integer.toString(intent_1.getIntExtra("unique_temp_timer_id",1)-2014149)};


            ContentValues cv = new ContentValues();
            cv.put("skip",skip+Action);

            db.update(MediSysContract.MedicationReminders.TABLE_NAME,cv,selection,selectionArgs);

            return null;
        }
    }
}
