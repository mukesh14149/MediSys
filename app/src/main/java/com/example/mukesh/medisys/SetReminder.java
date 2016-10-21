package com.example.mukesh.medisys;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mukesh.medisys.data.MediSysContract;
import com.example.mukesh.medisys.data.MediSysSQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SetReminder extends AppCompatActivity {

    String description=null;
    String reminder_timer=null;
    String schedule_duration=null;
    String schedule_days=null;
    Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);
        intent1=getIntent();
        createNotification();
        startActivity(new Intent(this,MainActivity.class));
    }
    public void createNotification() {
        // Prepare intent which is triggered if the
        // notification is selected
        ComponentName receiver = new ComponentName(getApplicationContext(), Reciever.class);
        PackageManager pm = getApplicationContext().getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        description=intent1.getStringExtra("Description");
        reminder_timer=intent1.getStringExtra("Reminder_timer");
        schedule_duration=intent1.getStringExtra("Schedule_duration");
        schedule_days=intent1.getStringExtra("Schedule_days");

        System.out.println( "uuuiiii "+description+" "+reminder_timer+" "+schedule_duration+" "+schedule_days);

        String[] temp=reminder_timer.toString().split("BBB");
        Calendar cal = Calendar.getInstance();
        System.out.println("tobha"+Calendar.getInstance().getTime());
        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Reciever.class);
        for (int id = 0; id < temp.length; id++) {

            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
            try {
                cal.setTime(sdf.parse(temp[id]));// all done
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println(temp[id]+"codingninja"+id+7+"ss"+cal.getTime()+"dd"+Calendar.getInstance().getTime());
            intent.putExtra("code",id+7);
            intent.putExtra("Description",description);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(this, id, intent, 0);

// set for 30 seconds later
            alarmMgr.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), alarmIntent);
        }
    }
}
