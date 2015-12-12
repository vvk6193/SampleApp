package com.vivek.sampleapp.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vivek.sampleapp.R;
import com.vivek.sampleapp.adapter.MySimpleArrayAdapter;
import com.vivek.sampleapp.adapter.RVAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecyclerViewActivity extends BaseActivity implements RVAdapter.ClickListener {

    @Bind(R.id.my_recycler_view)
    RecyclerView rcview;

    @Bind(R.id.RVTextView)
    TextView textView;

    @Override
    public void itemClicked(int position, String type) {
        textView.setText("Item clicked is" + position + " and type is " + type);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        RVAdapter adapter= new RVAdapter(this,(RVAdapter.ClickListener)this);
        StaggeredGridLayoutManager linearLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rcview.setLayoutManager(linearLayoutManager);
        rcview.setAdapter(adapter);
        rcview.addOnItemTouchListener(new MySimpleArrayAdapter.RVItemClickListener(this, new MySimpleArrayAdapter.RVItemClickListener.ItemClickListener() {
            @Override
            public void itemclicked(View v, int position) {
                Toast.makeText(getApplicationContext(),"position is " + position,Toast.LENGTH_SHORT).show();
            }
        }));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
