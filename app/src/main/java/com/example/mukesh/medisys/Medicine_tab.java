package com.example.mukesh.medisys;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mukesh.medisys.ReminderArch.ReminderArchclass;
import com.example.mukesh.medisys.data.MediSysContract;
import com.example.mukesh.medisys.data.MediSysSQLiteHelper;

import java.util.ArrayList;


public class Medicine_tab extends Fragment {
    RecyclerView listView;

   /* private SharedPreferences sharedread;
    String email=null;
    LinearLayout frag;
    public ArrayList<ReminderArchclass> remArc=new ArrayList<ReminderArchclass>();
    Adapter_medicine adapter;

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


        rootView = inflater.inflate(R.layout.fragment_medicine_records, container, false);
        listView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Medicine_tab.Getreminder getreminder = new Medicine_tab.Getreminder(email);
        getreminder.execute();


*//*
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

                Medicine_tab.Delete_item delete_item=new Medicine_tab.Delete_item(tempremArc);
                delete_item.execute();


            }

        });
        swipeToDismissTouchHelper.attachToRecyclerView(listView);*//*

        // if (remArc.size() == 0) {
        //   View v;

        // System.out.print("ddddd");






        //rootView = inflater.inflate(R.layout.fragment_remindre_empty, container, false);
        //holder.t.setVisibility(View.VISIBLE);
        //}











        return  rootView;
    }
    public class Getreminder extends AsyncTask<Void,Void,ArrayList<ReminderArchclass>> {
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
                        MediSysContract.MedicationEntry.COLUMN_NAME_REMINDER_TIMER,
                        MediSysContract.MedicationEntry.COLUMN_NAME_SKIP,

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
                        String reminder_timer = cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationEntry.COLUMN_NAME_REMINDER_TIMER));
                        String schedule_duration = cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationEntry.COLUMN_NAME_SCHEDULE_DURAtION));
                        String schedule_days = cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationEntry.COLUMN_NAME_SCHEDULE_DAYS));
                        String description = cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationEntry.COLUMN_NAME_DESCRIPTION));
                        String skip=cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationEntry.COLUMN_NAME_SKIP));
                        reminderArchclass.setReminder_timer(reminder_timer);
                        reminderArchclass.setSchedule_duration(schedule_duration);
                        reminderArchclass.setDescription(description);
                        reminderArchclass.setSchedule_days(schedule_days);
                        reminderArchclass.setskip(skip);
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

            adapter=new Adapter_medicine(getActivity(),remArc);
            listView.setAdapter(adapter);
        }
    }



    public class Delete_item extends AsyncTask<Void, Void, Boolean> {
        ReminderArchclass reminderArchclass;
        final MediSysSQLiteHelper mDbHelper = new MediSysSQLiteHelper(getActivity().getApplicationContext());
        Delete_item(ReminderArchclass reminderArchclass) {
            this.reminderArchclass=reminderArchclass;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            //Log.e(password,"eeeeeeeeeeeeee");

            String selection = MediSysContract.MedicationEntry.COLUMN_NAME_REMINDER_TIMER + "= ? AND "+ MediSysContract.MedicationEntry.COLUMN_NAME_DESCRIPTION+"=?";
            String[] selectionArgs = {reminderArchclass.getReminder_timer(),reminderArchclass.getDescription()};
            db.delete(MediSysContract.MedicationEntry.TABLE_NAME,selection,selectionArgs);


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

*/

}

