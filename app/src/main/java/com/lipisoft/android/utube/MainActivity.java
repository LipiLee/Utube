package com.lipisoft.android.utube;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        WebView webview = new WebView(this);
        webview.loadUrl("http://m.youtube.com/");
        webview.getSettings().setJavaScriptEnabled(true);

        setContentView(webview);

    }
}
