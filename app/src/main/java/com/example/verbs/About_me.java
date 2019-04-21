package com.example.verbs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.verbs.R;

public class About_me extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        WebView webView = findViewById(R.id.webView);
        webView.loadUrl("https://www.facebook.com/xuanngocb");
    }
}
