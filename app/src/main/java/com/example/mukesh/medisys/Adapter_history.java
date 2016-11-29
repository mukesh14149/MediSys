package com.example.mukesh.medisys;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mukesh.medisys.ReminderArch.ReminderArchclass;
import com.example.mukesh.medisys.data.MediSysContract;
import com.example.mukesh.medisys.data.MediSysSQLiteHelper;

import java.util.ArrayList;

/**
 * Created by sujeet on 29/11/16.
 */

public class Adapter_history extends RecyclerView.Adapter<Adapter_history.ViewHolder> {
    public  String naam;
//    public  String skip;


    static private Context context;

    private ArrayList<ReminderArchclass> remArc;

    public Adapter_history(Context context, ArrayList<ReminderArchclass> remArc) {
        this.remArc = remArc;
        this.context = context;
    }

    LinearLayout frag;

    public Context getContext() {
        return context;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        TextView t;
        TextView disease;
        ImageButton bel;
        String skip;
        LinearLayout layout, frag;
        ImageView indic;


        public ViewHolder(View item) {

            super(item);

           /* layout = (LinearLayout) item.findViewById(R.id.medicine_details);
            frag = (LinearLayout) item.findViewById(R.id.parent_frag);*/


            disease = (TextView) item.findViewById(R.id.disease);
         /*   t = (TextView) item.findViewById(R.id.emptyforskip);
            indic = (ImageView) item.findViewById(R.id.indicator);*/

        }




    }


    @Override
    public Adapter_history.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.history_fragment, parent, false);
        Adapter_history.ViewHolder viewHolder = new Adapter_history.ViewHolder(view);
        return viewHolder;

    }





    @Override
    public void onBindViewHolder(Adapter_history.ViewHolder holder, int position) {

        ReminderArchclass reminderArchclass = remArc.get(position);
        // String a = reminderArchclass.getReminder_timer();


        holder.disease.setText(reminderArchclass.getAdvise());
       /* holder.t.setText(reminderArchclass.getskip());*/
        //System.out.println("SUuuu"+reminderArchclass.getskip());
        // holder.skip=reminderArchclass.getskip();




    }





    @Override
    public int getItemCount() {
        return remArc.size();
    }
}

