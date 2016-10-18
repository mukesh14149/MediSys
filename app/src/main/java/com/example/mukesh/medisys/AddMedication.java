package com.example.mukesh.medisys;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class AddMedication extends AppCompatActivity {
    ArrayList<TextView> time=new ArrayList<TextView>();
    LinearLayout reminder;
    TextView settime ;

    String email=null;
    String description=null;
    String reminder_timer=null;
    String schedule=null;


    RadioGroup radioduration;
    RadioButton continuous;
    RadioButton number_of_days;

    RadioGroup radiodays;
    RadioButton Everyday;
    RadioButton specific_days_of_week;
    RadioButton days_interval;


    private SharedPreferences sharedread;;
    private EditText editdesc;
    public static int c=0;
    LinearLayout.LayoutParams lprams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        sharedread = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        email=sharedread.getString("email_id","Admin");

        editdesc=(EditText)findViewById(R.id.medication_name);


        System.out.println("in oncreate method 111");
    }

    public void save_medication(View view){

    }


    public void Generate_set_time(View view){
        reminder=(LinearLayout) findViewById(R.id.reminder_inner_layout);

        settime= new TextView(this);
        settime.setText("2:00"+c);
        settime.setId(c++);
        settime.setClickable(true);
        settime.setLayoutParams(lprams);
        System.out.println("yooo"+settime.getId());
        TextView temp_time=(TextView) findViewById(settime.getId());
        time.add(temp_time);
        settime.setOnClickListener(new View.OnClickListener() {
            int k=0;
            TextView t;
            @Override
            public void onClick(View v) {
                System.out.println("in oncreate method"+settime.getId());

                t=(TextView) findViewById(v.getId());;
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddMedication.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        t.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        reminder.addView(settime);
    }

    public class save_data extends AsyncTask<String, Void,Void>{
        save_data(){

        }

        @Override
        protected Void doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
