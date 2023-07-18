package com.example.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText fullNameEdtReset, emailEdtReset, mobileNoEdtReset;
    private AppCompatButton sendResetLink;
    private TextView backToSignInReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        fullNameEdtReset = (EditText) findViewById(R.id.idEDTFullNameReset);
        emailEdtReset = (EditText) findViewById(R.id.idEDTEmailReset);
        mobileNoEdtReset = (EditText) findViewById(R.id.idEDTMobileNoReset);
        sendResetLink = (AppCompatButton) findViewById(R.id.idBtnSendResetLink);
        backToSignInReset = (TextView) findViewById(R.id.idTVBtnSignInReset);

        backToSignInReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }
}