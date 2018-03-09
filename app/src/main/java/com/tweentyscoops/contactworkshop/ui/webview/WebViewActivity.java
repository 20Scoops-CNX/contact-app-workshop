package com.tweentyscoops.contactworkshop.ui.webview;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.tweentyscoops.contactworkshop.R;

@SuppressLint("Registered")
public class WebViewActivity extends AppCompatActivity {
    public static String KEY_WEB_VIEW = "web_view";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        setupView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupView() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.detail);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        String url = getIntent().getExtras().getString(KEY_WEB_VIEW);
        Log.e("url " ,url);
        WebView WebViw = findViewById(R.id.webView);
        WebViw.getSettings().setJavaScriptEnabled(true);
        WebViw.loadUrl("http://"+url);
    }
}
