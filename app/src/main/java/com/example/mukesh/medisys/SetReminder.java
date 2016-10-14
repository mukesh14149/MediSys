package com.example.mukesh.medisys;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;

public class SetReminder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);
    }
    public void createNotification(View view) {
        // Prepare intent which is triggered if the
        // notification is selected
        ComponentName receiver = new ComponentName(getApplicationContext(), Reciever.class);
        PackageManager pm = getApplicationContext().getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);


        System.out.println("tobha"+Calendar.getInstance().getTimeInMillis());
        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Reciever.class);
        for (int id = 0; id < 3; id++) {
            intent.putExtra("code",id+7);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(this, id, intent, 0);

// set for 30 seconds later
            alarmMgr.set(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis()+(id*10000), alarmIntent);
        }
    }

}
