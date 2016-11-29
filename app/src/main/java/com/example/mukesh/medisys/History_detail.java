package com.example.mukesh.medisys;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.mukesh.medisys.ReminderArch.ReminderArchclass;
import com.example.mukesh.medisys.data.MediSysContract;
import com.example.mukesh.medisys.data.MediSysSQLiteHelper;

import java.util.ArrayList;

public class History_detail extends AppCompatActivity {
TextView Name,Advise,Speciaity;
    String doctor=null;
    String advise=null;
    String speciality=null;
    String unique_id=null;

    public  ArrayList<ReminderArchclass> remArc=new ArrayList<ReminderArchclass>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);
        Advise=(TextView)findViewById(R.id.disease_name) ;
        Name=(TextView)findViewById(R.id.doctor_name) ;
        Speciaity=(TextView)findViewById(R.id.speciality) ;

        Intent intent = getIntent();
        advise = intent.getStringExtra("unique_id");
        //intent.putExtra("unique_id",remArc.get(position).getUnique_id());
        History_detail.GetAdvise getreminder = new History_detail.GetAdvise(advise);
        getreminder.execute();
    }



    public class GetAdvise extends AsyncTask<Void,Void,ArrayList<ReminderArchclass>> {
        String madvise=null;

        public GetAdvise(String madvise){
            this.madvise=madvise;
        }

        final MediSysSQLiteHelper mDbHelper = new MediSysSQLiteHelper(getApplicationContext());



        @Override
        protected ArrayList<ReminderArchclass> doInBackground(Void... params) {
            try {
                SQLiteDatabase db = mDbHelper.getReadableDatabase();

                String selection = MediSysContract.MedicalHistory.COLUMN_NAME_UNIQUE_ID + " = ?";
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
                         doctor = cursor.getString(cursor.getColumnIndex(MediSysContract.MedicalHistory.COLUMN_NAME_DOCTOR));
                         advise = cursor.getString(cursor.getColumnIndex(MediSysContract.MedicalHistory.COLUMN_NAME_ADVISE));
                         speciality = cursor.getString(cursor.getColumnIndex(MediSysContract.MedicalHistory.COLUMN_NAME_SPECIALITY));
                        unique_id=cursor.getString(cursor.getColumnIndex(MediSysContract.MedicalHistory.COLUMN_NAME_UNIQUE_ID));

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

            Name.setText(doctor);
            Advise.setText(advise);
            Speciaity.setText(speciality);

        }
    }
}
