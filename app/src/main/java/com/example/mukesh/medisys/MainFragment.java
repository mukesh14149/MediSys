package com.example.mukesh.medisys;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Calendar;


public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    android.support.design.widget.FloatingActionButton fab,fab1,fab2,fab3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_main, container, false);
        final TextView current_date=(TextView)view.findViewById(R.id.show_date);
        String temp[]=Calendar.getInstance().getTime().toString().split(" ");
        current_date.setText(temp[0]+" "+temp[1]+" "+temp[2]+" "+temp[5]);
        current_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        final  TextView temp_date=(TextView)view.findViewById(R.id.temp_date);
        temp_date.setText(Calendar.getInstance().getTime().toString());

        ImageView morning=(ImageView)view.findViewById(R.id.Show_morning_details);
        Glide.with(this).load(R.drawable.morning).into((ImageView) view.findViewById(R.id.Show_morning_details));
        morning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),MainFragmentDetails.class);
                intent.putExtra("CurrentDate",temp_date.getText());
                intent.putExtra("position",0);
                startActivity(intent);
            }
        });

        ImageView afternoon=(ImageView)view.findViewById(R.id.Show_afternoon_details);
        Glide.with(this).load(R.drawable.afternoon).into((ImageView) view.findViewById(R.id.Show_afternoon_details));
        afternoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),MainFragmentDetails.class);
                intent.putExtra("CurrentDate",temp_date.getText());
                intent.putExtra("position",1);
                startActivity(intent);
            }
        });


        ImageView evening=(ImageView)view.findViewById(R.id.Show_evening_details);
        Glide.with(this).load(R.drawable.evening).into((ImageView) view.findViewById(R.id.Show_evening_details));
        evening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),MainFragmentDetails.class);
                intent.putExtra("CurrentDate",temp_date.getText());
                intent.putExtra("position",2);
                startActivity(intent);
            }
        });


        ImageView night=(ImageView)view.findViewById(R.id.Show_night_details);
        Glide.with(this).load(R.drawable.night).into((ImageView) view.findViewById(R.id.Show_night_details));
        night.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),MainFragmentDetails.class);
                intent.putExtra("CurrentDate",temp_date.getText());
                intent.putExtra("position",3);
                startActivity(intent);
            }
        });


        fab=(android.support.design.widget.FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab(view);
            }
        });
        fab1=(android.support.design.widget.FloatingActionButton)view.findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRecord(view);
            }
        });
        fab2=(android.support.design.widget.FloatingActionButton)view.findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setReminder(view);
            }
        });

        return view;
    }

    int v=0;
    public void fab(View view) {
        if (v == 0) {
            fab1.setVisibility(View.VISIBLE);
            fab2.setVisibility(View.VISIBLE);

            v = 1;
        } else {
            fab1.setVisibility(View.GONE);
            fab2.setVisibility(View.GONE);
            v = 0;
        }
    }

    public void setReminder(View view) {
        Intent intent=new Intent(getActivity(),AddMedication.class);
        startActivity(intent);
    }
    public void setRecord(View view) {
        Intent intent=new Intent(getActivity(),AddMedical_history.class);
        startActivity(intent);
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
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

            TextView show_date=(TextView) getActivity().findViewById(R.id.show_date);
            TextView temp_date=(TextView) getActivity().findViewById(R.id.temp_date);

            Calendar calendar=Calendar.getInstance();
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,day);
            System.out.println("kyyyy"+calendar.getTime());

            String temp[]=calendar.getTime().toString().split(" ");
            show_date.setText(temp[0]+" "+temp[1]+" "+temp[2]+" "+temp[5]);
            temp_date.setText(calendar.getTime().toString());

        }
    }

}
