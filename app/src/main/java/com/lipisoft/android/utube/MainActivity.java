package com.lipisoft.android.utube;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

public class MainActivity extends Activity {

    private TubeWebChromeClient mWebChromeClient = null;
    private View mCustomView;
    private WebView mContentView;
    private FrameLayout mCustomViewContainer;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.loadUrl("http://m.youtube.com");
        mWebView.setWebViewClient(new WebViewClient());

        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebChromeClient = new TubeWebChromeClient();
        mWebView.setWebChromeClient(mWebChromeClient);
    }

    @Override
    public void onBackPressed() {
        if (mCustomViewContainer != null)
            mWebChromeClient.onHideCustomView();
        else if (mWebView.canGoBack())
            mWebView.goBack();
        else
            super.onBackPressed();
    }

    private class TubeWebChromeClient extends WebChromeClient {
        FrameLayout.LayoutParams LayoutParameters = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);

            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mContentView = (WebView) findViewById(R.id.webview);
            mContentView.setVisibility(View.GONE);
            mCustomViewContainer = new FrameLayout(MainActivity.this);
            mCustomViewContainer.setLayoutParams(LayoutParameters);
            mCustomViewContainer.setBackgroundResource(android.R.color.black);
            view.setLayoutParams(LayoutParameters);
            mCustomViewContainer.addView(view);
            mCustomView = view;
            mCustomViewCallback = callback;
            mCustomViewContainer.setVisibility(View.VISIBLE);
            setContentView(mCustomViewContainer);
        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();

            if (mCustomView != null) {
                // Hide the custom view.
                mCustomView.setVisibility(View.GONE);
                // Remove the custom view from its container.
                mCustomViewContainer.removeView(mCustomView);
                mCustomView = null;
                mCustomViewContainer.setVisibility(View.GONE);
                mCustomViewCallback.onCustomViewHidden();
                // Show the content view.
                mContentView.setVisibility(View.VISIBLE);
                setContentView(mContentView);
            }
        }
    }
}
