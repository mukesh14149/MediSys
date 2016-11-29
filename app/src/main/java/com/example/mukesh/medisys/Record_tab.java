package com.example.mukesh.medisys;
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


public class Record_tab extends Fragment {
    public Record_tab() {
        // Required empty public constructor
    }
    RecyclerView listView;

    private SharedPreferences sharedread;
    String advise=null;
    String doctor=null;
    String speciality=null;

    public  ArrayList<ReminderArchclass> remArc=new ArrayList<ReminderArchclass>();
    Adapter_history adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {





        sharedread = PreferenceManager.getDefaultSharedPreferences(getActivity());
        advise=sharedread.getString("disease","Admin");
        View rootView;


        // // View view = (View) LayoutInflater.from(ctx).inflate(R.layout.fragment_reminders, null);
        //TextView editText =(TextView)rootView.findViewById(R.id.empty1);
        //editText.setVisibility(View.VISIBLE);

        if (remArc.size() == 0)
            System.out.print("dddd");


        rootView =  inflater.inflate(R.layout.fragment_history_records, container, false);
        listView = (RecyclerView) rootView.findViewById(R.id.recycler_view3);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));


        listView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        //ShowPopup(getContext(),view, position);

                        Intent intent=new Intent(getActivity(), History_detail.class);
                        intent.putExtra("unique_id",remArc.get(position).getPrescription_unique_id());
                        startActivity(intent);
                    }
                })
        );

        Record_tab.Getreminder getreminder = new Record_tab.Getreminder(advise);
        getreminder.execute();




        return  rootView;
    }

    public class Getreminder extends AsyncTask<Void,Void,ArrayList<ReminderArchclass>> {
        String madvise=null;

        public Getreminder(String madvise){
            this.madvise=madvise;
        }

        final MediSysSQLiteHelper mDbHelper = new MediSysSQLiteHelper(getActivity().getApplicationContext());



        @Override
        protected ArrayList<ReminderArchclass> doInBackground(Void... params) {
            try {
                SQLiteDatabase db = mDbHelper.getReadableDatabase();

                String selection = MediSysContract.MedicalHistory.COLUMN_NAME_ADVISE + " = ?";
                String[] selectionArgs = {advise};
                String sortOrder =
                        MediSysContract.MedicalHistory.COLUMN_NAME_ADVISE + " DESC";

                String[] projection = {
                        MediSysContract.MedicalHistory.COLUMN_NAME_DOCTOR,

                        MediSysContract.MedicalHistory.COLUMN_NAME_ADVISE,
                        MediSysContract.MedicalHistory.COLUMN_NAME_SPECIALITY,
                        MediSysContract.MedicalHistory.COLUMN_NAME_UNIQUE_ID,


                };


                Cursor cursor = db.query(
                        MediSysContract.MedicalHistory.TABLE_NAME,                     // The table to query
                        projection,                               // The columns to return
                        null,                                // The columns for the WHERE clause
                        null,                            // The values for the WHERE clause
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
                        String doctor = cursor.getString(cursor.getColumnIndex(MediSysContract.MedicalHistory.COLUMN_NAME_DOCTOR));
                        String advise = cursor.getString(cursor.getColumnIndex(MediSysContract.MedicalHistory.COLUMN_NAME_ADVISE));
                        String speciality = cursor.getString(cursor.getColumnIndex(MediSysContract.MedicalHistory.COLUMN_NAME_SPECIALITY));

                        String unique_id=cursor.getString(cursor.getColumnIndex(MediSysContract.MedicalHistory.COLUMN_NAME_UNIQUE_ID));

                        reminderArchclass.setDoctor(doctor);
                        reminderArchclass.setAdvise(advise);
                        reminderArchclass.setSpeciality(speciality);

                        reminderArchclass.setPrescription_unique_id(unique_id);





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

            adapter=new Adapter_history(getActivity(),remArc);
            listView.setAdapter(adapter);
        }
    }




}

