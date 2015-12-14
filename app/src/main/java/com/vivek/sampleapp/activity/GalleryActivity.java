package com.vivek.sampleapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.vivek.sampleapp.R;
import com.vivek.sampleapp.adapter.GalleryAdapter;
import com.vivek.sampleapp.interfaces.ClickListener;

import java.io.File;

public class GalleryActivity extends BaseActivity implements ClickListener {

    public static File[] imageFiles;
    RecyclerView recyclerView;
    GalleryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.gallery_recycler_view);

        final GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String CameraFolder="Camera";
        File CameraDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString());
        File[] files = CameraDirectory.listFiles();
        for (File CurFile : files) {
            if (CurFile.isDirectory()) {
                Log.d("vvk","dir "+CurFile.getName());
                imageFiles = CurFile.listFiles();
                if(imageFiles!=null && imageFiles.length > 20) {
                    adapter = new GalleryAdapter(getApplicationContext(),imageFiles,this);
                    recyclerView.setAdapter(adapter);
                    break;
                }
//                CameraDirectory=CurFile.getName();
            } else {
                Log.d("vvk","file "+CurFile.getName());
            }
        }

    }

    @Override
    public void itemClicked(int position, String type) {
        Intent i = new Intent(this,OpenImageActivity.class);
        i.putExtra("position",position);
        startActivity(i);
    }
}
