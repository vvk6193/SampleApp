package com.vivek.sampleapp.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivek.sampleapp.R;
import com.vivek.sampleapp.modal.Patient;
import com.vivek.sampleapp.modal.StaffInformation;
import com.vivek.sampleapp.modal.Student;
import com.vivek.sampleapp.networktask.FetchDataTask;
import com.vivek.sampleapp.networktask.FetchStaff;
import com.vivek.sampleapp.networktask.NetworkTask;
import com.vivek.sampleapp.networktask.SendToServer;

import java.util.List;
import java.util.concurrent.Future;

public class DrawerActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener ,Response.Listener<List<StaffInformation>>{

//    @Override
//    protected void onResume() {
//        Log.d("vvk","onresume Drawer activity");
//        super.onResume();
//    }
//
//    @Override
//    protected void onDestroy() {
//        Log.d("vvk","ondestroy Drawer activity");
//        super.onDestroy();
//
//    }
//
//    @Override
//    protected void onStop() {
//        Log.d("vvk", "onstop Drawer activity ");
//        super.onStop();
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d("vvk", "onpause Drawer activity ");
//    }

    TextView tv;
    ObjectMapper objMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        objMapper = new ObjectMapper();
        tv = (TextView) findViewById(R.id.textviewdrawer);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        findViewById(R.id.buttonDrawer).setOnClickListener(this);

//        new FetchDataTask(new Response.Listener<List<Student>>() {
//            @Override
//            public void onResponse(List<Student> response) {
//
//            }
//        });


//        JsonObjectRequest jsObjRequest = new JsonObjectRequest
//                (Request.Method.GET, new String(), null, new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        mTxtDisplay.setText("Response: " + response.toString());
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO Auto-generated method stub
//
//                    }
//                });
//        RequestQueue queue = Volley.newRequestQueue(this);
//        queue.add(jsObjRequest);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.buttonDrawer:
//                FetchStaff f = new FetchStaff(DrawerActivity.this);
//                f.execute();
//                f.print();
                long t1 = System.currentTimeMillis();
                for(int i=0;i<1000;i++) {
                    NetworkTask task = new NetworkTask(new com.vivek.sampleapp.interfaces.Response.SuccessListener() {
                        @Override
                        public void onResponse(Object result) {
                            String patientname = "";
                            List<Object> patientList = (List<Object>) result;
                            if(patientList != null && patientList.size() > 0 ) {
                                Patient p = objMapper.convertValue(patientList.get(0),Patient.class);
                                patientname = p.getFullName();
                            }
                            tv.setText("success" + patientname);
                            Log.d("vvk","success " + patientname);
                        }
                    }, new com.vivek.sampleapp.interfaces.Response.ErrorListener() {
                        @Override
                        public void onError() {
                            Log.d("vvk","error");
                        }
                    });
                    Future<?> future = task.execute();
                    futureList.add(future);
//                    new SendToServer().execute();

                }
                long t2 = System.currentTimeMillis();
                Log.d("vvk","time taken = " + (t2-t1));
                break;
        }

    }

    @Override
    public void onResponse(List<StaffInformation> response) {

    }
}
