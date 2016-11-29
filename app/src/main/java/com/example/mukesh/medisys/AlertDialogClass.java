package com.example.mukesh.medisys;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by mg on 11/25/16.
 */

public class AlertDialogClass extends AppCompatActivity{
    AlertDialog.Builder mAlertDlgBuilder;
    AlertDialog mAlertDialog;
    View mDialogView = null;
    Button mOKBtn, mCancelBtn;
    EditText snooze_time;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("Check it bro",getIntent().getStringExtra("Description"));
        //setContentView(R.layout.activity_main);
        intent=getIntent();

        LayoutInflater inflater = getLayoutInflater();

        // Build the dialog
        mAlertDlgBuilder = new AlertDialog.Builder(this);
        mDialogView = inflater.inflate(R.layout.activity_alert_dialog, null);
        snooze_time=(EditText)mDialogView.findViewById(R.id.snooze_time);

        mOKBtn = (Button)mDialogView.findViewById(R.id.ID_Ok);
        mCancelBtn = (Button)mDialogView.findViewById(R.id.ID_Cancel);
        mOKBtn.setOnClickListener(mDialogbuttonClickListener);
        mCancelBtn.setOnClickListener(mDialogbuttonClickListener);
        mAlertDlgBuilder.setCancelable(false);
        mAlertDlgBuilder.setInverseBackgroundForced(true);
        mAlertDlgBuilder.setView(mDialogView);
        mAlertDialog = mAlertDlgBuilder.create();
        mAlertDialog.show();

    }
    View.OnClickListener mDialogbuttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.ID_Ok)
            {

                Intent intent1 = new Intent(getApplicationContext(), Reciever.class);
                intent1.putExtra("unique_timer_id",intent.getIntExtra("unique_temp_timer_id",1));
                intent1.putExtra("Description",intent.getStringExtra("Description"));
                intent1.putExtra("Time",intent.getStringExtra("Time"));
                intent1.putExtra("schedule_duration", intent.getStringExtra("schedule_duration"));
                intent1.putExtra("schedule_days",intent.getStringExtra("schedule_days"));
                intent1.putExtra("Reminder_timer",intent.getStringExtra("Reminder_timer"));
                intent1.putExtra("Unique_id",intent.getStringExtra("Unique_id"));
                Log.i("ghj",intent.getStringExtra("schedule_duration"));
                Log.i("what is time",snooze_time.getText().toString());
                int time=Integer.parseInt(snooze_time.getText().toString());
                PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), intent.getIntExtra("unique_temp_timer_id",1), intent1, 0);
                AlarmManager alarmMgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                alarmMgr.set(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis()+(60000*time), alarmIntent);



                mAlertDialog.dismiss();
                finish();
            }
            else if(v.getId() == R.id.ID_Cancel)
            {
                mAlertDialog.dismiss();
                finish();
            }
        }
    };
}