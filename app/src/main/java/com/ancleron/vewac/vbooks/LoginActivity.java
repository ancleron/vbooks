package com.ancleron.vewac.vbooks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ancleron.vewac.vbooks.base.BaseActivity;
import com.ancleron.vewac.vbooks.base.PreferencesManager;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private PreferencesManager myPref;
    private int width = 0, height = 0;

    private RelativeLayout headerLayout;
    private TextView titleTextView;
    private LinearLayout loginLayout;
    private TextView enterPhotoTextView;
    private EditText photoEditText;
    private Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myPref = new PreferencesManager(this);
        width = myPref.getIntValue("ScreenWidth");
        height = myPref.getIntValue("ScreenHeight");

        initializeViews();
        setDynamicViews();
        setOnClickListener();
        defaultFunction();
    }

    private void initializeViews() {
        headerLayout = (RelativeLayout) findViewById(R.id.headerLayout);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        loginLayout = (LinearLayout) findViewById(R.id.loginLayout);
        enterPhotoTextView = (TextView) findViewById(R.id.enterPhotoTextView);
        photoEditText = (EditText) findViewById(R.id.photoEditText);
        submitButton = (Button) findViewById(R.id.submitButton);

    }

    private void setDynamicViews() {
        int titleMarginTopBottom = (int) (height * 0.03225);// 0.5
        int loginLayoutMarginLeftRight = (int) (height * 0.10752);// 1.0
        int editTextPaddingLeft = (int) (width * 0.03225);// 0.3

        RelativeLayout.LayoutParams titleText = (RelativeLayout.LayoutParams) titleTextView.getLayoutParams();
        titleText.setMargins(titleMarginTopBottom, titleMarginTopBottom, titleMarginTopBottom, titleMarginTopBottom);
        titleTextView.setLayoutParams(titleText);

        RelativeLayout.LayoutParams loginLay = (RelativeLayout.LayoutParams) loginLayout.getLayoutParams();
        loginLay.setMargins(loginLayoutMarginLeftRight, loginLayoutMarginLeftRight, loginLayoutMarginLeftRight, loginLayoutMarginLeftRight);
        loginLayout.setLayoutParams(loginLay);

        LinearLayout.LayoutParams phoneBookText = (LinearLayout.LayoutParams) photoEditText.getLayoutParams();
        phoneBookText.setMargins(0, titleMarginTopBottom, 0, titleMarginTopBottom);
        photoEditText.setLayoutParams(phoneBookText);
        photoEditText.setPadding(editTextPaddingLeft, editTextPaddingLeft, editTextPaddingLeft, editTextPaddingLeft);

        LinearLayout.LayoutParams saveBtn = (LinearLayout.LayoutParams) submitButton.getLayoutParams();
        saveBtn.setMargins(titleMarginTopBottom, 0, titleMarginTopBottom, 0);
        submitButton.setLayoutParams(saveBtn);
    }

    private void setOnClickListener() {
        submitButton.setOnClickListener(this);
    }

    private void defaultFunction() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitButton:
                String photo = photoEditText.getText().toString().trim();
                if (photo.equals("")) {
                    photoEditText.setError(getResources().getString(R.string.enter_photo_no));
                } else if (photo.length()!= 6 || !photo.contains("v3") ) {
                    photoEditText.setError(getResources().getString(R.string.enter_valid_photo_no));
                } else {
                    Intent intent = new Intent(this, PhotoActivity.class);
                    intent.putExtra("photo_no", photo);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }
}
