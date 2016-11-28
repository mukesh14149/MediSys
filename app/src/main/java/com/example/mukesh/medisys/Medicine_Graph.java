package com.example.mukesh.medisys;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.mukesh.medisys.data.MediSysContract;
import com.example.mukesh.medisys.data.MediSysSQLiteHelper;


import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.StackedBarModel;

import java.util.List;
import java.util.Random;

public class Medicine_Graph extends AppCompatActivity {
TextView naMe;
    TextView per;
     String f="";
    View bar1,bar2,bar3;
    RelativeLayout al;
    int i;
    float values[] = { 7,8,1 };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String skip = intent.getStringExtra("skip");
        String name = intent.getStringExtra("name");

   al=(RelativeLayout)findViewById(R.id.all);





























        setContentView(R.layout.activity_medicine__graph);
        PieChart mPieChart = (PieChart) findViewById(R.id.piechart1);

        naMe=(TextView)findViewById(R.id.medicine_name) ;
        per=(TextView)findViewById(R.id.percent) ;
       /* bar1=(View)findViewById(R.id.bar1);
        bar2=(View)findViewById(R.id.bar2);
        bar3=(View)findViewById(R.id.bar3);*/

        naMe.setText(name);
       // per.setText(Integer.toString(((s*100)/i))+"%");
      //  per.setText(skip);
//        System.out.println(name+"dssss"+skip+"d"+s+Integer.toString(((s*100)/i)));




        LinearLayout lv1 = (LinearLayout) findViewById(R.id.activity_medicine__graph);







        final MediSysSQLiteHelper mDbHelper = new MediSysSQLiteHelper(getApplication().getApplicationContext());








        try {
            SQLiteDatabase db = mDbHelper.getReadableDatabase();

            String selection = MediSysContract.MedicationReminders.COLUMN_NAME_DESCRIPTION+"=?";
            String[] selectionArgs = {name};


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

          /*  LayoutInflater inflater = (LayoutInflater)getBaseContext() .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout main =(LinearLayout)findViewById(R.id.parent);

            for(int i=0;i<9;i++){
                LinearLayout layout = (LinearLayout) findViewById(R.id.repli);
                main.addView(layout);
            }*/


            if (cursor.getCount() > 0) {

                while(cursor.moveToNext()) {
                    skip=cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationReminders.COLUMN_NAME_SKIP));
                    System.out.println(cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationReminders.COLUMN_NAME_REMINDER_TIMER))+"qaz"+skip);

String s12=cursor.getString(cursor.getColumnIndex(MediSysContract.MedicationReminders.COLUMN_NAME_REMINDER_TIMER)).split(" ")[3];



                       // al.addView(d,params);
