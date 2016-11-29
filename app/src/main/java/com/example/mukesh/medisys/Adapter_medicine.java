package com.example.mukesh.medisys;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mukesh.medisys.ReminderArch.ReminderArchclass;
import com.example.mukesh.medisys.data.MediSysContract;
import com.example.mukesh.medisys.data.MediSysSQLiteHelper;

import java.util.ArrayList;
import java.util.List;


public class Adapter_medicine extends RecyclerView.Adapter<Adapter_medicine.ViewHolder> {
    public  String naam;
//    public  String skip;


    static private Context context;

    private ArrayList<ReminderArchclass> remArc;

    public Adapter_medicine(Context context, ArrayList<ReminderArchclass> remArc) {
        this.remArc = remArc;
        this.context = context;
    }

    LinearLayout frag;

    public Context getContext() {
        return context;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case

        TextView t;
        TextView description;
        ImageButton bel;
        String skip;
        LinearLayout layout, frag;
        ImageView indic;


        public ViewHolder(View item) {

            super(item);

            layout = (LinearLayout) item.findViewById(R.id.medicine_details);
            frag = (LinearLayout) item.findViewById(R.id.parent_frag);
           // item.setOnClickListener(this);


            description = (TextView) item.findViewById(R.id.description_view);
            t = (TextView) item.findViewById(R.id.emptyforskip);
            indic = (ImageView) item.findViewById(R.id.indicator);

        }

      /*  @Override
        public void onClick(View v) {





              *//* Toast.makeText(v.getContext(), "ROW PRESSED = " + String.valueOf(this), Toast.LENGTH_SHORT).show();*//*
            Intent intent=new Intent(context,Medicine_Graph.class);

            intent.putExtra("skip",t.getText() );
            intent.putExtra("name", description.getText());

            context.startActivity(intent);

        }
*/

    }


    @Override
    public Adapter_medicine.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.medicine_fragment, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }





    @Override
    public void onBindViewHolder(Adapter_medicine.ViewHolder holder, int position) {

        ReminderArchclass reminderArchclass = remArc.get(position);
       // String a = reminderArchclass.getReminder_timer();


        holder.description.setText(reminderArchclass.getDescription());
       holder.t.setText(reminderArchclass.getskip());
        System.out.println("SUuuu"+reminderArchclass.getskip());
       // holder.skip=reminderArchclass.getskip();








        final MediSysSQLiteHelper mDbHelper = new MediSysSQLiteHelper(context);




        String skip="";



        try {
            SQLiteDatabase db = mDbHelper.getReadableDatabase();

            String selection = MediSysContract.MedicationReminders.COLUMN_NAME_DESCRIPTION+"=?";
            String[] selectionArgs = {reminderArchclass.getDescription()};


            String[] projection = {
                    MediSysContract.MedicationReminders.COLUMN_NAME_DESCRIPTION,

                    MediSysContract.MedicationReminders.COLUMN_NAME_SKIP,

                    MediSysContract.MedicationReminders.COLUMN_NAME_REMINDER_TIMER,


            };


            Cursor cursor = db.query(
                    MediSysContract.MedicationReminders.TABLE_NAME,                     // The table to query
                    projection,                               // The columns to return
                    selection,                                // The columns for the WHERE clause
                    selectionArgs,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    null                                // The sort order
            );
            Log.i("Reminders","cursor count");
            System.out.println(cursor.getCount()+"qaz");
            holder.indic.setImageResource(R.drawable.exclamtion);

            if (cursor.getCount() > 0) {

                while(cursor.moveToNext()) {

                    skip=cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationReminders.COLUMN_NAME_SKIP));
                    if (skip.length() > 0) {
                        char h = skip.charAt(skip.length() - 1);
                        //
                        System.out.println(h+"hi");

                        if (h == '0') {
                            holder.indic.setImageResource(R.drawable.cross);
                        } else if (h == '1') {
                            holder.indic.setImageResource(R.drawable.green);
                        } else {
                            holder.indic.setImageResource(R.drawable.exclamtion);
                        }

                    }




                }
            }
        }catch (Exception e) {
            Log.i("Reminders", "exception");
        }





















    }





    @Override
    public int getItemCount() {
        return remArc.size();
    }
}
