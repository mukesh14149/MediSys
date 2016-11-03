package com.example.mukesh.medisys;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
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
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mukesh.medisys.ReminderArch.ReminderArchclass;
import com.example.mukesh.medisys.data.MediSysContract;
import com.example.mukesh.medisys.data.MediSysSQLiteHelper;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class AddMedication extends AppCompatActivity {
    ArrayList<TextView> time=new ArrayList<TextView>();
    LinearLayout reminder;
    private PopupWindow pwindo;
    Button btnClosePopup;
    TextView settime ;
    Button startdate ;
    String email=null;
    String skip="";
    String description=null;

    String reminder_timer="";
    String schedule_duration=null;
    String schedule_days=null;

    RadioGroup radioduration;
    RadioButton buttonduration;

    RadioGroup radiodays;
    RadioButton buttondays;

    int radio_duration_selected=0;

    String number_of_days="";

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
        try {
            Bundle b = getIntent().getExtras();
            ReminderArchclass reminderArchclass = b.getParcelable("ReminderArchclass");
            Log.i("Check bhai","yo budd");
            System.out.println(reminderArchclass.getDescription());
        }catch (Exception e){
            Log.i("Check bhai","yo");
            e.printStackTrace();
        }



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
        TextView startdate=(TextView)findViewById(R.id.start_date);
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

    public void popupsdow(View view){

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = new PropNumberDialogFragment();
        Bundle args = new Bundle();
        args.putString("type","popupsdow");
        newFragment.setArguments(args);

        newFragment.show(ft, "dialog");
    }


    public void popupnod(View view){


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = new PropNumberDialogFragment();
        Bundle args = new Bundle();
        args.putString("type","popupnod");
        newFragment.setArguments(args);

        newFragment.show(ft, "dialog");
    }

    public static class PropNumberDialogFragment extends DialogFragment {
        NumberPicker numberpicker;
        String selected_no;
        String type="";
        ArrayList<Integer> mSelectedItems;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            //getting proper access to LayoutInflater is the trick. getLayoutInflater is a                   //Function

            type=getArguments().getString("type");
            Log.i("type",type);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            if(type.equals("popupnod")) {
                LayoutInflater inflater = getActivity().getLayoutInflater();

                View view = inflater.inflate(R.layout.number_picker_dialog, null);
                builder.setView(view);
                builder.setTitle("Select number").setNeutralButton(
                        "", null);


                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something else
                        TextView setnod = (TextView) getActivity().findViewById(R.id.set_nod);
                        setnod.setText(selected_no);


                        getDialog().dismiss();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something else
                        getDialog().dismiss();
                    }
                });


                numberpicker = (NumberPicker) view.findViewById(R.id.numberPicker1);
                numberpicker.setMinValue(0);
                numberpicker.setMaxValue(365);
                numberpicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                        //textview.setText("Selected Value is : " + newVal);
                        selected_no = Integer.toString(newVal);
                    }
                });
            }
            else{
                mSelectedItems = new ArrayList();
                builder.setTitle("Pick Items")
                        // Specify the list array, the items to be selected by default (null for none),
                        // and the listener through which to receive callbacks when items are selected
                        .setMultiChoiceItems(R.array.toppings, null,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which,
                                                        boolean isChecked) {
                                        if (isChecked) {
                                            // If the user checked the item, add it to the selected items
                                            mSelectedItems.add(which);
                                        } else if (mSelectedItems.contains(which)) {
                                            // Else, if the item is already in the array, remove it
                                            mSelectedItems.remove(Integer.valueOf(which));
                                        }
                                    }
                                })
                        // Set the action buttons
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK, so save the mSelectedItems results somewhere
                                // or return them to the component that opened the dialog
                                Toast.makeText(getActivity().getApplicationContext(),"mmmml",Toast.LENGTH_LONG).show();
                                String temp="";
                                Log.i("Length of array",Integer.toString(mSelectedItems.size()));
                                    for(Integer i:mSelectedItems){
                                        if(i==0)
                                            temp+="Sun"+" ";
                                        if(i==1)
                                            temp+="Mon"+" ";
                                        if(i==2)
                                            temp+="Tue"+" ";
                                        if(i==3)
                                            temp+="Wed"+" ";
                                        if(i==4)
                                            temp+="Thu"+" ";
                                        if(i==5)
                                            temp+="Fri"+" ";
                                        if(i==6)
                                            temp+="Sat"+" ";
                                    }
                                TextView textView=(TextView)getActivity().findViewById(R.id.set_sdow);
                                textView.setText(temp);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                Toast.makeText(getActivity().getApplicationContext(),"ccccl",Toast.LENGTH_LONG).show();
                            }
                        });
            }
            return builder.create();
        }
    }





    public void save_medication(View view) {
        try {
            description = editdesc.getText().toString();

            radio_duration_selected = radioduration.getCheckedRadioButtonId();
            System.out.println("yo buddy we are done" + radio_duration_selected);

            buttonduration = (RadioButton) findViewById(radio_duration_selected);
            schedule_duration = buttonduration.getText().toString();
            if(schedule_duration.equals("number of days")) {
                TextView temp = (TextView)findViewById(R.id.set_nod);
                schedule_duration=temp.getText().toString();

            }
            Log.i("Schedule_duaration",schedule_duration);

            radio_duration_selected = radiodays.getCheckedRadioButtonId();
            buttondays = (RadioButton) findViewById(radio_duration_selected);
            schedule_days = buttondays.getText().toString();
            if(schedule_days.equals("specific days of week")) {
                TextView temp = (TextView)findViewById(R.id.set_sdow);
                schedule_days=temp.getText().toString();

            }
            Log.i("Schedule_days",schedule_days);



            System.out.println(email + " " + description + " " + reminder_timer + " " + schedule_duration + " " + schedule_days);
            if (email == null || description == null || reminder_timer == null || schedule_duration == null || schedule_days == null) {
                Toast.makeText(getApplication().getApplicationContext(), "Error while updating", Toast.LENGTH_SHORT).show();

            } else {
                save_data save = new save_data(email, description, reminder_timer, schedule_duration, schedule_days,skip);
                save.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            TextView startdate=(TextView) getActivity().findViewById(R.id.start_date);
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
        String local;
        if(var<10) {
             local = "0"+Integer.toString(var) + ":" + m + " " + suffix;
        }
        else
            local = Integer.toString(var) + ":" + m + " " + suffix;
        System.out.println("Date"+local);
        return local;
    }




    public void Generate_set_time(View view){
        reminder=(LinearLayout) findViewById(R.id.reminder_inner_layout);
        lprams.setMargins(0,0,0,15);
        settime= new TextView(this);
        settime.setText("02:00 PM");

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

                TextView startdate=(TextView) findViewById(R.id.start_date);
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
        String skip=null; String unique_id=null;
        final MediSysSQLiteHelper mDbHelper = new MediSysSQLiteHelper(getApplication().getApplicationContext());
        save_data(String email, String description, String reminder_timer, String schedule_duration, String schedule_days,String  skip){
            this.email=email;
            this.description=description;
            this.reminder_timer=reminder_timer;
            this.schedule_duration=schedule_duration;
            this.schedule_days=schedule_days;
            this.skip=skip; this.unique_id=Long.toString(System.currentTimeMillis());
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MediSysContract.MedicationEntry.COLUMN_NAME_UNIQUE_ID,unique_id);
            values.put(MediSysContract.MedicationEntry.COLUMN_NAME_EMAIL, email);
            values.put(MediSysContract.MedicationEntry.COLUMN_NAME_DESCRIPTION, description);
            values.put(MediSysContract.MedicationEntry.COLUMN_NAME_REMINDER_TIMER, reminder_timer);
            values.put(MediSysContract.MedicationEntry.COLUMN_NAME_SCHEDULE_DURAtION, schedule_duration);
            values.put(MediSysContract.MedicationEntry.COLUMN_NAME_SCHEDULE_DAYS, schedule_days);
            values.put(MediSysContract.MedicationEntry.COLUMN_NAME_SKIP,skip);


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
                intent.putExtra("Unique_id",unique_id);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplication().getApplicationContext(), "Data is not store", Toast.LENGTH_SHORT);
            }
        }
    }
}
