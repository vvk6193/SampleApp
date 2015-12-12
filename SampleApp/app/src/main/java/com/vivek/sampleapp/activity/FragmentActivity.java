package com.vivek.sampleapp.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.vivek.sampleapp.R;
import com.vivek.sampleapp.adapter.MySimpleArrayAdapter;
import com.vivek.sampleapp.fragment.ChatFragment;
import com.vivek.sampleapp.fragment.DetailFragment;

public class FragmentActivity extends BaseActivity implements DetailFragment.OnFragmentInteractionListener,
        View.OnClickListener, ChatFragment.ChatFragmentInterAction,MySimpleArrayAdapter.ClickListener {

    ChatFragment chatFragment;
    DetailFragment detailedFragment;

    private FragmentManager fragmentManager;
    private String fragType = "detail";

//    @Override
//    protected void onResume() {
//        Log.d("vvk", "onresume Fragmentactivity");
//        super.onResume();
//    }
//
//    @Override
//    protected void onDestroy() {
//        Log.d("vvk", "ondestroy Fragmentactivity");
//        super.onDestroy();
//
//    }
//
//    @Override
//    protected void onStop() {
//        Log.d("vvk", "onstop Fragmentactivity ");
//        super.onStop();
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d("vvk", "onpause Fragmentactivity ");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("vvk", "oncreate fragmentactivity enter");
        setContentView(R.layout.activity_fragment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.loadChatFragment).setOnClickListener(this);
        findViewById(R.id.loadDetailFragment).setOnClickListener(this);
        findViewById(R.id.swapFragment).setOnClickListener(this);
        chatFragment = ChatFragment.newInstance("vivek", "vekariya");
        detailedFragment = DetailFragment.newInstance("vivek", "vekariya");
        fragmentManager = getSupportFragmentManager();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//        DetailFragment detailedFragment = DetailFragment.newInstance("vivek", "vekariya");
//        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, detailedFragment, "detailedfragment").setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).commit();
        Log.d("vvk", "oncreate fragmentactivity enter");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

        Toast.makeText(FragmentActivity.this, "Button pressed on fragment", Toast.LENGTH_SHORT).show();

    }

    void toggleFragType() {
        if (fragType.equals("chat")) {
            fragType = "detail";
        } else {
            fragType = "chat";
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.swapFragment:

                if (fragmentManager.getBackStackEntryCount() > 0) {
                    toggleFragType();
                    fragmentManager.popBackStack();
                } else if (fragType.equals("detail")) {
                    loadChatFragment();
                } else if (fragType.equals("chat")) {
                    loadDetailedFragment();
                } else {
                    Toast.makeText(FragmentActivity.this, "No fragment in stack", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.loadChatFragment:
                if (fragType.equals("detail") && fragmentManager.getBackStackEntryCount() > 0) {
                    toggleFragType();
                    fragmentManager.popBackStack();
                } else if (fragType.equals("detail")) {
                    loadChatFragment();
                }
                break;
            case R.id.loadDetailFragment:
                if (fragType.equals("chat") && fragmentManager.getBackStackEntryCount() > 0) {
                    toggleFragType();
                    fragmentManager.popBackStack();
                } else if (fragType.equals("chat")) {
                    loadDetailedFragment();
                }
                break;
        }
    }

//    void toggleFragment() {
//        if(fragType)
//    }

    public void loadChatFragment() {
        toggleFragType();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, chatFragment, "chatfragment").addToBackStack(null).setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).commit();
    }

    public void loadDetailedFragment() {
        toggleFragType();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, detailedFragment, "detailedfragment").addToBackStack(null).setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).commit();
    }

    @Override
    public void chatMessageSelected(int id) {

    }

    @Override
    public void itemClicked(int position, String type) {

    }
}
