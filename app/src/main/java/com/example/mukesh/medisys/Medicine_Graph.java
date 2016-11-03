package com.example.mukesh.medisys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class Medicine_Graph extends AppCompatActivity {
TextView naMe;
    TextView per;
    View bar1,bar2,bar3;
    int i;
    float values[] = { 7,8,1 };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String skip = intent.getStringExtra("skip");
        String name = intent.getStringExtra("name");




























        setContentView(R.layout.activity_medicine__graph);
        naMe=(TextView)findViewById(R.id.medicine_name) ;
        per=(TextView)findViewById(R.id.percent) ;
        bar1=(View)findViewById(R.id.bar1);
        bar2=(View)findViewById(R.id.bar2);
        bar3=(View)findViewById(R.id.bar3);
int s=0;
       int i=0;
        for(i=0;i<skip.length();i++)
        {
            if(skip.charAt(i)=='1')
            {
             s++;
            }
        }
        naMe.setText(name);
       // per.setText(Integer.toString(((s*100)/i))+"%");
        per.setText(skip);
//        System.out.println(name+"dssss"+skip+"d"+s+Integer.toString(((s*100)/i)));
        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 0),
                new DataPoint(2, 1),
                new DataPoint(3, 1),
                new DataPoint(4, 0),
                new DataPoint(5, 1)

        });
        graph.addSeries(series);


        LinearLayout lv1 = (LinearLayout) findViewById(R.id.activity_medicine__graph);

        values = calculateData(values);
        MyGraphview graphview = new MyGraphview(this, values);
        lv1.addView(graphview);
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
}
