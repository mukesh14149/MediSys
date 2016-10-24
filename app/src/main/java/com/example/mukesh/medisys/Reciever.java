package com.example.mukesh.medisys;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

/**
 * Created by mukesh on 13/10/16.
 */
public class Reciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        // retrieve the value
            int code=intent.getIntExtra("code", 1);
            String string=intent.getStringExtra("Description");

            System.out.println(string +"yo buddy"+code);
            Intent i = new Intent(context, NotificationReceiverActivity.class);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), i, 0);
            Notification noti = new Notification.Builder(context)
                    .setContentTitle(string)
                    .setContentText("Time to Take Pill").setSmallIcon(R.drawable.bellimage)
                    .setContentIntent(pIntent)

                    /*.addAction(R.drawable.default_icon, "Call", pIntent)
                    .addAction(R.drawable.default_icon, "More", pIntent)
                    .addAction(R.drawable.default_icon, "And more", pIntent)*/.build();
            // hide the notification after its selected
            try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(context, notification);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }


            noti.flags |= Notification.FLAG_AUTO_CANCEL;

            notificationManager.notify(0, noti);

    }
}
