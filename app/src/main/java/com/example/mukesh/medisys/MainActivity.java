package com.example.mukesh.medisys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener  {
    private SharedPreferences sharedread;
    android.support.design.widget.FloatingActionButton fab,fab1,fab2,fab3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main_act);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView=navigationView.getHeaderView(0);
        TextView name=(TextView) hView.findViewById(R.id.username);
        TextView email_id=(TextView) hView.findViewById(R.id.emailview);
        Log.e("aaaa"+Integer.toString(R.id.username),"naaammmmmeee");
        sharedread = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Log.i(sharedread.getString(getString(R.string.name),"Admin"),"asssssssssssssss");
        name.setText(sharedread.getString(getString(R.string.name),"Admin"));
        email_id.setText(sharedread.getString(getString(R.string.email_id),"Email"));

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

            if (id == R.id.profile) {
            // Handle the camera action
                Intent intent=new Intent(this,Profile.class);
                startActivity(intent);
        } else if (id == R.id.medication) {

        } else if (id == R.id.records) {
                Intent intent=new Intent(this,History.class);
                startActivity(intent);
        } else if (id == R.id.settings) {
                Intent intent=new Intent(this,SettingsActivity.class);
                startActivity(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
