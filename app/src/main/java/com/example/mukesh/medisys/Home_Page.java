package com.example.mukesh.medisys;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mukesh.medisys.data.MediSysContract;
import com.example.mukesh.medisys.data.MediSysSQLiteHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Home_Page extends AppCompatActivity {
    ImageView imageView;
    private File output = null;
    private String updated_password;
    SharedPreferences sharedread;
    Menu_Task menu_task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__page);


        imageView = (ImageView) findViewById(R.id.profile_pic);

        File dir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);


        output = new File(dir, "profile_pic.jpeg");

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(dir + "/" + "profile_pic.jpeg", options);
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {

        }

        Button button = (Button) findViewById(R.id.select_image);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                System.out.println(Uri.fromFile(output) + "what a lovely day");
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
                startActivityForResult(takePicture, 0);

            }
        });

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
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("Login_Status", false);
                editor.putString("email_id","");
                editor.commit();
                finish();

            case R.id.delete_account:
                sharedread = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                menu_task=new Menu_Task(getApplication().getApplicationContext(),"delete_account",sharedread.getString("email_id","Default"),null);
                menu_task.execute();
                return  true;

            case R.id.update_account:

                alertdialog();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public void alertdialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Password");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updated_password= input.getText().toString();
                sharedread = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                menu_task=new Menu_Task(getApplication().getApplicationContext(),"update_account",sharedread.getString("email_id","Default"),updated_password);
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
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
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

    public static void StoreImage(Context mContext, Uri imageLoc, File imageDir) {
        Bitmap bm = null;
        try {
            bm = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), imageLoc);
            FileOutputStream out = new FileOutputStream(imageDir);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            bm.recycle();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class Menu_Task extends AsyncTask<Void, Void, Boolean> {

        Context context;
        String type;
        String email;
        String password;

        MediSysSQLiteHelper mDbHelper = new MediSysSQLiteHelper(getApplication().getApplicationContext());
        Menu_Task(Context context, String type, String email, String password) {
            this.context=context;
            this.type=type;
            this.email=email;
            this.password=password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();


            if(type=="delete_account") {
                String selection = MediSysContract.MediSysEntry.COLUMN_NAME_EMAIL + " LIKE ?";
                String[] selectionArgs = {email};
                db.delete(MediSysContract.MediSysEntry.TABLE_NAME, selection, selectionArgs);
            }
            else if(type=="update_account"){
                System.out.println(email+"what is pass"+password);
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