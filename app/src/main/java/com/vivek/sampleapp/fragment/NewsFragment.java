package com.vivek.sampleapp.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.vivek.sampleapp.R;
import com.vivek.sampleapp.modal.NewsItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseFragment {

    WebView webview;
    NewsItem news;
    Activity context;

    public NewsFragment(Activity context,NewsItem item) {
        this.news = item;
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_news, container, false);
        webview = (WebView) v.findViewById(R.id.news_web_view);
        webview.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            @Override
            public void onLoadResource(WebView view, String url) {

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d("vvk","onpagestarted");
                Log.d("vvk","start progressdialog");
//                if (progressDialog == null) {
//                    // in standard case YourActivity.this
//                    progressDialog = new ProgressDialog(context);
//                    progressDialog.setMessage("Loading...");
//                    progressDialog.show();
//                }
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                try{
//                    Log.d("vvk", "end progressdialog");
//                    if (progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                        progressDialog = null;
//                    }
//                }catch(Exception exception){
//                    exception.printStackTrace();
//                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(news.getWebURL());

        return v;
    }

}
