package com.example.mukesh.medisys;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.mukesh.medisys.data.MediSysContract;
import com.example.mukesh.medisys.data.MediSysSQLiteHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class SignupForm extends AppCompatActivity {




    private AutoCompleteTextView name;
    private AutoCompleteTextView  password;
    private AutoCompleteTextView email_id;
    private AutoCompleteTextView mobile_no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);



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
                Log.i("SignupForm.java","email_id+profile.txt");

            } catch (Exception e) {
                e.printStackTrace();
            }

            BufferedReader input = null;
            File file = null;
            try {
                System.out.println("file is here");
                file = new File(getFilesDir(), fileName); // Pass getFilesDir() and "MyFile" to read file

                input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line;
                StringBuffer buffer = new StringBuffer();
                while ((line = input.readLine()) != null) {
                    buffer.append(line);
                }
                System.out.println("file content:"+buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }



            String string = "privateExternal.txt";

            // Get the directory for the app's private pictures directory.
            File file2 = new File(getExternalFilesDir(
                    Environment.DIRECTORY_DCIM), "privateExternal.txt");

            try {
                FileOutputStream stream = null;
                stream = new FileOutputStream(file2);
                stream.write(string.getBytes());
                stream.close();
                Log.i("SignupForm.java","privateExternal");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }



            string = "publicExternal.txt";

            // Get the directory for the app's private pictures directory.
            File file3 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "publicExternal.txt");

            try {
                FileOutputStream stream = null;
                stream = new FileOutputStream(file3);
                stream.write(string.getBytes());
                stream.close();
                Log.i("SignupForm.java","publicExternal.txt");
            } catch (IOException e) {
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

