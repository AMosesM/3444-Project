package com.example.bulbbeats;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;



public class SongActivity extends AppCompatActivity {
    private Button Uploadbtn;
    private Button selectSong;
    private ProjectSettings projset;
    private static int openDoc = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        projset = getIntent().getParcelableExtra("settings");

        initSongWidgits();
        setSongOnClickListeners();

    }
    private void initSongWidgits(){

        Uploadbtn = (Button) findViewById(R.id.uploadBtn);
        selectSong = findViewById(R.id.selectSong);
    }

    private void setSongOnClickListeners(){
        Uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //will start from SongActivity to LaunchActivity class
                Intent sintent = new Intent(SongActivity.this, LaunchActivity.class);
                sintent.putExtra("settings", projset);
                startActivity(sintent); //will trigger the intent
            }
        });
        selectSong.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("*/*");
                startActivityForResult(intent, openDoc);
            }});

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == openDoc && resultCode == RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);
            projset.songUri = data.getData();
            selectSong.setText(projset.songUri.getLastPathSegment());
        }
    }
}
