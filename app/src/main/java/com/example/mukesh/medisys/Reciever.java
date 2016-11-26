package com.example.mukesh.medisys;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.mukesh.medisys.data.MediSysContract;
import com.example.mukesh.medisys.data.MediSysSQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by mukesh on 13/10/16.
 */
public class Reciever extends BroadcastReceiver {
    public static int  id=2014149;
    @Override
    public void onReceive(Context context, Intent intent) {



        // retrieve the value
        HashMap<Integer,String> week=new HashMap<Integer, String>();

        week.put(2,"Mon");
        week.put(3,"Tue");
        week.put(4,"Wed");
        week.put(5,"Thu");
        week.put(6,"Fri");
        week.put(7,"Sat");
        week.put(1,"Sun");



        Log.i("Reciever.java","See the detail");
        Log.i("Check value id_reminde","aaa"+intent.getStringExtra("Reminder_timer"));


        String string=intent.getStringExtra("Description");
            //System.out.println(string +"yo buddy"+code);

            String dayofweek=intent.getStringExtra("schedule_days");
            String reminder_time=intent.getStringExtra("Reminder_timer");
            Calendar cal = Calendar.getInstance();
            System.out.println(dayofweek+" "+reminder_time+" ");
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
            try {
                cal.setTime(sdf.parse(reminder_time));// all done
            }catch (Exception e){
                e.printStackTrace();
            }

            cal.set(Calendar.SECOND, 0);
            Log.i("Check date",Integer.toString(cal.get(Calendar.DAY_OF_WEEK)));

            if(!intent.getStringExtra("schedule_duration").equals("continuous")){
                cal.add(Calendar.DATE,Integer.parseInt(intent.getStringExtra("schedule_duration")));
                Log.i("We are here",cal.getTime().toString());
                Log.i("We are here1",reminder_time);
                Log.i("We are here2",Calendar.getInstance().getTime().toString());



                if(cal.getTimeInMillis()>=Calendar.getInstance().getTimeInMillis()){
                    if(dayofweek.equals("specific days of week")||dayofweek.contains(week.get(cal.get(Calendar.DAY_OF_WEEK)))){
                        Log.i("I am checking days","voo");
                        show_notiication(intent,string,context);
                    }
                    else
                        Log.i("I am checking days","kat gya");
                }
                else{
                    PendingIntent alarmIntent = PendingIntent.getBroadcast(context, (intent.getIntExtra("unique_timer_id",1)), intent, 0);
                    alarmIntent.cancel();
                    AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    alarmMgr.cancel(alarmIntent);
                }
            }

            else{
              System.out.println(Calendar.SATURDAY+"a"+Calendar.SUNDAY+"b"+Calendar.MONDAY);

                //  System.out.println(dayofweek.contains(week.get(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)))+"aaaaa"+week.get(Calendar.getInstance().get(Calendar.DAY_OF_WEEK))+Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
                if(dayofweek.equals("Every day")||dayofweek.equals("specific days of week")||dayofweek.contains(week.get(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)))){
                    show_notiication(intent,string,context);
                }
            }



    }

    public void show_notiication(Intent intent,String string, Context context){
        Log.i("I am in show_",string);
        int unique=(id+intent.getIntExtra("unique_timer_id",1));
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification noti = new Notification.Builder(context)
                .setContentTitle(string)
                .setContentText("Time to Take Pill").setSmallIcon(R.drawable.bellimage)
                .setContentIntent(getPendingAction(context, intent, unique, "Action_Main"))
                .addAction(R.drawable.bellimage, "Skip", getPendingAction(context, intent, unique, "Action_Skip"))
                .addAction(R.drawable.bellimage, "Snooze",getPendingAction(context, intent, unique, "Action_Snooze"))
                .addAction(R.drawable.bellimage, "Take",getPendingAction(context, intent, unique, "Action_Take")).build();
        // hide the notification after its selected
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(context, notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }


        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(unique, noti);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, (id+intent.getIntExtra("unique_timer_id",1)), intent, 0);
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis()+10000, alarmIntent);

    }

    public PendingIntent getPendingAction(Context context, Intent intent,int unique,String action) {
        // Prepare intent which is triggered if the
        // notification is selected
        Log.i("Check Descript_R",intent.getStringExtra("Description"));
        Intent i = new Intent(context,NotificationReceiverActivity.class);
        i.putExtra("unique_temp_timer_id",id+intent.getIntExtra("unique_timer_id",1));
        i.putExtra("Description",intent.getStringExtra("Description"));
        i.putExtra("Unique_id",intent.getStringExtra("Unique_id"));

        i.putExtra("Time",intent.getStringExtra("Time"));
        i.putExtra("schedule_duration",intent.getStringExtra("schedule_duration"));
        i.putExtra("schedule_days",intent.getStringExtra("schedule_days"));
        i.putExtra("Reminder_timer",intent.getStringExtra("Reminder_timer"));

        i.putExtra("notification_unique_id",unique);
        i.putExtra("ACTION", action);
        i.setAction(action);

        return  PendingIntent.getBroadcast(context, unique, i, PendingIntent.FLAG_UPDATE_CURRENT);

    }


}
