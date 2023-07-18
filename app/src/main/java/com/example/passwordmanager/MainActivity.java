package com.example.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText userNameET, passwordET;
    private TextView signUpTV, forgotPasswordTV;
    private RelativeLayout signInWithGoogle;
    private AppCompatButton signInBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameET = (EditText) findViewById(R.id.idEDTUserName);
        passwordET = (EditText) findViewById(R.id.idEDTPassword);
        signUpTV = (TextView) findViewById(R.id.idTVSignUpBtn);
        forgotPasswordTV = (TextView) findViewById(R.id.idTVForgotPassword);
        signInWithGoogle = (RelativeLayout) findViewById(R.id.idRLSignUpGoogle);
        signInBtn = (AppCompatButton) findViewById(R.id.idBtnSignIn);

        signUpTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        forgotPasswordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(getApplicationContext(), LoggedInPage.class);
//                intent.putExtra("user", "data");
//                startActivity(intent);

                String userName = userNameET.getText().toString();
                String password = passwordET.getText().toString();
                Fetch fetch = new Fetch(getApplicationContext(), "GET", "/users?_embed=directories&emailEdt=" + userName + "&passwordEdt=" + password, "", new ResponseListener() {
                    @Override
                    public void onResponse(String data) {
                        try {
                            JSONArray response = new JSONArray(data);
                            JSONObject response2 = response.getJSONObject(0);
                            String dataArray = response2.getString("directories");
//                            Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
//                            Toast.makeText(MainActivity.this, response2.getString("passwordEdt") , Toast.LENGTH_SHORT).show();
                            if (response2.getString("passwordEdt").equals(password)) {
//                                Toast.makeText(MainActivity.this, response2.getString("passwordEdt") , Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoggedInPage.class);
                                intent.putExtra("user", response2.toString());
                                intent.putExtra("data", dataArray);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Log In Failed, try again with valid credentials!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                fetch.send();
            }
        });

    }
}