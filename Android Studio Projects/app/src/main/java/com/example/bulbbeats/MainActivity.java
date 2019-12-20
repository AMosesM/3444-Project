package com.example.bulbbeats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button newProjectbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
        initMainWidgits();
        setOnClickListeners();

    }
    private void checkPermissions(){
        //Need to get permissions from the user.
        //Checks if permission is granted
        int PERMISSION_CODE = 1;
        //TODO: add something to handle denied permission request
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED){
            //Permission not granted. Need to ask for it
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.RECORD_AUDIO},1);
        }
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED){
            //Permission not granted. Need to ask for it
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.INTERNET},1);
        }
    }
    private void initMainWidgits(){
        newProjectbtn = findViewById((R.id.button));
    }

    private void setOnClickListeners(){
        newProjectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //will start from MainActivity to SongActivity class
                    Intent intent = new Intent(MainActivity.this, SongActivity.class);
                    startActivity(intent); //will trigger the intent
            }
        });
    }
}
