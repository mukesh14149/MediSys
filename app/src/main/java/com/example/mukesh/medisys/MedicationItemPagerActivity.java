package com.example.mukesh.medisys;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.mukesh.medisys.ReminderArch.ReminderArchclass;
import com.example.mukesh.medisys.data.MediSysContract;
import com.example.mukesh.medisys.data.MediSysSQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class MedicationItemPagerActivity extends AppCompatActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    public  ArrayList<ReminderArchclass> remArc=new ArrayList<ReminderArchclass>();


    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;
    List<MedicationItemPagerFragment> fList;
    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;


    private SharedPreferences sharedread;
    String email=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_medication_item_pager);




        // add back arrow to toolbar



        sharedread = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        email=sharedread.getString("email_id","Admin");
        Getreminder showdata_in_page=new Getreminder(email,getIntent().getStringExtra("unique_id"),getIntent().getStringExtra("currentdate"));
        showdata_in_page.execute();


        // Instantiate a ViewPager and a PagerAdapter.

    }


    /**
     * A simple pager adapter that represents 5 MedicationItemPagerFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public  ArrayList<ReminderArchclass> remArc=new ArrayList<ReminderArchclass>();
        public String currentdate;
        public ScreenSlidePagerAdapter(FragmentManager fm, ArrayList<ReminderArchclass>remArc,String currentdate) {
            super(fm);
            this.remArc=remArc;
            this.currentdate=currentdate;
        }

        @Override
        public Fragment getItem(int position) {
            return MedicationItemPagerFragment.newInstance(remArc.get(position),currentdate);
        }

        @Override
        public int getCount() {
            return remArc.size();
        }
    }


    public class Getreminder extends AsyncTask<Void,Void,ArrayList<ReminderArchclass>>{
        String mEmail=null;
        Integer position=0;
        String uniqueid;
        String currentdate;
        public Getreminder(String mEmail,String unique_id,String currentdate){
            this.mEmail=mEmail;
            this.uniqueid=unique_id;
            this.currentdate=currentdate;
        }

        final MediSysSQLiteHelper mDbHelper = new MediSysSQLiteHelper(getApplicationContext());



        @Override
        protected ArrayList<ReminderArchclass> doInBackground(Void... params) {
            try {
                SQLiteDatabase db = mDbHelper.getReadableDatabase();

                String selection = MediSysContract.MedicationEntry.COLUMN_NAME_EMAIL + " = ?";
                String[] selectionArgs = {mEmail};
                String sortOrder =
                        MediSysContract.MedicationEntry.COLUMN_NAME_EMAIL + " DESC";

                String[] projection = {
                        MediSysContract.MedicationEntry.COLUMN_NAME_DESCRIPTION,
                        MediSysContract.MedicationEntry.COLUMN_NAME_SCHEDULE_DAYS,
                        MediSysContract.MedicationEntry.COLUMN_NAME_SCHEDULE_DURAtION,
                        MediSysContract.MedicationEntry.COLUMN_NAME_SKIP,
                        MediSysContract.MedicationEntry.COLUMN_NAME_UNIQUE_ID,


                };


                Cursor cursor = db.query(
                        MediSysContract.MedicationEntry.TABLE_NAME,                     // The table to query
                        projection,                               // The columns to return
                        selection,                                // The columns for the WHERE clause
                        selectionArgs,                            // The values for the WHERE clause
                        null,                                     // don't group the rows
                        null,                                     // don't filter by row groups
                        sortOrder                                 // The sort order
                );
                Log.i("Reminders","cursor count");
                System.out.println(cursor.getCount());

                if (cursor.getCount() > 0) {
                    System.out.println("aaa"+remArc.size());
                    while(cursor.moveToNext()) {
                        ReminderArchclass reminderArchclass = new ReminderArchclass();
                        String schedule_duration = cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationEntry.COLUMN_NAME_SCHEDULE_DURAtION));
                        String schedule_days = cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationEntry.COLUMN_NAME_SCHEDULE_DAYS));
                        String description = cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationEntry.COLUMN_NAME_DESCRIPTION));
                        String skip=cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationEntry.COLUMN_NAME_SKIP));
                        String unique_id=cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationEntry.COLUMN_NAME_UNIQUE_ID));

                        reminderArchclass.setSchedule_duration(schedule_duration);
                        reminderArchclass.setDescription(description);
                        reminderArchclass.setSchedule_days(schedule_days);
                        reminderArchclass.setskip(skip);
                        reminderArchclass.setUnique_id(unique_id);


                        if(uniqueid.equals(unique_id)) {

                            System.out.println(uniqueid+"aaaaaaaaaaaaaaaaaaaaaaaaaa"+description);

                            position=cursor.getPosition();
                        }
                        System.out.println(position+"afdjk");




                        String selection_reminder_timer = MediSysContract.MedicationReminders.COLUMN_NAME_UNIQUE_ID + " = ?";
                        String[] selectionArgs_reminder_timer = {unique_id};
                        String sortOrder_reminder_timer =
                                MediSysContract.MedicationReminders.COLUMN_NAME_UNIQUE_TIMER_ID + " DESC";

                        String[] projection_reminder_timer = {
                                MediSysContract.MedicationReminders.COLUMN_NAME_REMINDER_TIMER,

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
                            ArrayList<String> tempreminder=new ArrayList<String>();
                            while (cursor_reminder_timer.moveToNext()) {
                                String timer = cursor_reminder_timer.getString(cursor_reminder_timer.getColumnIndex(MediSysContract.MedicationReminders.COLUMN_NAME_REMINDER_TIMER));
                                Log.i("Check timeReminder",Integer.toString(position));
                                // timer="Sun Nov 27 04:37:00 GMT+05:30 2016";
                                String date[]=timer.split(" ");
                                String time[]=date[3].split(":");
                                System.out.println(Integer.parseInt(time[0]));




                                tempreminder.add(timer);
                            }
                                reminderArchclass.setReminder_timer(tempreminder);
                                remArc.add(reminderArchclass);

                        }




                    }
                }
            }catch (Exception e){
                Log.i("Reminders","exception");

                e.printStackTrace();
            }
            return remArc;
        }



        @Override

        protected void onPostExecute(ArrayList<ReminderArchclass> remArc) {
            if(!remArc.isEmpty())
                System.out.println(remArc.size()+"uuuuuuu"+remArc.get(0).getDescription()+remArc.get(0).getReminder_timer());
            mPager = (ViewPager) findViewById(R.id.pager);
            mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), remArc, currentdate);
            mPager.setAdapter(mPagerAdapter);
            mPager.setCurrentItem(position);
        }
    }

}

