package com.example.passwordmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {

    private CircleImageView profileImage;
    private TextView profileUsername, profileEmail, profilePassword;
    int id;

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        id = getIntent().getIntExtra("id",0);

        Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();
        profileImage = (CircleImageView) findViewById(R.id.idIVPlatformicon);
        profileUsername = (TextView) findViewById(R.id.idTVProfileUserName);
        profileEmail = (TextView) findViewById(R.id.idTVProfileEmail);
        profilePassword = (TextView) findViewById(R.id.idTVProfilePassword);

        String userName = getIntent().getStringExtra("platform");
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");
//        String password = "Password Janina";

        profileUsername.setText(userName);
        profileEmail.setText(email);
        profilePassword.setText(password);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.idUpdateItem:
                startActivity(new Intent(getApplicationContext(), UpdateItemActivity.class).putExtra("id", getIntent().getIntExtra("id",0)));
                Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.idDeleteItem:

                CreateAlertDialogue();
//                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.idComingSoon:
                Toast.makeText(this, "Not added yet", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.idComingSoon2:
                Toast.makeText(this, "Not Added yet 2", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    public void CreateAlertDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure to delete ?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new Fetch(getApplicationContext(), "DELETE", "/directories/" + id,"", new ResponseListener() {
                    @Override
                    public void onResponse(String data) {
                        try {
                            JSONObject response = new JSONObject(data);
                            if (response.getInt("id")>0) {
                                Toast.makeText(UserProfile.this, "Item not deleted Successfully", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(UserProfile.this, "Item deleted, DEBUG", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }).send();
                Toast.makeText(UserProfile.this, "Item Repo Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(UserProfile.this, "Item Not Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        builder.create();
        builder.show();
    }
}