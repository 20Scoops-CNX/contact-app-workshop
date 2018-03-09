package com.tweentyscoops.contactworkshop.ui.webview;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.tweentyscoops.contactworkshop.R;

@SuppressLint("Registered")
public class WebViewActivity extends AppCompatActivity {
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
        WebView WebViw = findViewById(R.id.webView);
        WebViw.getSettings().setJavaScriptEnabled(true);
        WebViw.loadUrl("https://www.google.co.th/");
    }
}
