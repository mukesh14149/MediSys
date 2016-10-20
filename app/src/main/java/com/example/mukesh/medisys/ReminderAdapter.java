package com.example.mukesh.medisys;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mukesh.medisys.ReminderArch.ReminderArchclass;

import java.util.ArrayList;

/**
 * Created by mukesh on 14/10/16.
 */
public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {


        private Context context;
        ArrayList<ReminderArchclass> remArc;
        public ReminderAdapter(Context context,ArrayList<ReminderArchclass> remArc){
            this.remArc=remArc;
        }

        private Context getContext() {
            return context;
        }


        public static class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            TextView description;
            TextView timer;
            TextView date;
            TextView days;
            public ViewHolder(View item) {

                super(item);
                description=(TextView) item.findViewById(R.id.description_view);
                timer=(TextView) item.findViewById(R.id.timer_view);
                date=(TextView) item.findViewById(R.id.schedule_date_view);
                days=(TextView) item.findViewById(R.id.schedule_days_view);

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

            @Override
            public void onBindViewHolder(ReminderAdapter.ViewHolder holder, int position) {
                ReminderArchclass reminderArchclass=remArc.get(position);
                holder.timer.setText(reminderArchclass.getReminder_timer());
                holder.description.setText(reminderArchclass.getDescription());
                holder.date.setText(reminderArchclass.getSchedule_duration());
                holder.days.setText(reminderArchclass.getSchedule_days());
            }

            @Override
            public int getItemCount() {
                return remArc.size();
            }
}
