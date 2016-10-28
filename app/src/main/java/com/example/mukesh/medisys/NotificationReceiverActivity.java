package com.example.mukesh.medisys;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class NotificationReceiverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_receiver);
        String Action= getIntent().getStringExtra("ACTION");
        Log.i("Viski",Action);

        if(Action.equals("Action_Skip")||Action.equals("Action_Take")){
            Intent intent = new Intent(this, Reciever.class);
            intent.putExtra("code",getIntent().getIntExtra("unique code",1));
            intent.putExtra("Description",intent.getStringExtra("Description"));
            //  intent.putExtra("Time",intent.getIntExtra("Time",1));
            System.out.println("voooooooo"+ getIntent().getIntExtra("unique code",1));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                    getIntent().getIntExtra("unique code",1), intent, 0);
            pendingIntent.cancel();
            AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmMgr.cancel(pendingIntent);

        }
        else if(Action.equals("Action_Snooze")){
            Log.i("Viski","poha");
        }



    }
}
