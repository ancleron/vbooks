package com.ancleron.vewac.vbooks;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import project.sample.com.vewac.base.PreferencesManager;
import project.sample.com.vewac.utils.AppUtils;

public class PhotoActivity extends AppCompatActivity implements View.OnClickListener {
    private PreferencesManager myPref;
    private int width = 0, height = 0;

    private String webViewUrl = "";
    private WebView photoWebView;
    private ProgressBar progressWheel;
    private ImageView screenRotationImageView;
    private boolean isRotate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
        screenRotationImageView = (ImageView) findViewById(R.id.screenRotationImageView);
    }

    private void setDynamicViews() {

        int imagePadding = (int) (width * 0.0196);// 0.3
        int rotateImgHeight = (int) (width * 0.10322);// 1.6

        RelativeLayout.LayoutParams rotateImage = (RelativeLayout.LayoutParams) screenRotationImageView.getLayoutParams();
        rotateImage.width = rotateImgHeight;
        rotateImage.height = rotateImgHeight;
        rotateImage.setMargins(imagePadding, imagePadding, imagePadding, imagePadding);
        screenRotationImageView.setPadding(imagePadding, imagePadding, imagePadding, imagePadding);
        screenRotationImageView.setLayoutParams(rotateImage);

    }

    private void setOnClickListener() {
        screenRotationImageView.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.screenRotationImageView:
                if (isRotate) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    isRotate = false;
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    isRotate = true;
                }
                break;
            default:
                break;
        }
    }

    //navigation within webview
    public class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}
