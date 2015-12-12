package com.vivek.sampleapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.vivek.sampleapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.textViewHome)
    TextView textView;


//    @Override
//    protected void onResume() {
//        Log.d("vvk","onresume Homeactivity");
//        super.onResume();
//    }
//
//    @Override
//    protected void onDestroy() {
//        Log.d("vvk","ondestroy Homeactivity");
//        super.onDestroy();
//
//    }
//
//    @Override
//    protected void onStop() {
//        Log.d("vvk","onstop  Homeactivity");
//        super.onStop();
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d("vvk", "onpause  Homeactivity");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        findViewById(R.id.buttonFragmentActivity).setOnClickListener(this);
        findViewById(R.id.buttonRVActivity).setOnClickListener(this);
        findViewById(R.id.buttonDrawerActivity).setOnClickListener(this);
        findViewById(R.id.buttontabLayoutActivity).setOnClickListener(this);


    }

    @OnClick(R.id.buttonListActivity )
    @Override
    public void onClick(View v) {
        Intent i = null;
        switch (v.getId()) {
            case R.id.buttonListActivity:
                 i = new Intent(this,ListViewActivity.class);
                break;
            case R.id.buttonFragmentActivity:
                i = new Intent(this,FragmentActivity.class);
                break;
            case R.id.buttonRVActivity:
                i = new Intent(this,RecyclerViewActivity.class);
                break;
            case R.id.buttonDrawerActivity:
                i = new Intent(this,DrawerActivity.class);
                break;
            case R.id.buttontabLayoutActivity:
                i = new Intent(this, TabLayoutActivity.class);
                break;
        }
        startActivity(i);
    }
}
