package com.example.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullNameEdt, emailEdt, mobileNoEdt, passwordEdt, confirmPasswordEdt;
    private AppCompatButton signUpBtnReg;
    private TextView backToSignIn;
    private Fetch fetch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullNameEdt = (EditText) findViewById(R.id.idEDTFullNameReg);
        emailEdt = (EditText) findViewById(R.id.idEDTEmailReg);
        mobileNoEdt = (EditText) findViewById(R.id.idEDTMobileNoReg);
        passwordEdt = (EditText) findViewById(R.id.idEDTPasswordReg);
        confirmPasswordEdt = (EditText) findViewById(R.id.idEDTConfirmPasswordReg);
        signUpBtnReg = (AppCompatButton) findViewById(R.id.idBtnSignUpReg);
        backToSignIn = (TextView) findViewById(R.id.idTVBtnSignInReg);

        backToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        signUpBtnReg.setOnClickListener(view -> {
            if (!fullNameEdt.getText().toString().isEmpty() && !emailEdt.getText().toString().isEmpty() &&
                    !mobileNoEdt.getText().toString().isEmpty() && !passwordEdt.getText().toString().isEmpty() && !confirmPasswordEdt.getText().toString().isEmpty()) {
                if (passwordEdt.getText().toString().equals(confirmPasswordEdt.getText().toString())) {
                    registerUser();
                } else {
                    Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please fill all fields with info!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void registerUser(){
        JSONObject body = new JSONObject();
        try {
            JSONObject fullNameEdt = body.put("fullNameEdt", this.fullNameEdt.getText().toString());
            JSONObject emailEdt = body.put("emailEdt", this.emailEdt.getText().toString());
            JSONObject mobileNoEdt = body.put("mobileNoEdt", String.valueOf(this.mobileNoEdt.getText()));
            JSONObject passwordEdt = body.put("passwordEdt", this.passwordEdt.getText().toString());

//                Toast.makeText(this, body.toString(), Toast.LENGTH_SHORT).show();

            fetch = new Fetch(this, "POST", "/users", body.toString(), new ResponseListener() {
                @Override
                public void onResponse(String data) {
                    try {
                        JSONObject response = new JSONObject(data);
                        if (response.getInt("id")>0) {
                            Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(RegisterActivity.this, "Registration unsuccessful!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    Toast.makeText(RegisterActivity.this, data, Toast.LENGTH_SHORT).show();
                }
            });
            fetch.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}