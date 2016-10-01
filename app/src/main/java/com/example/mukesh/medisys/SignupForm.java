package com.example.mukesh.medisys;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mukesh.medisys.data.MediSysContract;
import com.example.mukesh.medisys.data.MediSysSQLiteHelper;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;


public class SignupForm extends AppCompatActivity {


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    AutoCompleteTextView name;
    AutoCompleteTextView  password;
    AutoCompleteTextView email_id;
    AutoCompleteTextView mobile_no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);
        // Set up the login form.



        name=(AutoCompleteTextView) findViewById(R.id.name);
        password=(AutoCompleteTextView) findViewById(R.id.sign_up_password);
        email_id=(AutoCompleteTextView) findViewById(R.id.email_id);
        mobile_no=(AutoCompleteTextView) findViewById(R.id.number);

       // System.out.println(name.getText().toString()+"baniye");



        Button signup=(Button)findViewById(R.id.sign_up_form_submit);
        signup.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final SignUpTask signUpTask=new SignUpTask(email_id.getText().toString(),name.getText().toString(),password.getText().toString(),mobile_no.getText().toString());
               // System.out.println(email_id.getText().toString());
                signUpTask.execute();
            }
        });

    }
    public class SignUpTask extends AsyncTask<Void, Void, Boolean> {
        private final String email_id;
        private final String name;
        private final String password;
        private final String mobile_no;
        MediSysSQLiteHelper mDbHelper = new MediSysSQLiteHelper(getApplication().getApplicationContext());
        SignUpTask(String email_id, String name, String password, String mobile_no) {
            this.email_id=email_id;
            this.name = name;
            this.password = password;
            this.mobile_no=mobile_no;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            Log.d("SignupForm.java","I am in background");
            System.out.println(email_id+" "+password);
            ContentValues values = new ContentValues();
            values.put(MediSysContract.MediSysEntry.COLUMN_NAME_EMAIL, email_id);
            values.put(MediSysContract.MediSysEntry.COLUMN_NAME_NAME, name);
            values.put(MediSysContract.MediSysEntry.COLUMN_NAME_PASSWORD, password);
            values.put(MediSysContract.MediSysEntry.COLUMN_NAME_MOBILE, mobile_no);

            long newRowId = db.insert(MediSysContract.MediSysEntry.TABLE_NAME, null, values);
            Log.d("SignupForm.java","I am in background");
            System.out.println("background_signup"+newRowId);

//Store basic information in file and send this to user email id
            String fileName = email_id+"profile";
            String content = "Name :-"+name+"\n"+"Mobile no  :-"+mobile_no;

            FileOutputStream outputStream = null;
            try {
                System.out.println("Is there any one 1");
                outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                outputStream.write(content.getBytes());
                outputStream.close();
            } catch (Exception e) {
                System.out.println("Is there any one");
                e.printStackTrace();
            }

            if(newRowId==-1)
                return false;



            return true;
        }




        @Override
        protected void onPostExecute(final Boolean success) {


            if (success) {
                finish();
            } else {
                //Define your problem
            }
        }

        @Override
        protected void onCancelled() {

        }
    }

}