f=f+skip;
                    int g=0;int l=0;
                    for (i = 0; i < skip.length(); i++) {
                        if (skip.charAt(i) == '1') {
                            g++;
                        }

                        if (skip.charAt(i) == '0') {
                            l++;
                        }
                    }
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    frag_graph hello = new frag_graph();
                    Bundle bundle=new Bundle();
                    bundle.putString("key",Integer.toString(per(skip))+"%");
                    bundle.putString("time",converttime(s12));

                    bundle.putString("skip",Integer.toString(l));
                    bundle.putString("take",Integer.toString(g));

                    hello.setArguments(bundle);


                    fragmentTransaction.add(R.id.repli, hello, "HELLO");
                /*   TextView t =(TextView)findViewById(R.id.percent);
                    TextView t1 =(TextView)findViewById(R.id.percent2);
                    t.setText(converttime(s12));

                    t1.setText(Integer.toString(per(skip))+"%"+"\n");*/
                    fragmentTransaction.commit();
                   /* final StackedBarChart mStackedBarChart = new StackedBarChart(this);
                    mStackedBarChart.setLayoutParams(params);

                    StackedBarModel s1 = new StackedBarModel("12.4");

                    s1.addBar(new BarModel(2.3f, 0xFF63CBB0));
                    s1.addBar(new BarModel(2.3f, 0xFF56B7F1));
                    s1.addBar(new BarModel(2.3f, 0xFFCDA67F));

                    StackedBarModel s2 = new StackedBarModel("13.4");
                    s2.addBar(new BarModel(1.1f, 0xFF63CBB0));
                    s2.addBar(new BarModel(2.7f, 0xFF56B7F1));
                    s2.addBar(new BarModel(0.7f, 0xFFCDA67F));

                    StackedBarModel s3 = new StackedBarModel("14.4");

                    s3.addBar(new BarModel(2.3f, 0xFF63CBB0));
                    s3.addBar(new BarModel(2.f, 0xFF56B7F1));
                    s3.addBar(new BarModel(3.3f, 0xFFCDA67F));

                    StackedBarModel s4 = new StackedBarModel("15.4");
                    s4.addBar(new BarModel(1.f, 0xFF63CBB0));
                    s4.addBar(new BarModel(4.2f, 0xFF56B7F1));
                    s4.addBar(new BarModel(2.1f, 0xFFCDA67F));

                    mStackedBarChart.addBar(s1);
                    mStackedBarChart.addBar(s2);
                    mStackedBarChart.addBar(s3);
                    mStackedBarChart.addBar(s4);

                    mStackedBarChart.startAnimation();

*/


              //     lv1.addView(mStackedBarChart);





                }
            }
        }catch (Exception e) {
            Log.i("Reminders", "exception");
        }
        int s=0,t=0,s0=0;
        for (i = 0; i < f.length(); i++) {
            if (f.charAt(i) == '1') {
                t++;
            }
            if (f.charAt(i) == '2') {
                s0++;
            }
            if (f.charAt(i) == '0') {
                s++;
            }
        }



        mPieChart.addPieSlice(new PieModel("Skip",s, Color.parseColor("#FE6DA8")));


        mPieChart.addPieSlice(new PieModel("Take", t, Color.parseColor("#56B7F1")));


        mPieChart.startAnimation();



















    }


    private float[] calculateData(float[] data) {
        float total = 0;
        for (int i = 0; i < data.length; i++) {
            total += data[i];
        }
        for (int i = 0; i < data.length; i++) {
            data[i] = 360 * (data[i] / total);
        }
        return data;
    }

    public class MyGraphview extends View {
        private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private float[] value_degree;
        RectF rectf = new RectF(120, 120, 380, 380);
        float temp = 0;

        public MyGraphview(Context context, float[] values) {
            super(context);
            value_degree = new float[values.length];
            for (int i = 0; i< values.length; i++) {
                value_degree[i] = values[i];
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Random r;
            for (int i = 0; i < value_degree.length; i++) {
                if (i == 0) {
                    r = new Random();
                    int color = Color.argb(100, r.nextInt(256), r.nextInt(256),
                            r.nextInt(256));
                    paint.setColor(color);
                    canvas.drawArc(rectf, 0, value_degree[i], true, paint);
                    bar1.setBackgroundColor(color);
                } else if(i==1){
                    temp += value_degree[i - 1];
                    r = new Random();
                    int color = Color.argb(255, r.nextInt(256), r.nextInt(256),
                            r.nextInt(256));
                    paint.setColor(color);
                    bar2.setBackgroundColor(color);
                    canvas.drawArc(rectf, temp, value_degree[i], true, paint);
                }
                else if(i==2){
                    temp += value_degree[i - 1];
                    r = new Random();
                    int color = Color.argb(255, r.nextInt(256), r.nextInt(256),
                            r.nextInt(256));
                    paint.setColor(color);
                    bar3.setBackgroundColor(color);
                    canvas.drawArc(rectf, temp, value_degree[i], true, paint);
                }

            }
        }
    }
   int  per(String skip) {
        int s = 0;

        int i = 0;
        for (i = 0; i < skip.length(); i++) {
            if (skip.charAt(i) == '1') {
                s++;
            }
        }
       if(i>0)
     return (((s*100)/i));
       else
           return 0;
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
}
