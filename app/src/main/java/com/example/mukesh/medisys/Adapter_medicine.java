package com.example.mukesh.medisys;

import android.content.Context;
import android.content.Intent;
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


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case

        TextView t;
        TextView description;
        ImageButton bel;
        String skip;
        LinearLayout layout, frag;


        public ViewHolder(View item) {

            super(item);

            layout = (LinearLayout) item.findViewById(R.id.medicine_details);
            frag = (LinearLayout) item.findViewById(R.id.parent_frag);
             item.setOnClickListener(this);


            description = (TextView) item.findViewById(R.id.description_view);
           t = (TextView) item.findViewById(R.id.emptyforskip);





        }

        @Override
        public void onClick(View v) {





               Toast.makeText(v.getContext(), "ROW PRESSED = " + String.valueOf(this), Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(context,Medicine_Graph.class);

            intent.putExtra("skip",t.getText() );
            intent.putExtra("name", description.getText());

            context.startActivity(intent);

        }


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
        String a = reminderArchclass.getReminder_timer();


        holder.description.setText(reminderArchclass.getDescription());
       holder.t.setText(reminderArchclass.getskip());
       // holder.skip=reminderArchclass.getskip();
        //
    }





    @Override
    public int getItemCount() {
        return remArc.size();
    }
}
