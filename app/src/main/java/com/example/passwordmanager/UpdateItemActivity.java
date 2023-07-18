package com.example.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateItemActivity extends AppCompatActivity {

    private EditText editUserNameTV, editEmailTV, editPasswordTV;
    private ImageView saveButtonIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        editUserNameTV = (EditText) findViewById(R.id.idTVProfileUserNameUpdate);
        editEmailTV = (EditText) findViewById(R.id.idTVProfileEmailUpdate);
        editPasswordTV = (EditText) findViewById(R.id.idTVProfilePasswordUpdate);
        saveButtonIV = (ImageView) findViewById(R.id.idIVSaveButton);

        saveButtonIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = getIntent().getIntExtra("id",0);

                String userNameEdit = editUserNameTV.getText().toString();
                String emailEdit = editEmailTV.getText().toString();
                String passwordEdit = editPasswordTV.getText().toString();

                JSONObject dataArray = new JSONObject();

                try {
                    dataArray.put("platformName", userNameEdit);
                    dataArray.put("email", emailEdit);
                    dataArray.put("password", passwordEdit);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(UpdateItemActivity.this, "Item Updated!", Toast.LENGTH_SHORT).show();
                new Fetch(getApplicationContext(), "PATCH", "/directories/" + id, dataArray.toString(), new ResponseListener() {
                    @Override
                    public void onResponse(String data) {
                        try {
                            JSONObject response = new JSONObject(data);
                            if (response.getInt("id")>0) {
                                Toast.makeText(UpdateItemActivity.this, "Item modified Successfully", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(UpdateItemActivity.this, "Item not modified, DEBUG", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }).send();
                startActivity(new Intent(getApplicationContext(), UserProfile.class));
            }
        });
    }
}