package com.example.mukesh.medisys;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Calendar;

public class MainFragmentDetails extends AppCompatActivity {
    String current_date="";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main_fragment_details);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.sunrise));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.sun));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.sunset));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.moon));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        current_date= getIntent().getStringExtra("CurrentDate");


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),current_date);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(getIntent().getIntExtra("position",0));
       // tabLayout.getTabAt(getIntent().getIntExtra("position",0)).setIcon(R.drawable.ic_wb_sunny_white_24dp);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                System.out.println("see position"+tab.getPosition());

                /*switch (tab.getPosition()){
                    case 0:
                        tab.setIcon(R.drawable.ic_wb_sunny_white_24dp);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.ic_wb_sunny_white_24dp);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.ic_wb_sunny_white_24dp);
                        break;
                    case 3:
                        tab.setIcon(R.drawable.ic_wb_sunny_white_24dp);
                        break;
                }*/
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                System.out.println("see position unselected"+tab.getPosition());
               /* switch (tab.getPosition()){
                    case 0:
                        tab.setIcon(R.drawable.ic_wb_sunny_black_24dp);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.ic_wb_sunny_black_24dp);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.ic_wb_sunny_black_24dp);
                        break;
                    case 3:
                        tab.setIcon(R.drawable.ic_wb_sunny_black_24dp);
                        break;
                }*/
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });





    }





    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;
        String current_date="";
        public PagerAdapter(FragmentManager fm, int NumOfTabs, String current_date) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
            this.current_date=current_date;
        }

        @Override
        public Fragment getItem(int position) {
                return Reminders.newInstance(position, current_date);
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }
}
