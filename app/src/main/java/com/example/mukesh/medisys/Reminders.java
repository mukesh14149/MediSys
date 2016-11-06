package com.example.mukesh.medisys;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.mukesh.medisys.ReminderArch.ReminderArchclass;
import com.example.mukesh.medisys.data.MediSysContract;
import com.example.mukesh.medisys.data.MediSysSQLiteHelper;

import java.util.ArrayList;

public class Reminders extends Fragment {
    RecyclerView listView;

    private SharedPreferences sharedread;
    String email=null;

    public  ArrayList<ReminderArchclass> remArc=new ArrayList<ReminderArchclass>();
    ReminderAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment




        sharedread = PreferenceManager.getDefaultSharedPreferences(getActivity());
        email=sharedread.getString("email_id","Admin");
        View rootView;


          // // View view = (View) LayoutInflater.from(ctx).inflate(R.layout.fragment_reminders, null);
            //TextView editText =(TextView)rootView.findViewById(R.id.empty1);
            //editText.setVisibility(View.VISIBLE);

        if (remArc.size() == 0)
            System.out.print("dddd");


        rootView = inflater.inflate(R.layout.fragment_reminders, container, false);
            listView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
            listView.setLayoutManager(new LinearLayoutManager(getActivity()));

            Getreminder getreminder = new Getreminder(email);
            getreminder.execute();



        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
            {
                viewHolder.itemView.setVisibility(View.GONE);

                ReminderArchclass tempremArc;
                tempremArc=remArc.get(viewHolder.getAdapterPosition());

                remArc.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                adapter.notifyItemRangeChanged(viewHolder.getAdapterPosition(),remArc.size());
                System.out.println("Sujeey");

                Delete_item delete_item=new Delete_item(tempremArc,getActivity());
                delete_item.execute();


            }

        });
        swipeToDismissTouchHelper.attachToRecyclerView(listView);

        // if (remArc.size() == 0) {
         //   View v;

           // System.out.print("ddddd");






            //rootView = inflater.inflate(R.layout.fragment_remindre_empty, container, false);
            //holder.t.setVisibility(View.VISIBLE);
        //}











        return  rootView;
    }
    public class Getreminder extends AsyncTask<Void,Void,ArrayList<ReminderArchclass>>{
        String mEmail=null;

        public Getreminder(String mEmail){
            this.mEmail=mEmail;
        }

        final MediSysSQLiteHelper mDbHelper = new MediSysSQLiteHelper(getActivity().getApplicationContext());



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
                                tempreminder.add(timer);
                            }
                            reminderArchclass.setReminder_timer(tempreminder);
                        }




                        remArc.add(reminderArchclass);
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

            adapter=new ReminderAdapter(getActivity(),remArc);
            listView.setAdapter(adapter);
        }
    }



    public static class Delete_item extends AsyncTask<Void, Void, Boolean> {
        ReminderArchclass reminderArchclass;
        Context context;
        final MediSysSQLiteHelper mDbHelper;
        Delete_item(ReminderArchclass reminderArchclass,Context context) {
            this.reminderArchclass=reminderArchclass;
            this.context=context;
            mDbHelper= new MediSysSQLiteHelper(context);
        }


        public void deleteReminder(SQLiteDatabase db) {


            String selection_reminder_timer = MediSysContract.MedicationReminders.COLUMN_NAME_UNIQUE_ID + " = ?";
            String[] selectionArgs_reminder_timer = {reminderArchclass.getUnique_id()};
            String sortOrder_reminder_timer =
                    MediSysContract.MedicationReminders.COLUMN_NAME_UNIQUE_TIMER_ID + " DESC";

            String[] projection_reminder_timer = {
                    MediSysContract.MedicationReminders.COLUMN_NAME_UNIQUE_TIMER_ID,
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

            Intent intent=new Intent(context,Reciever.class);
            intent.putExtra("Description",reminderArchclass.getDescription());
            intent.putExtra("schedule_duration",reminderArchclass.getSchedule_duration());
            intent.putExtra("schedule_days",reminderArchclass.getSchedule_days());
            intent.putExtra("Unique_id",reminderArchclass.getUnique_id());


            if (cursor_reminder_timer.getCount() > 0) {
                // System.out.println("aaa" + remArc.size());
                while (cursor_reminder_timer.moveToNext()) {
                    String timer_id = cursor_reminder_timer.getString(cursor_reminder_timer.getColumnIndex(MediSysContract.MedicationReminders.COLUMN_NAME_UNIQUE_TIMER_ID));
                    String timer=cursor_reminder_timer.getString(cursor_reminder_timer.getColumnIndex(MediSysContract.MedicationReminders.COLUMN_NAME_REMINDER_TIMER));
                    intent.putExtra("unique_timer_id",timer_id);
                    intent.putExtra("Reminder_timer",timer);
                    PendingIntent alarmIntent = PendingIntent.getBroadcast(context, Integer.parseInt(timer_id), intent, 0);
                    alarmIntent.cancel();
                    AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    alarmMgr.cancel(alarmIntent);
                }

            }




        }




        @Override
        protected Boolean doInBackground(Void... params) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            //Log.e(password,"eeeeeeeeeeeeee");

            deleteReminder(db);



          /*  PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    Integer.parseInt(reminderArchclass.getUnique_id()), intent, 0);
            pendingIntent.cancel();
            AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmMgr.cancel(pendingIntent);
*/


            String selection = MediSysContract.MedicationEntry.COLUMN_NAME_UNIQUE_ID + " = ?";
            String[] selectionArgs = {reminderArchclass.getUnique_id()};
            db.delete(MediSysContract.MedicationEntry.TABLE_NAME,selection,selectionArgs);

            selection= MediSysContract.MedicationReminders.COLUMN_NAME_UNIQUE_ID + " = ?";
            db.delete(MediSysContract.MedicationReminders.TABLE_NAME,selection,selectionArgs);


            return true;
        }




        @Override
        protected void onPostExecute(final Boolean success) {


            if (success) {
                //    finish();
            } else {
                //Define your problem
            }
        }

        @Override
        protected void onCancelled() {

        }
    }


}
