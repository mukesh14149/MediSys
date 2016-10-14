package com.example.mukesh.medisys;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by mukesh on 14/10/16.
 */
public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {


        private Context context;
        String text;
        public ReminderAdapter(Context context,String string){
            text=string;
        }

        private Context getContext() {
            return context;
        }


        public static class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            TextView textView;
            public ViewHolder(View item) {

                super(item);
                textView=(TextView) item.findViewById(R.id.info_text);

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
                holder.textView.setText(text);
            }

            @Override
            public int getItemCount() {
                return 1;
            }
}
