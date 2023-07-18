package com.example.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class AddItemActivity extends AppCompatActivity {

    private EditText platformNameAddItem, userNameAddItem, emailAddItem, passwordAddItem, confirmPasswordAddItem;
    private Button addItemToRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        platformNameAddItem = (EditText) findViewById(R.id.idEDTPlatformAddItem);
        userNameAddItem = (EditText) findViewById(R.id.idEDTUserNameAddItem);
        emailAddItem = (EditText) findViewById(R.id.idEDTEmailAddItem);
        passwordAddItem = (EditText) findViewById(R.id.idEDTPasswordAddItem);
        confirmPasswordAddItem = (EditText) findViewById(R.id.idEDTConfirmPasswordAddItem);
        addItemToRepo = (Button) findViewById(R.id.idBtnAddItemBtn);

        addItemToRepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!platformNameAddItem.getText().toString().isEmpty() && !userNameAddItem.getText().toString().isEmpty() && !emailAddItem.getText().toString().isEmpty()) {
                    if (passwordAddItem.getText().toString().equals(confirmPasswordAddItem.getText().toString())) {

                        String platformName = platformNameAddItem.getText().toString();
                        String userName = userNameAddItem.getText().toString();
                        String email = emailAddItem.getText().toString();
                        String password = passwordAddItem.getText().toString();
                        String confirmPassword = confirmPasswordAddItem.getText().toString();
                        int id = getIntent().getIntExtra("userId",0);

                        JSONObject body = new JSONObject();

                        try {
                            JSONObject platformName1 = body.put("platformName", platformName);
                            JSONObject email1 = body.put("email", email);
                            JSONObject password1 = body.put("password", password);
                            JSONObject confirmPassword1 = body.put("confirmPassword", confirmPassword);
                            JSONObject id1 = body.put("userId", id);
                            JSONObject userName1 = body.put("userName", userName);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Fetch fetch = new Fetch(getApplicationContext(), "POST", "/directories", body.toString(), new ResponseListener() {
                            @Override
                            public void onResponse(String data) {
                                try {
                                    JSONObject response = new JSONObject(data);
                                    if (response.getInt("id")>0) {
                                        Toast.makeText(AddItemActivity.this, "Item Added Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(AddItemActivity.this, "Item not added, DEBUG", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }
                        });
                        fetch.send();
//                        startActivity(new Intent(getApplicationContext(), LoggedInPage.class));
                    } else {
                        Toast.makeText(AddItemActivity.this, "Passwords don't match!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddItemActivity.this, "Please fill out all fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}