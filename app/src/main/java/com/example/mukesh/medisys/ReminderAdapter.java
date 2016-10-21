package com.example.mukesh.medisys;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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

/**
 * Created by mukesh on 14/10/16.
 */
public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder>  {


        private Context context;

        ArrayList<ReminderArchclass> remArc;
        public ReminderAdapter(Context context,ArrayList<ReminderArchclass> remArc){
            this.remArc=remArc;
            this.context=context;
        }

        private Context getContext() {
            return context;
        }


        public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            // each data item is just a string in this case
            TextView description;
            ImageButton bel;
            Button TAke;
            Button SKip;
            LinearLayout layout;
            TextView timer;
            TextView date;
            TextView days;

            public ViewHolder(View item) {

                super(item);
                layout = (LinearLayout)item.findViewById(R.id.medicine_details);
                bel=(ImageButton)item.findViewById(R.id.bell);
                bel.setOnClickListener(this);
                TAke=(Button)item.findViewById(R.id.take);
                SKip=(Button)item.findViewById(R.id.skip);
                TAke.setOnClickListener(this);
                SKip.setOnClickListener(this);
                description=(TextView) item.findViewById(R.id.description_view);
                timer=(TextView) item.findViewById(R.id.timer_view);
                date=(TextView) item.findViewById(R.id.schedule_date_view);
                days=(TextView) item.findViewById(R.id.schedule_days_view);



            }
            @Override
            public void onClick(View v) {
                Drawable drawable = bel.getDrawable();

                if (v.getId() == bel.getId()){

                      System.out.println(bel.getTag());
                    if(bel.getTag().equals("on")) {
                        bel.setImageResource(R.drawable.off);
                        Toast.makeText(v.getContext(), "bell", Toast.LENGTH_SHORT).show();
                        bel.setTag("off");
                    }
                    else if(bel.getTag().equals("off")) {
                        bel.setImageResource(R.drawable.bellimage);
                        Toast.makeText(v.getContext(), "belloff", Toast.LENGTH_SHORT).show();
                        bel.setTag("on");
                    }

                } else if (v.getId() == TAke.getId()){
                    Toast.makeText(v.getContext(), "Take", Toast.LENGTH_SHORT).show();

                }else if (v.getId() == SKip.getId()){
                    Toast.makeText(v.getContext(), "Skip", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(v.getContext(), "ROW PRESSED = " + String.valueOf(this), Toast.LENGTH_SHORT).show();
                }
            }
        }





            @Override
            public ReminderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                Context context = parent.getContext();
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.reminder_fragment_item, parent, false);
                ViewHolder viewHolder = new ViewHolder(view);
                return viewHolder;

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
        System.out.println(local);
return local;
    }



    @Override
            public void onBindViewHolder(ReminderAdapter.ViewHolder holder, int position) {

                ReminderArchclass reminderArchclass=remArc.get(position);
                String a=reminderArchclass.getReminder_timer();


int prevTextViewId=2014108;

                int i;
                String[] words=a.split("BBB");
                String[] word = words[0].split(" ");
if(word.length>2) {
    word[3] = word[3].substring(0, (word[3].length() - 3));
    holder.timer.setText(converttime(word[3]));

}
                for(i=1;i<words.length;i++) {
                     word = words[i].split(" ");




                   final  TextView textView = new TextView(getContext());
                    textView.setText(converttime(word[3].substring(0, (word[3].length() - 3))));

                    textView.setTextColor(Color.parseColor("#33B5E5"));
                    textView.setTextSize(20);
                    textView.setTypeface(null, Typeface.BOLD);


                    int curTextViewId = prevTextViewId + 1;
                    textView.setId(curTextViewId);
                    final LinearLayout.LayoutParams params =
                            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                    textView.setLayoutParams(params);
                    prevTextViewId = curTextViewId;

                    holder.layout.addView(textView, params);
                }
                holder.description.setText(reminderArchclass.getDescription());
                holder.date.setText(reminderArchclass.getSchedule_duration());
                holder.days.setText(reminderArchclass.getSchedule_days());
            }

            @Override
            public int getItemCount() {
                return remArc.size();
            }
}
