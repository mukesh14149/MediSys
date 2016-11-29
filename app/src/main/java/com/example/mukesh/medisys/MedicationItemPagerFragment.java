package com.example.mukesh.medisys;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mukesh.medisys.ReminderArch.ReminderArchclass;

import java.util.ArrayList;


public class MedicationItemPagerFragment extends Fragment {

    public static int prevTextViewId = 2014108;
    ReminderArchclass reminderArchclass;

    public static MedicationItemPagerFragment newInstance(ReminderArchclass reminderArchclass,String currentdate) {
        MedicationItemPagerFragment fragment = new MedicationItemPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("reminderArchclass", reminderArchclass);
        bundle.putString("currentdate",currentdate);
        fragment.setArguments(bundle);
        return fragment;
    }

    public String converttime(String time) {
        String t[] = time.split(":");
        String h = t[0];
        String m = t[1];
        String suffix;
        int var = Integer.parseInt(h);
        if (var < 12) {
            suffix = "AM";
        } else {
            suffix = "PM";
            if (var >= 13) {
                var = var - 12;
            }
        }
        String local = Integer.toString(var) + ":" + m + " " + suffix;
        System.out.println(local);
        return local;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_medication_item_pager, container, false);




        reminderArchclass = getArguments().getParcelable("reminderArchclass");
        Log.i("yo adsjkf", reminderArchclass.getDescription());



        ArrayList<String> words = reminderArchclass.getReminder_timer();
        TextView timer = (TextView) rootView.findViewById(R.id.timer_view);

        TextView description = (TextView) rootView.findViewById(R.id.description_view);
        description.setText(reminderArchclass.getDescription());


        TextView schedule_duration=(TextView)rootView.findViewById(R.id.schedule_days_view);
        schedule_duration.setText(reminderArchclass.getSchedule_duration());

        TextView schedule_days=(TextView)rootView.findViewById(R.id.schedule_date_view);
        schedule_days.setText(reminderArchclass.getSchedule_days());


        ImageButton edit=(ImageButton)rootView.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Edit();

            }
        });

        ImageButton delete=(ImageButton) rootView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Delete(getArguments().get("currentdate").toString());

            }
        });



        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.medicine_details);



        int i;
        //String[] words = a.split("BBB");
        String[] word = words.get(0).split(" ");
        if (word.length > 2) {
            word[3] = word[3].substring(0, (word[3].length() - 3));
            timer.setText(converttime(word[3]));

        }
        for (i = 1; i < words.size(); i++) {
            word = words.get(i).split(" ");

            final TextView textView = new TextView(getContext());
            textView.setText(converttime(word[3].substring(0, (word[3].length() - 3))));

            textView.setTextColor(Color.parseColor("#33B5E5"));
            textView.setTextSize(20);
            textView.setTypeface(null, Typeface.ITALIC);


            int curTextViewId = prevTextViewId + 1;
            textView.setId(curTextViewId);
            final LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(params);
            prevTextViewId = curTextViewId;

            linearLayout.addView(textView, params);

        }
            return rootView;

        }


        public void Edit(){
            Toast.makeText(getContext(), reminderArchclass.getDescription(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), AddMedication.class);
            Bundle b = new Bundle();
            b.putParcelable("ReminderArchclass", reminderArchclass);
            intent.putExtras(b);
            getContext().startActivity(intent);

        }

        public void Delete(String current_date){
            Reminders.Delete_item delete_item=new Reminders.Delete_item(reminderArchclass,getActivity());
            delete_item.execute();

            Intent intent=new Intent(getActivity(),MainFragmentDetails.class);
            intent.putExtra("CurrentDate",current_date);
            startActivity(intent);

        }


}

