package com.example.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoggedInPage extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logged_in_menu, menu);
        return true;
    }


//    int[] images = {R.drawable.ic_android_black, R.drawable.ic_android_black, R.drawable.ic_android_black, R.drawable.ic_android_black, R.drawable.ic_android_black, R.drawable.ic_android_black, R.drawable.ic_android_black, R.drawable.ic_android_black, R.drawable.ic_android_black, R.drawable.ic_android_black, R.drawable.ic_android_black, R.drawable.ic_android_black, R.drawable.ic_android_black, R.drawable.ic_android_black};

//    String[] platform = new String[]{"Steam", "Origin", "Rockstar", "Blizzard", "Gmail", "G-suit", "TorrentBD", "BuX", "Instagram", "Twitch", "Reddit", "Discord", "GitHub", "Facebook"};
//
////    String[] platform = {"Steam", "Origin", "Rockstar", "Blizzard", "Gmail", "G-suit", "TorrentBD", "BuX", "Instagram", "Twitch", "Reddit", "Discord", "GitHub", "Facebook"};
//
//    String[] email = {"steam@steam.com", "origin@origin.com", "rockstar@rockstar.com","blizzard@blizzard.com", "gmail@gmail.com", "gsuit@gsuit.com", "torrent@bd.com", "bux@bracu.com", "instagram@instagram.com", "twitch@twitch.com", "reddit@reddit.com", "discord@discord.com", "github@github.com", "facebook@facebook.com"};
//
//    //String[] password = {"Steam", "Origin", "Rockstar", "Blizzard", "Gmail", "G-suit", "TorrentBD", "BuX", "Instagram", "Twitch", "Reddit", "Discord", "GitHub", "Facebook"};
//
//    String password = "janina";
    ListView lView;

    ListAdapter lAdapter;

    int userID ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_page);

        lView = (ListView) findViewById(R.id.androidList);

        String userdata = getIntent().getStringExtra("user");
        String data = getIntent().getStringExtra("data");
        try {
            JSONObject user = new JSONObject(userdata);
            userID = user.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray dataArray = new JSONArray(data);

            String[] platform = new String[dataArray.length()];
            String[] email = new String[dataArray.length()];
            String[] password = new String[dataArray.length()];
            int[] images = new int[dataArray.length()];

            for (int i=0; i<dataArray.length(); i++) {
                platform[i] = dataArray.getJSONObject(i).getString("platformName");
                email[i] = dataArray.getJSONObject(i).getString("email");
                password[i] = dataArray.getJSONObject(i).getString("password");
                images[i] = R.drawable.ic_android_black;
            }

            renderList(platform, email, password, images, dataArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.idAddItemLog:
                startActivity(new Intent(getApplicationContext(), AddItemActivity.class).putExtra("userId", userID));
                Toast.makeText(this, "Add Item", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.idDeleteBatchLog:
                Toast.makeText(this, "Batch Deleted", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.idChangeTheme:
                Toast.makeText(this, "Theme Changed", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.idFilter:
                Toast.makeText(this, "Filter Initiated", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.idComingSoon3:
                Toast.makeText(this, "Not Added yet", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return false;
        }
    }

    public void renderList(String[] platform, String[] email, String[] password, int[] images, JSONArray dataArray) {


        lAdapter = new ListAdapter(LoggedInPage.this, platform, email, images);

        lView.setAdapter(lAdapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Toast.makeText(LoggedInPage.this, platform[i]+" "+email[i], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                try {
                    intent.putExtra("id", dataArray.getJSONObject(i).getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent.putExtra("platform", platform[i]);
                intent.putExtra("email", email[i]);
                intent.putExtra("password", password[i]);
                startActivity(intent);

            }
        });
    }


}