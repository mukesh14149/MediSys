package com.example.mukesh.medisys;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mukesh.medisys.ReminderArch.ReminderArchclass;
import com.example.mukesh.medisys.data.MediSysContract;
import com.example.mukesh.medisys.data.MediSysSQLiteHelper;

import java.util.ArrayList;
import java.util.List;


public class AddMedical_history extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String speciality=null;
    String med=null;
    String doctor=null;
    String advise=null;
    TextView Doctor;
    TextView Advise;
    Intent intent;
    ReminderArchclass reminderArchclass;
    String unique_id=Long.toString(System.currentTimeMillis());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medical_history);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);


        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        Doctor=(TextView)findViewById(R.id.doctor);
        Advise=(TextView)findViewById(R.id.advise);
        try {

            // Spinner Drop down elements
            List<String> categories = new ArrayList<>();
            categories.add("Physician");
            categories.add("Surgion");
            categories.add("Cardiologist");
            categories.add("Dermalogist");
            categories.add("Geriatrics");
            categories.add("General");
            if (getIntent() != null) {
                intent=getIntent();
                Doctor.setText(getIntent().getStringExtra("Doctor"));
                Advise.setText(getIntent().getStringExtra("Advice"));
                med=getIntent().getStringExtra("Medi");
                unique_id=getIntent().getStringExtra("prescription_unique_id");
                spinner.setSelection(categories.indexOf(getIntent().getStringExtra("Category")));

                System.out.println("aaaasdf" + getIntent().getStringExtra("Description")+med);
              //  String[] parts = med.split("=");
int i;
               /*
                for (String retval: med.split("=")) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    frag_history hello = new  frag_history();
                    Bundle bundle=new Bundle();
                    bundle.putString("key",retval);

                    hello.setArguments(bundle);


                    fragmentTransaction.add(R.id.ddupli, hello, "HELLO");

                    fragmentTransaction.commit();
                }*/
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                frag_history hello = new  frag_history();
                Bundle bundle=new Bundle();
                bundle.putString("key",getIntent().getStringExtra("Description"));

                //bundle.putString("time",converttime(s12));



                hello.setArguments(bundle);


                fragmentTransaction.add(R.id.ddupli, hello, "HELLO");
                /*   TextView t =(TextView)findViewById(R.id.percent);
                    TextView t1 =(TextView)findViewById(R.id.percent2);
                    t.setText(converttime(s12));

                    t1.setText(Integer.toString(per(skip))+"%"+"\n");*/
                fragmentTransaction.commit();
                med=med+"="+getIntent().getStringExtra("Description");
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner.setAdapter(dataAdapter);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        speciality = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item

    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    void onclick(View v)
    {
        System.out.println("aaa"+unique_id);

        Intent intent=new Intent(this,AddMedication.class);
        intent.putExtra("unique_id",unique_id);
        intent.putExtra("Doctor",Doctor.getText().toString());
        intent.putExtra("Advice",Advise.getText().toString());
        intent.putExtra("Category",speciality);
        intent.putExtra("Medi",med);
        startActivity(intent);
    }
    public void save_history(View view) {

        doctor = Doctor.getText().toString();
        advise=Advise.getText().toString();

        if(doctor==null||advise==null||speciality==null){
            Toast.makeText(getApplicationContext(), "Some Filled are empty", Toast.LENGTH_SHORT).show();
            System.out.println("holhol");
        }
        else{
            Intent intent=new Intent(this,MainActivity.class);

            AddMedical_history.save_data_history save = new AddMedical_history.save_data_history(doctor, advise, speciality, reminderArchclass,unique_id);
            save.execute();
            startActivity(intent);

        }

    }
    public class save_data_history extends AsyncTask<Void, Void, Boolean> {
        String doctor=null;
        String advise=null;

        String speciality=null;
        String unique_id=null;

        ReminderArchclass reminderArchclass;
        final MediSysSQLiteHelper mDbHelper = new MediSysSQLiteHelper(getApplication().getApplicationContext());
        save_data_history(String doctor, String advise,String speciality,ReminderArchclass reminderArchclass, String unique_id){

            this.doctor=doctor;
            this.advise=advise;
            this.speciality=speciality;

            this.unique_id=unique_id;
            this.reminderArchclass=reminderArchclass;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
               SQLiteDatabase db = mDbHelper.getWritableDatabase();



            ContentValues values = new ContentValues();
            values.put(MediSysContract.MedicalHistory.COLUMN_NAME_UNIQUE_ID,unique_id);
            values.put(MediSysContract.MedicalHistory.COLUMN_NAME_DOCTOR, doctor);
            values.put(MediSysContract.MedicalHistory.COLUMN_NAME_ADVISE, advise);
            values.put(MediSysContract.MedicalHistory.COLUMN_NAME_SPECIALITY, speciality);


            long newRowId;

            newRowId= db.insert(MediSysContract.MedicalHistory.TABLE_NAME, null, values);
          /*  try {
                int t = 149;
                for (String time : reminder_timer) {
                    System.out.println(time);
                    Integer i=(int)(long)Long.parseLong(unique_id);
                    String temp_unique_timer_id = Integer.toString(i + t);
                    ContentValues tempvalue = new ContentValues();
                    tempvalue.put(MediSysContract.MedicalHistory.COLUMN_NAME_DOCTOR, doctor);
                    tempvalue.put(MediSysContract.MedicalHistory.COLUMN_NAME_ADVISE, advise);
                    tempvalue.put(MediSysContract.MedicalHistory.COLUMN_NAME_UNIQUE_ID, unique_id);
                    tempvalue.put(MediSysContract.MedicalHistory.COLUMN_NAME_SPECIALITY, speciality);

                   ;
                    db.insert(MediSysContract.MedicationReminders.TABLE_NAME, null, tempvalue);
                    t++;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            *//*if(flag_for_object==1){
                Log.i("AddMedicationcheck",reminderArchclass.getUnique_id());

                newRowId=db.update(MediSysContract.MedicationEntry.TABLE_NAME,values, MediSysContract.MedicationEntry.COLUMN_NAME_UNIQUE_ID+"="+reminderArchclass.getUnique_id(),null);
            }
            else {
                newRowId= db.insert(MediSysContract.MedicationEntry.TABLE_NAME, null, values);
            }*//*

            System.out.println("yo baby+++++"+newRowId);*/

            return newRowId!=-1;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            System.out.println("yo bddd"+result);
            if(result) {

                Toast.makeText(getApplicationContext(), "Data is  store", Toast.LENGTH_SHORT);
                System.out.println("yo bddd"+result);
            }
            else {
                Toast.makeText(getApplicationContext(), "Data is not store", Toast.LENGTH_SHORT);
            }
        }
    }




}
