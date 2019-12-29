package com.example.bulbbeats;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.bulbbeats.Views.BarGraphView;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class LaunchActivity extends AppCompatActivity implements fftListener {

    private Context context;
    private ProjectSettings projSet;
    private AudioProcessor audProc;
    private Button playButton;
    private Button stopButton;
    private ToggleButton modeButton;
    private MediaPlayer mPlayer;
    private TextView songTitle;
    private BarGraphView barGraphView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        context = getApplicationContext();

        //custom view for the bars
        barGraphView = findViewById(R.id.bar);

        mPlayer = null;
        audProc = null;

        songTitle = findViewById(R.id.songTitle);

        //grabs the project settings passed by the previous activity.
        projSet = getIntent().getParcelableExtra("settings");

        //cursor is used to get the name of the song.
        Cursor returnCursor = getContentResolver().query(projSet.songUri, null, null, null, null);

        //used to find the song name
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);

        //dont know what this does. Crashes without it.
        returnCursor.moveToFirst();

        //actually sets the text view with the song name
        songTitle.setText(returnCursor.getString(nameIndex));
        returnCursor.close();

        playButton = findViewById(R.id.playButton);
        stopButton = findViewById(R.id.stopButton);
        modeButton = findViewById(R.id.toggleButton);
        setSongOnClickListeners();
        //Control back button press
    }

    //play creates a media player and an audio processor. Also changes the icon image.
    public  void play(View v)
    {
        if(mPlayer == null) {
            mPlayer = MediaPlayer.create(context, projSet.songUri);
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    System.out.println("Stahp");
                    stop();
                }
            });
        }
        if(audProc==null) {
            audProc = new AudioProcessor(mPlayer);
            audProc.setFFTListener(this);
        }

        barGraphView.enable();
        if(modeButton.isChecked())
            audProc.fftmode();
        else
            audProc.keymode();

        start();

        //make pause button icon drawable
        changePausePlayButton(0);
    }

    public  void pause(View v)
    {
        release();
        changePausePlayButton(1);
    }

    public void stop()
    {
        barGraphView.disable();
        stopPlayer();
        audProc = null;
        changePausePlayButton(1);
    }

    public void release()
    {
        barGraphView.disable();
        if(mPlayer != null) {
            mPlayer.pause();
        }
        if(audProc != null) {
            audProc.release();
            audProc = null;
        }
    }

    public void start()
    {
        mPlayer.start();
        audProc.enable(); //actually enables the visualizer to start capturing data.
    }

    //stopPlayer calls release but also frees system resources.
    public  void stopPlayer()
    {
        if(mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
            release();
        }
        if(audProc != null)
        {
            audProc.disable();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        pause(findViewById(android.R.id.content));
    }

    @Override
    protected void onResume() {
        super.onResume();
        pause(findViewById(android.R.id.content));
    }

    @Override
    protected void onStop() {
        super.onStop();
        pause(findViewById(android.R.id.content));
    }
    protected void changePausePlayButton(int isPlay){
        //Int isPlay is 1 if it needs to be the play button or 0 if it needs to be the pause

        if(isPlay==1){
            Drawable resImg = this.getResources().getDrawable(R.drawable.ic_play);

            playButton.setBackground(resImg);
        }
        else{
            Drawable resImg = this.getResources().getDrawable(R.drawable.ic_pause);

            playButton.setBackground(resImg);
        }

    }

    //Overrides back button press to send it to the main page
    @Override
    public void onBackPressed(){
        finish();
    }

    private void setSongOnClickListeners(){
        playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playPauseHandler(v);
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopHandler(v);
            }
        });
        modeButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    //fft
                    if(audProc != null)
                        audProc.fftmode();
                    barGraphView.fftpaint();
                }
                else
                {
                    //keys
                    if(audProc != null)
                        audProc.keymode();
                    barGraphView.keypaint();
                }
            }
        });

    }

    private void stopHandler(View v){
        changePausePlayButton(1);
        stop();
    }

    private void playPauseHandler(View v){
        if(audProc == null || !mPlayer.isPlaying()) {
            play(v);
        }
        else if(mPlayer.isPlaying()){
            pause(v);
        }
        else{
            play(v);
        }
    }


    @Override
    public void onUpdate(float[] Keys) {
        barGraphView.updateFFT(Keys);
        barGraphView.invalidate();
    }
}
