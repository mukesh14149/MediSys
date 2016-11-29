package com.example.mukesh.medisys;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.mukesh.medisys.ReminderArch.ReminderArchclass;
import com.example.mukesh.medisys.data.MediSysContract;
import com.example.mukesh.medisys.data.MediSysSQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Reminders extends Fragment {

    public static Reminders newInstance(int position, String current_date) {
        Reminders fragment = new Reminders();
        Bundle bundle=new Bundle();
        bundle.putInt("Position",position);
        bundle.putString("CurrentDate",current_date);
        fragment.setArguments(bundle);
        return fragment;
    }


    RecyclerView listView;

    private SharedPreferences sharedread;
    String email=null;


    public  ArrayList<ReminderArchclass> remArc=new ArrayList<ReminderArchclass>();
    ReminderAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        int position=getArguments().getInt("Position");
        System.out.println("aaasdfs"+position+ Calendar.getInstance().getTime());
        sharedread = PreferenceManager.getDefaultSharedPreferences(getActivity());
        email=sharedread.getString("email_id","Admin");
        View rootView;


          // // View view = (View) LayoutInflater.from(ctx).inflate(R.layout.fragment_reminders, null);
            //TextView editText =(TextView)rootView.findViewById(R.id.empty1);
            //editText.setVisibility(View.VISIBLE);

        if (remArc.size() == 0)
            System.out.print("dddd");


        rootView = inflater.inflate(R.layout.fragment_reminders, container, false);
        Toolbar toolbar = (Toolbar)rootView.findViewById(R.id.toolbar);
       /* AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
       */ initCollapsingToolbar(rootView);

        String temp_date=getArguments().getString("CurrentDate");
        TextView date_view=(TextView)rootView.findViewById(R.id.show_date);
        String temp[]=temp_date.split(" ");
        date_view.setText(temp[0]+" "+temp[1]+" "+temp[2]);

        TextView set_day=(TextView)rootView.findViewById(R.id.set_day);
        TextView set_day_type=(TextView)rootView.findViewById(R.id.show_day_part);

        try {
            if(position==0){
                Glide.with(this).load(R.drawable.morning).into((ImageView) rootView.findViewById(R.id.backdrop));
                set_day.setText("Morning");
                set_day_type.setText("4:00 AM - 12:00 PM");
            }
            else if(position==1){
                Glide.with(this).load(R.drawable.afternoon).into((ImageView) rootView.findViewById(R.id.backdrop));
                set_day.setText("Afternoon");
                set_day_type.setText("12:00 PM - 6:00 PM");
            }
            else if(position==2){
                Glide.with(this).load(R.drawable.evening).into((ImageView) rootView.findViewById(R.id.backdrop));
                set_day.setText("Evening");
                set_day_type.setText("6:00 PM - 12:00 AM");
            }
            else if(position==3){
                Glide.with(this).load(R.drawable.night).into((ImageView) rootView.findViewById(R.id.backdrop));
                set_day.setText("Night");
                set_day_type.setText("12:00 AM - 4:00 AM");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        listView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            listView.setLayoutManager(mLayoutManager);
           /* listView.addItemDecoration(new GridSpacingItemDecoration(10, dpToPx(10), true));
            listView.setItemAnimator(new DefaultItemAnimator());
*/
            Getreminder getreminder = new Getreminder(email,position,getArguments().getString("CurrentDate"));
            getreminder.execute();




       /* ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
            {
                viewHolder.itemView.setVisibility(View.GONE);

                ReminderArchclass tempremArc;
                tempremArc=remArc.get(viewHolder.getAdapterPosition());

                remArc.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                adapter.notifyItemRangeChanged(viewHolder.getAdapterPosition(),remArc.size());
                System.out.println("Sujeey");

                Delete_item delete_item=new Delete_item(tempremArc,getActivity());
                delete_item.execute();


            }

        });
        swipeToDismissTouchHelper.attachToRecyclerView(listView);*/
        listView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        //ShowPopup(getContext(),view, position);
                        Intent intent=new Intent(getActivity(), MedicationItemPagerActivity.class);
                        intent.putExtra("unique_id",remArc.get(position).getUnique_id());
                        intent.putExtra("currentdate",getArguments().getString("CurrentDate"));
                        startActivity(intent);
                    }
                })
        );




        // if (remArc.size() == 0) {
         //   View v;

           // System.out.print("ddddd");






            //rootView = inflater.inflate(R.layout.fragment_remindre_empty, container, false);
            //holder.t.setVisibility(View.VISIBLE);
        //}











        return  rootView;
    }

    public void ShowPopup(Context mContext,View view, int position){
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.medication_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.show();
    }


    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        ReminderArchclass reminderArchclass;
        int position;
        public MyMenuItemClickListener(int position) {
            this.reminderArchclass=remArc.get(position);
            this.position=position;
        }

        public void Edit(){
            Toast.makeText(getContext(), reminderArchclass.getDescription(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), AddMedication.class);
            Bundle b = new Bundle();
            b.putParcelable("ReminderArchclass", reminderArchclass);
            intent.putExtras(b);
            getContext().startActivity(intent);


        }
        public void Delete(){


            remArc.remove(position);
            adapter.notifyItemRemoved(position);
            adapter.notifyItemRangeChanged(position,remArc.size());
            System.out.println("Sujeey");

            /*Delete_item delete_item=new Delete_item(remArc.get(position),getActivity());
            delete_item.execute();*/
        }


        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.edit_medication:
                    Edit();
                    return true;
                case R.id.delete_medication:
                     // Delete();

                    Toast.makeText(getContext(), "Delete", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }


    private void initCollapsingToolbar(View view) {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    public class Getreminder extends AsyncTask<Void,Void,ArrayList<ReminderArchclass>>{
        String mEmail=null;
        Integer position;
        String current_time="";
        public Getreminder(String mEmail,Integer position, String current_time){
            this.mEmail=mEmail;
            this.position=position;
            this.current_time=current_time;
        }

        final MediSysSQLiteHelper mDbHelper = new MediSysSQLiteHelper(getActivity().getApplicationContext());



        @Override
        protected ArrayList<ReminderArchclass> doInBackground(Void... params) {
            try {
                SQLiteDatabase db = mDbHelper.getReadableDatabase();

                String selection = MediSysContract.MedicationEntry.COLUMN_NAME_EMAIL + " = ?";
                String[] selectionArgs = {mEmail};
                String sortOrder =
                        MediSysContract.MedicationEntry.COLUMN_NAME_EMAIL + " DESC";

                String[] projection = {
                        MediSysContract.MedicationEntry.COLUMN_NAME_DESCRIPTION,
                        MediSysContract.MedicationEntry.COLUMN_NAME_SCHEDULE_DAYS,
                        MediSysContract.MedicationEntry.COLUMN_NAME_SCHEDULE_DURAtION,
                        MediSysContract.MedicationEntry.COLUMN_NAME_SKIP,
                        MediSysContract.MedicationEntry.COLUMN_NAME_UNIQUE_ID,
                        MediSysContract.MedicationEntry.COLUMN_NAME_ALARM_STATUS,

                };


                Cursor cursor = db.query(
                        MediSysContract.MedicationEntry.TABLE_NAME,                     // The table to query
                        projection,                               // The columns to return
                        selection,                                // The columns for the WHERE clause
                        selectionArgs,                            // The values for the WHERE clause
                        null,                                     // don't group the rows
                        null,                                     // don't filter by row groups
                        sortOrder                                 // The sort order
                );
                Log.i("Reminders","cursor count");
                System.out.println(cursor.getCount());

                if (cursor.getCount() > 0) {
                    System.out.println("aaa"+remArc.size());
                    while(cursor.moveToNext()) {
                        ReminderArchclass reminderArchclass = new ReminderArchclass();
                        if(cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationEntry.COLUMN_NAME_ALARM_STATUS)).equals("true")) {

                            String schedule_duration = cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationEntry.COLUMN_NAME_SCHEDULE_DURAtION));
                            String schedule_days = cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationEntry.COLUMN_NAME_SCHEDULE_DAYS));
                            String description = cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationEntry.COLUMN_NAME_DESCRIPTION));
                            String skip = cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationEntry.COLUMN_NAME_SKIP));
                            String unique_id = cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationEntry.COLUMN_NAME_UNIQUE_ID));

                            reminderArchclass.setSchedule_duration(schedule_duration);
                            reminderArchclass.setDescription(description);
                            reminderArchclass.setSchedule_days(schedule_days);
                            reminderArchclass.setskip(skip);
                            reminderArchclass.setUnique_id(unique_id);


                            String selection_reminder_timer = MediSysContract.MedicationReminders.COLUMN_NAME_UNIQUE_ID + " = ?";
                            String[] selectionArgs_reminder_timer = {unique_id};
                            String sortOrder_reminder_timer =
                                    MediSysContract.MedicationReminders.COLUMN_NAME_UNIQUE_TIMER_ID + " DESC";

                            String[] projection_reminder_timer = {
                                    MediSysContract.MedicationReminders.COLUMN_NAME_REMINDER_TIMER,

                            };


                            Cursor cursor_reminder_timer = db.query(
                                    MediSysContract.MedicationReminders.TABLE_NAME,                     // The table to query
                                    projection_reminder_timer,                               // The columns to return
                                    selection_reminder_timer,                                // The columns for the WHERE clause
                                    selectionArgs_reminder_timer,                            // The values for the WHERE clause
                                    null,                                     // don't group the rows
                                    null,                                     // don't filter by row groups
                                    sortOrder_reminder_timer                                 // The sort order
                            );


                            if (cursor_reminder_timer.getCount() > 0) {
                                // System.out.println("aaa" + remArc.size());
                                ArrayList<String> tempreminder = new ArrayList<String>();
                                while (cursor_reminder_timer.moveToNext()) {
                                    String timer = cursor_reminder_timer.getString(cursor_reminder_timer.getColumnIndex(MediSysContract.MedicationReminders.COLUMN_NAME_REMINDER_TIMER));
                                    Log.i("Check timeReminder", Integer.toString(position));
                                    // timer="Sun Nov 27 04:37:00 GMT+05:30 2016";
                                    String date[] = timer.split(" ");
                                    String time[] = date[3].split(":");
                                    System.out.println(Integer.parseInt(time[0]));


                                    /*switch (position)
                                    {
                                        case 0:if(Integer.parseInt(time[0])>=4&&Integer.parseInt(time[0])<12){
                                            tempreminder.add(timer);
                                        }

                                        case 1:if(Integer.parseInt(time[0])>=12&&Integer.parseInt(time[0])<18){
                                            tempreminder.add(timer);
                                        }

                                        case 2:
                                        }

                                        case 3:if(Integer.parseInt(time[0])>=0&&Integer.parseInt(time[0])<4){
                                            tempreminder.add(timer);
                                        }
                                    }

    */

                                    Calendar calendar = Calendar.getInstance();
                                    Calendar calendar1 = Calendar.getInstance();
                                    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                                    calendar.setTime(sdf.parse(timer));
                                    calendar.set(Calendar.HOUR, 0);
                                    calendar.set(Calendar.MINUTE, 0);
                                    calendar.set(Calendar.SECOND, 0);

                                    calendar1.setTime(sdf.parse(current_time));
                                    calendar1.set(Calendar.HOUR, 0);
                                    calendar1.set(Calendar.MINUTE, 0);
                                    calendar1.set(Calendar.SECOND, 0);

                                    System.out.println(calendar.getTime().toString() + "asd" + calendar1.getTime().toString() + "I am not null" + timer);
                                    if(calendar1.getTimeInMillis()>=calendar.getTimeInMillis()){
                                        if (position == 0) {
                                            if (Integer.parseInt(time[0]) >= 4 && Integer.parseInt(time[0]) < 12) {
                                                tempreminder.add(timer);
                                            }
                                        }
                                        if (position == 1) {
                                            if (Integer.parseInt(time[0]) >= 12 && Integer.parseInt(time[0]) < 18) {
                                                tempreminder.add(timer);
                                            }
                                        }
                                        if (position == 2) {
                                            if (Integer.parseInt(time[0]) >= 18 && Integer.parseInt(time[0]) < 24) {
                                                tempreminder.add(timer);
                                            }
                                        }

                                        if (position == 3) {
                                            if (Integer.parseInt(time[0]) >= 0 && Integer.parseInt(time[0]) < 4) {
                                                tempreminder.add(timer);
                                            }
                                        }
                                }

                                    // tempreminder.add(timer);
                                }
                                reminderArchclass.setReminder_timer(tempreminder);
                                if (tempreminder.size() != 0) {

                                    remArc.add(reminderArchclass);
                                }
                            }


                        }
                    }
                }
            }catch (Exception e){
                Log.i("Reminders","exception");

                e.printStackTrace();
            }
            return remArc;
        }



        @Override

        protected void onPostExecute(ArrayList<ReminderArchclass> remArc) {
            if(!remArc.isEmpty())
                System.out.println(remArc.size()+"uuuuuuu"+remArc.get(0).getDescription()+remArc.get(0).getReminder_timer());

            adapter=new ReminderAdapter(getActivity(),remArc);
            listView.setAdapter(adapter);
        }
    }



    public static class Delete_item extends AsyncTask<Void, Void, Boolean> {
        ReminderArchclass reminderArchclass;
        Context context;
        final MediSysSQLiteHelper mDbHelper;

        Delete_item(ReminderArchclass reminderArchclass,Context context) {
            this.reminderArchclass=reminderArchclass;
            this.context=context;
            mDbHelper= new MediSysSQLiteHelper(context);

        }


        public void deleteReminder(SQLiteDatabase db) {

            String selection_reminder_timer = MediSysContract.MedicationReminders.COLUMN_NAME_UNIQUE_ID + " = ?";
            String[] selectionArgs_reminder_timer = {reminderArchclass.getUnique_id()};
            String sortOrder_reminder_timer =
                    MediSysContract.MedicationReminders.COLUMN_NAME_UNIQUE_TIMER_ID + " DESC";

            String[] projection_reminder_timer = {
                    MediSysContract.MedicationReminders.COLUMN_NAME_UNIQUE_TIMER_ID,
                    MediSysContract.MedicationReminders.COLUMN_NAME_REMINDER_TIMER,

            };


            Cursor cursor_reminder_timer = db.query(
                    MediSysContract.MedicationReminders.TABLE_NAME,                     // The table to query
                    projection_reminder_timer,                               // The columns to return
                    selection_reminder_timer,                                // The columns for the WHERE clause
                    selectionArgs_reminder_timer,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    sortOrder_reminder_timer                                 // The sort order
            );

            Intent intent=new Intent(context,Reciever.class);
            intent.putExtra("Description",reminderArchclass.getDescription());
            intent.putExtra("schedule_duration",reminderArchclass.getSchedule_duration());
            intent.putExtra("schedule_days",reminderArchclass.getSchedule_days());
            intent.putExtra("Unique_id",reminderArchclass.getUnique_id());


            if (cursor_reminder_timer.getCount() > 0) {
                // System.out.println("aaa" + remArc.size());
                while (cursor_reminder_timer.moveToNext()) {
                    String timer_id = cursor_reminder_timer.getString(cursor_reminder_timer.getColumnIndex(MediSysContract.MedicationReminders.COLUMN_NAME_UNIQUE_TIMER_ID));
                    String timer=cursor_reminder_timer.getString(cursor_reminder_timer.getColumnIndex(MediSysContract.MedicationReminders.COLUMN_NAME_REMINDER_TIMER));
                    intent.putExtra("unique_timer_id",timer_id);
                    intent.putExtra("Reminder_timer",timer);
                    PendingIntent alarmIntent = PendingIntent.getBroadcast(context, Integer.parseInt(timer_id), intent, 0);
                    alarmIntent.cancel();
                    AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    alarmMgr.cancel(alarmIntent);
                }

            }




        }



        @Override
        protected Boolean doInBackground(Void... params) {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            //Log.e(password,"eeeeeeeeeeeeee");


            deleteReminder(db);

          /*  PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    Integer.parseInt(reminderArchclass.getUnique_id()), intent, 0);
            pendingIntent.cancel();
            AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmMgr.cancel(pendingIntent);
*/

            ContentValues values = new ContentValues();
            values.put(MediSysContract.MedicationEntry.COLUMN_NAME_ALARM_STATUS,"false" );
            String selection = MediSysContract.MedicationEntry.COLUMN_NAME_UNIQUE_ID + " LIKE ?";
            String[] selectionArgs = { reminderArchclass.getUnique_id() };
            int count = db.update(
                    MediSysContract.MedicationEntry.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
            System.out.println("ssdddddddd"+count);


/*

            String selection = MediSysContract.MedicationEntry.COLUMN_NAME_UNIQUE_ID + " = ?";
            String[] selectionArgs = {reminderArchclass.getUnique_id()};
            db.delete(MediSysContract.MedicationEntry.TABLE_NAME,selection,selectionArgs);

            selection= MediSysContract.MedicationReminders.COLUMN_NAME_UNIQUE_ID + " = ?";
            db.delete(MediSysContract.MedicationReminders.TABLE_NAME,selection,selectionArgs);
*/


            return true;
        }




        @Override
        protected void onPostExecute(final Boolean success) {


            if (success) {
                //    finish();
            } else {
                //Define your problem
            }
        }

        @Override
        protected void onCancelled() {

        }
    }




    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
