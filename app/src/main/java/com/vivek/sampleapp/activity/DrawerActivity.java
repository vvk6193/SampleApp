package com.vivek.sampleapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivek.sampleapp.R;
import com.vivek.sampleapp.adapter.NewsAdapter;
import com.vivek.sampleapp.application.MyApplication;
import com.vivek.sampleapp.interfaces.ClickListener;
import com.vivek.sampleapp.modal.News;
import com.vivek.sampleapp.modal.NewsItem;
import com.vivek.sampleapp.networktask.FetchNewsTask;
import com.vivek.sampleapp.sharedpreference.MySharedPreferenceManager;
import com.vivek.sampleapp.sharedpreference.MySharedPreferenceObject;

import java.util.ArrayList;
import java.util.List;

public class DrawerActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener,
SwipeRefreshLayout.OnRefreshListener,ClickListener{

    TextView tv;
    ObjectMapper objMapper;
    RecyclerView newsRecyclerView;
    SwipeRefreshLayout swipeLayout;
    static List<NewsItem> newsList;
    NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        objMapper = new ObjectMapper();
        tv = (TextView) findViewById(R.id.textviewdrawer);
        newsRecyclerView = (RecyclerView) findViewById(R.id.news_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        newsRecyclerView.setLayoutManager(layoutManager);
        newsList = new ArrayList<>();
        adapter = new NewsAdapter(getApplicationContext(),newsList,(ClickListener)this);
        newsRecyclerView.setAdapter(adapter);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);


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
        loadNews();
        MySharedPreferenceManager s = MySharedPreferenceManager.getInstance(getApplicationContext());
        long t1 = System.currentTimeMillis();
        s.putFirstName("vivek");
        s.putWeight(23);
        s.putSignIn(true);
        s.putUserId("vvk");
        Log.d("vvk", "" + s.getFirstName() + s.getUserId() + s.getSignIn() + s.getWeight());
        long t2 = System.currentTimeMillis();
        Log.d("vvk","time taken preference1 = " + (t2-t1));
        MySharedPreferenceObject obj = MyApplication.getPreferenceObject();
        t1 = System.currentTimeMillis();
        obj.setSignIn(false);
        obj.setUid("vvk2");
        obj.setWeight(25);
        obj.setFname("vekariya");
        Log.d("vvk", "" + obj.getFname() + obj.getUid() + obj.getWeight() +obj.isSignIn());
        t2 = System.currentTimeMillis();
        Log.d("vvk","time taken preference2 = " + (t2-t1));

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
            Intent i = new Intent(DrawerActivity.this,HomeActivity.class);
            startActivity(i);
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
                long t1 = System.currentTimeMillis();
                loadNews();


//                FetchStaff f = new FetchStaff(DrawerActivity.this);
//                f.execute();
//                f.print();
//                for(int i=0;i<1000;i++) {
//                    NetworkTask task = new NetworkTask(new com.vivek.sampleapp.interfaces.Response.SuccessListener() {
//                        @Override
//                        public void onResponse(Object result) {
//                            String patientname = "";
//                            List<Object> patientList = (List<Object>) result;
//                            if(patientList != null && patientList.size() > 0 ) {
//                                Patient p = objMapper.convertValue(patientList.get(0),Patient.class);
//                                patientname = p.getFullName();
//                            }
//                            tv.setText("success" + patientname);
//                            Log.d("vvk","success " + patientname);
//                        }
//                    }, new com.vivek.sampleapp.interfaces.Response.ErrorListener() {
//                        @Override
//                        public void onError() {
//                            Log.d("vvk","error");
//                        }
//                    });
//                    Future<?> future = task.execute();
//                    futureList.add(future);
////                    new SendToServer().execute();
//
//                }
                long t2 = System.currentTimeMillis();
                Log.d("vvk","time taken = " + (t2-t1));
                break;
        }

    }

    public void loadNews() {
        new FetchNewsTask(new com.vivek.sampleapp.interfaces.Response.SuccessListener() {
            @Override
            public void onResponse(Object result) {
                ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
                News n = mapper.convertValue(result,News.class);
                newsList.clear();
                newsList.addAll(n.getNewsItem());
                adapter.notifyDataSetChanged();
                Log.d("vvk","success");
            }
        }, new com.vivek.sampleapp.interfaces.Response.ErrorListener() {
            @Override
            public void onError() {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                Log.d("vvk" ,"error");
            }
        }).execute();
    }

    @Override
    public void onRefresh() {

        swipeLayout.setRefreshing(true);
        loadNews();
        swipeLayout.setRefreshing(false);
        Toast.makeText(getApplicationContext(),"refreshed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void itemClicked(int position, String type) {
        NewsItem item = newsList.get(position);
        Intent i = new Intent(this,WebViewActivity.class);
        i.putExtra("position",position);
        startActivity(i);
        adapter.visitedIds.add(item.getNewsItemId());
    }
}
