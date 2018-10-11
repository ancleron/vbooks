package com.ancleron.vewac.vbooks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ancleron.vewac.vbooks.base.PreferencesManager;
import com.ancleron.vewac.vbooks.utils.AppUtils;

public class PhotoActivity extends AppCompatActivity {
    private PreferencesManager myPref;
    private int width = 0, height = 0;

    private String webViewUrl = "";
    private WebView photoWebView;
    private ProgressBar progressWheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        myPref = new PreferencesManager(this);
        width = myPref.getIntValue("ScreenWidth");
        height = myPref.getIntValue("ScreenHeight");

        try {
            Intent intent = getIntent();
            webViewUrl = intent.getStringExtra("photo_no");
            webViewUrl = getResources().getString(R.string.photo_url) + webViewUrl;
            Log.d("webViewUrl", "onCreate: " + webViewUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initializeViews();
        setDynamicViews();
        setOnClickListener();
        defaultFunction();
    }

    private void initializeViews() {
        photoWebView = (WebView) findViewById(R.id.photoWebView);
        progressWheel = (ProgressBar) findViewById(R.id.progressWheel);
    }

    private void setDynamicViews() {
    }

    private void setOnClickListener() {
    }

    private void defaultFunction() {
        photoWebView.setWebViewClient(new myWebViewClient());
        photoWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressWheel.setVisibility(View.VISIBLE);

                if (progress == 100) {
                    progressWheel.setVisibility(View.GONE);
                }
            }
        });
        photoWebView.getSettings().setJavaScriptEnabled(true);
        if (AppUtils.isNetworkAvailable(this))
            photoWebView.loadUrl(webViewUrl);
        else
            Toast.makeText(this, "" + getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
    }

    //navigation within webview
    public class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return super.shouldOverrideUrlLoading(view, url);
        }
    }




}
