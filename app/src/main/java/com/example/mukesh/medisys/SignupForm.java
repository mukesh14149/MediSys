package com.example.mukesh.medisys;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.mukesh.medisys.data.MediSysContract;
import com.example.mukesh.medisys.data.MediSysSQLiteHelper;

import java.io.FileOutputStream;


public class SignupForm extends AppCompatActivity {




    private AutoCompleteTextView name;
    private AutoCompleteTextView  password;
    private AutoCompleteTextView email_id;
    private AutoCompleteTextView mobile_no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);




        name=(AutoCompleteTextView) findViewById(R.id.name);
        password=(AutoCompleteTextView) findViewById(R.id.sign_up_password);
        email_id=(AutoCompleteTextView) findViewById(R.id.email_id);
        mobile_no=(AutoCompleteTextView) findViewById(R.id.number);




        Button signup=(Button)findViewById(R.id.sign_up_form_submit);
        signup.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final SignUpTask signUpTask=new SignUpTask(email_id.getText().toString(),name.getText().toString(),password.getText().toString(),mobile_no.getText().toString());
                signUpTask.execute();
            }
        });

    }
    public class SignUpTask extends AsyncTask<Void, Void, Boolean> {
        private final String email_id;
        private final String name;
        private final String password;
        private final String mobile_no;
        final MediSysSQLiteHelper mDbHelper = new MediSysSQLiteHelper(getApplication().getApplicationContext());
        SignUpTask(String email_id, String name, String password, String mobile_no) {
            this.email_id=email_id;
            this.name = name;
            this.password = password;
            this.mobile_no=mobile_no;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(MediSysContract.MediSysEntry.COLUMN_NAME_EMAIL, email_id);
            values.put(MediSysContract.MediSysEntry.COLUMN_NAME_NAME, name);
            values.put(MediSysContract.MediSysEntry.COLUMN_NAME_PASSWORD, password);
            values.put(MediSysContract.MediSysEntry.COLUMN_NAME_MOBILE, mobile_no);

            long newRowId = db.insert(MediSysContract.MediSysEntry.TABLE_NAME, null, values);
            if(newRowId==-1)
                return false;

            //Store basic information in file and send this to user email id
            String fileName = email_id+"profile";
            String content = "Name :-"+name+"\n"+"Mobile no  :-"+mobile_no;

            FileOutputStream outputStream ;
            try {
                outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                outputStream.write(content.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


            return true;
        }


        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {
                finish();
            }
        }

        @Override
        protected void onCancelled() {

        }
    }

}

