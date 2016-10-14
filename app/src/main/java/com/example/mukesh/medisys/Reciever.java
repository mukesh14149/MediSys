package com.example.mukesh.medisys;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

/**
 * Created by mukesh on 13/10/16.
 */
public class Reciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        // retrieve the value
            int code=intent.getIntExtra("code", 1);
            String string="yo buddy 1";
            if(code==7)
                string="mml";
            else if(code==8)
                string="dbmml";
            else if(code==9)
                string="tbmml";


            System.out.println("yo buddy"+code);
            Intent i = new Intent(context, NotificationReceiverActivity.class);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), i, 0);
            Notification noti = new Notification.Builder(context)
                    .setContentTitle(string)
                    .setContentText("Subject").setSmallIcon(R.drawable.default_icon)
                    .setContentIntent(pIntent)
                    .addAction(R.drawable.default_icon, "Call", pIntent)
                    .addAction(R.drawable.default_icon, "More", pIntent)
                    .addAction(R.drawable.default_icon, "And more", pIntent).build();
            // hide the notification after its selected
            noti.flags |= Notification.FLAG_AUTO_CANCEL;

            notificationManager.notify(0, noti);

    }
}
