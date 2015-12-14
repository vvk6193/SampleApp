package com.vivek.sampleapp.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.vivek.sampleapp.R;
import com.vivek.sampleapp.adapter.NewsPagerAdapter;

public class WebViewActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        int position = getIntent().getIntExtra("position",0);

        NewsPagerAdapter adapter = new NewsPagerAdapter(getSupportFragmentManager(),DrawerActivity.newsList,WebViewActivity.this);
        ViewPager viewPager = (ViewPager) findViewById(R.id.news_view_pager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);

    }

}
