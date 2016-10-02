package com.example.mukesh.medisys;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mukesh.medisys.data.MediSysContract;
import com.example.mukesh.medisys.data.MediSysSQLiteHelper;


import java.io.File;
import java.io.FileOutputStream;


public class Home_Page extends AppCompatActivity {
    private ImageView imageView;
    private File output = null;
    private String updated_password;
    private SharedPreferences sharedread;
    private Menu_Task menu_task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__page);

        sharedread = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        imageView = (ImageView) findViewById(R.id.profile_pic);

        File dir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);


        output = new File(dir, sharedread.getString("email_id","Admin")+"profile_pic.jpeg");

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(dir + "/" + sharedread.getString("email_id","Admin")+"profile_pic.jpeg", options);
            if(bitmap!=null)
                imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView name=(TextView) findViewById(R.id.profile_name);
        TextView mobile_no=(TextView) findViewById(R.id.profile_mobile_no);
        name.setText(sharedread.getString(getString(R.string.name),"Admin"));
        mobile_no.setText(sharedread.getString(getString(R.string.mobile_no),"9999999999"));


        Button button = (Button) findViewById(R.id.select_image);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
                startActivityForResult(takePicture, 0);

            }
        });

    }
    private void sign_out(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.login_status), false);
        editor.putString(getString(R.string.email_id),"");
        editor.apply();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sign_out:
                sign_out();
                finish();

            case R.id.delete_account:
                sharedread = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                menu_task=new Menu_Task(getString(R.string.delete_account),sharedread.getString(getString(R.string.email_id),"Default"),null);
                menu_task.execute();
                return  true;

            case R.id.update_account:

                alertdialog();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void alertdialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.Update_Password));

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updated_password= input.getText().toString();
                sharedread = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                menu_task=new Menu_Task(getString(R.string.update_account),sharedread.getString(getString(R.string.email_id),"Default"),updated_password);
                menu_task.execute();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {


                    try {
                        StoreImage(this, Uri.parse(imageReturnedIntent.toURI()),
                                output);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finish();
                    startActivity(new Intent(Home_Page.this, Home_Page.class));


                }
                break;
        }
    }

    private static void StoreImage(Context mContext, Uri imageLoc, File imageDir) {
        Bitmap bm;
        try {
            bm = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), imageLoc);
            FileOutputStream out = new FileOutputStream(imageDir);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            bm.recycle();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class Menu_Task extends AsyncTask<Void, Void, Boolean> {

        final String type;
        final String email;
        final String password;

        final MediSysSQLiteHelper mDbHelper = new MediSysSQLiteHelper(getApplication().getApplicationContext());
        Menu_Task(String type, String email, String password) {
            this.type=type;
            this.email=email;
            this.password=password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();


            if(type.equals(getString(R.string.delete_account))) {
                String selection = MediSysContract.MediSysEntry.COLUMN_NAME_EMAIL + " LIKE ?";
                String[] selectionArgs = {email};
                db.delete(MediSysContract.MediSysEntry.TABLE_NAME, selection, selectionArgs);
                sign_out();  //Remove key-value from sharedpref
            }
            else if(type.equals(getString(R.string.update_account))){
                ContentValues values = new ContentValues();
                values.put(MediSysContract.MediSysEntry.COLUMN_NAME_PASSWORD, password);
                String selection = MediSysContract.MediSysEntry.COLUMN_NAME_EMAIL + " LIKE ?";
                String[] selectionArgs = { email };
                int count = db.update(
                        MediSysContract.MediSysEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                System.out.println("ss"+count);

                return false;
            }

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