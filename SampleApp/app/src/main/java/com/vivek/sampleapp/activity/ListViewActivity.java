package com.vivek.sampleapp.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vivek.sampleapp.R;
import com.vivek.sampleapp.adapter.MySimpleArrayAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListViewActivity extends BaseActivity implements MySimpleArrayAdapter.ClickListener {

    @Bind(R.id.listview)
    ListView listView;

    @Bind(R.id.textviewlist)
    TextView textView;

//    @Override
//    protected void onResume() {
//        Log.d("vvk","onresume Listview activity");
//        super.onResume();
//    }
//
//    @Override
//    protected void onDestroy() {
//        Log.d("vvk","ondestroy Listview activity");
//        super.onDestroy();
//
//    }
//
//    @Override
//    protected void onStop() {
//        Log.d("vvk","onstop Listview activity ");
//        super.onStop();
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d("vvk", "onpause Listview activity");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        ButterKnife.bind(this);
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

        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };

            MySimpleArrayAdapter mySimpleArrayAdapter = new MySimpleArrayAdapter(this,values);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                textView.setText(" outer clicked " + position);
                Log.d("vvk", "clicked outer at " + position);
                Toast.makeText(getApplicationContext(),"clicked outer at " + position,Toast.LENGTH_SHORT).show();
            }
        });
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//        listView.setMultiChoiceModeListener(new Listvie);
        listView.setAdapter(mySimpleArrayAdapter);


    }

    @Override
    public void itemClicked(int position, String type) {
        textView.setText("item clicked " + position + " type " + type);
    }
}
