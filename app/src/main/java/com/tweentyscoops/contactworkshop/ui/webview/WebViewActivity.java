package com.tweentyscoops.contactworkshop.ui.webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import com.tweentyscoops.contactworkshop.R;

public class WebViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        setWebView();
        setupView();
    }

    private void setupView() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.detail);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setWebView() {
        WebView WebViw = (WebView) findViewById(R.id.webView);
        WebViw.getSettings().setJavaScriptEnabled(true);
        WebViw.loadUrl("https://www.google.co.th/");
    }
}
