package com.example.mukesh.medisys;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mukesh.medisys.data.MediSysContract;
import com.example.mukesh.medisys.data.MediSysSQLiteHelper;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddMedication extends AppCompatActivity {
    ArrayList<TextView> time=new ArrayList<TextView>();
    LinearLayout reminder;
    private PopupWindow pwindo;
    Button btnClosePopup;
    TextView settime ;
    Button startdate ;
    String email=null;
    String description=null;

    String reminder_timer="";
    String schedule_duration=null;
    String schedule_days=null;

    RadioGroup radioduration;
    RadioButton buttonduration;

    RadioGroup radiodays;
    RadioButton buttondays;

    int radio_duration_selected=0;

    private SharedPreferences sharedread;
    private EditText editdesc;
    public int c=2014149;
    LinearLayout.LayoutParams lprams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);

    ImageButton hide1;

    int x=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        hide1=(ImageButton)findViewById(R.id.imageButton2);
        sharedread = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        email=sharedread.getString("email_id","Admin");

        editdesc=(EditText)findViewById(R.id.medication_name);
        View panelProfile = findViewById(R.id.hide_schdule);
        panelProfile.setVisibility(View.GONE);
        radioduration=(RadioGroup)findViewById(R.id.radio_duration);
        radiodays=(RadioGroup)findViewById(R.id.radio_days);

        Calendar currentTime = Calendar.getInstance();
        Button startdate=(Button)findViewById(R.id.start_date);
        startdate.setText(currentTime.get(Calendar.YEAR)+"-"+(currentTime.get(Calendar.MONTH)+1)+"-"+currentTime.get(Calendar.DAY_OF_MONTH));

        System.out.println("in oncreate method 111");

        hide1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DO STUFF
                View panelProfile = findViewById(R.id.hide_schdule);
                if(x==0){
                    panelProfile.setVisibility(View.VISIBLE);
                    hide1.setImageResource(R.drawable.open);
                    x=1;}
                else {
                    panelProfile.setVisibility(View.GONE);
                    hide1.setImageResource(R.drawable.close);
                    x=0;

                }




            }
        });
    }

    public void save_medication(View view) {
        try {
            description = editdesc.getText().toString();

            radio_duration_selected = radioduration.getCheckedRadioButtonId();
            System.out.println("yo buddy we are done" + radio_duration_selected);

            buttonduration = (RadioButton) findViewById(radio_duration_selected);
            schedule_duration = buttonduration.getText().toString();

            radio_duration_selected = radiodays.getCheckedRadioButtonId();
            buttondays = (RadioButton) findViewById(radio_duration_selected);
            schedule_days = buttondays.getText().toString();

            System.out.println(email + " " + description + " " + reminder_timer + " " + schedule_duration + " " + schedule_days);
            if (email == null || description == null || reminder_timer == null || schedule_duration == null || schedule_days == null) {
                Toast.makeText(getApplication().getApplicationContext(), "Error while updating", Toast.LENGTH_SHORT).show();

            } else {
                save_data save = new save_data(email, description, reminder_timer, schedule_duration, schedule_days);
                save.execute();
            }
        } catch (Exception e) {
            Toast.makeText(getApplication().getApplicationContext(), "Some Filled are empty", Toast.LENGTH_SHORT).show();
        }

    }
    private void initiatePopupWindow() {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) AddMedication.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.add_done,
                    (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, 300, 370, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

            btnClosePopup = (Button) layout.findViewById(R.id.btn_close_popup);
            btnClosePopup.setOnClickListener(cancel_button_click_listener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private View.OnClickListener cancel_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            pwindo.dismiss();

        }
    };




    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            Button startdate=(Button)getActivity().findViewById(R.id.start_date);
            month++;
            startdate.setText(year+"-"+month+"-"+day);
            System.out.println("kyyyy"+year+" "+month+" "+day);

        }
    }

    public String converttime(String time){
        String t[]=time.split(":");
        String h=t[0];
        String m=t[1];
        String suffix;
        int var=Integer.parseInt(h);
        if(var<12)
        {
            suffix="AM";
        }
        else{
            suffix="PM";
            if(var>=13)
            {
                var=var-12;
            }
        }
        String local=Integer.toString(var)+":"+m+" "+suffix;
        System.out.println("Date"+local);
        return local;
    }

    public void Generate_set_time(View view){
        reminder=(LinearLayout) findViewById(R.id.reminder_inner_layout);
        lprams.setMargins(0,0,0,15);
        settime= new TextView(this);
        settime.setText("2:00");

        settime.setId(c++);

        settime.setTextColor(Color.parseColor("#33B5E5"));
        settime.setTextSize(20);
        settime.setTypeface(null, Typeface.BOLD);


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

                Button startdate=(Button)findViewById(R.id.start_date);
                String []te=startdate.getText().toString().split("-");
                final int Year=Integer.parseInt(te[0]);
                final int Month=Integer.parseInt(te[1]);
                final int Day=Integer.parseInt(te[2]);
                System.out.println(Year+" "+Month+" "+Day+"in oncreate method"+settime.getId());



                t=(TextView) findViewById(v.getId());

                final Calendar beginCal = Calendar.getInstance();

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddMedication.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
System.out.println(selectedHour + ":" + selectedMinute+"mml");
                        t.setText( converttime(selectedHour + ":" + selectedMinute));

                        beginCal.set(Year,Month-1, Day, selectedHour, selectedMinute);
                      //  startdate.setText(beginCal.getTime().toString());
                        reminder_timer+=beginCal.getTime().toString()+"BBB";
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");

                mTimePicker.show();
            }
        });
        reminder.addView(settime);
    }

    public class save_data extends AsyncTask<Void, Void, Boolean>{
        String email=null;
        String description=null;
        String reminder_timer=null;
        String schedule_duration=null;
        String schedule_days=null;
        final MediSysSQLiteHelper mDbHelper = new MediSysSQLiteHelper(getApplication().getApplicationContext());
        save_data(String email, String description, String reminder_timer, String schedule_duration, String schedule_days){
            this.email=email;
            this.description=description;
            this.reminder_timer=reminder_timer;
            this.schedule_duration=schedule_duration;
            this.schedule_days=schedule_days;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MediSysContract.MedicationEntry.COLUMN_NAME_EMAIL, email);
            values.put(MediSysContract.MedicationEntry.COLUMN_NAME_DESCRIPTION, description);
            values.put(MediSysContract.MedicationEntry.COLUMN_NAME_REMINDER_TIMER, reminder_timer);
            values.put(MediSysContract.MedicationEntry.COLUMN_NAME_SCHEDULE_DURAtION, schedule_duration);
            values.put(MediSysContract.MedicationEntry.COLUMN_NAME_SCHEDULE_DAYS, schedule_days);

            long newRowId = db.insert(MediSysContract.MedicationEntry.TABLE_NAME, null, values);
            System.out.println("yo baby"+newRowId);

            return newRowId != -1;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            System.out.println("yo bddd"+result);
            if(result) {
              //  Toast.makeText(getApplication().getApplicationContext(), "Data is store", Toast.LENGTH_SHORT);
                Intent intent=new Intent(AddMedication.this,SetReminder.class);
                intent.putExtra("Description",description);
                intent.putExtra("Reminder_timer",reminder_timer);
                intent.putExtra("Schedule_duration",schedule_duration);
                intent.putExtra("Schedule_days",schedule_days);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplication().getApplicationContext(), "Data is not store", Toast.LENGTH_SHORT);
            }
        }
    }
}
