package com.example.bulbbeats;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;


import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class ThemeActivity extends AppCompatActivity {
    private Button selThemebtn;
    private ProjectSettings projset;
    private Spinner spinner1;

    ThemeUtil util = new ThemeUtil();//get array of themes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        initThemeWidgits();

        // Initializing a String Array
        String[] themes = new String[]{
                "",
                "UNT Theme",
                "Halloween Theme",
                "Christmas Theme",
                "Rainbow Theme"
        };

        final List<String> themeList = new ArrayList<>(Arrays.asList(themes));
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>( // Initializing an ArrayAdapter
                this,R.layout.spinner_item,themeList){
            @Override
            public boolean isEnabled(int pposition){
                if(pposition == 0)                    // Disables the first item in th spinner
                {   //allowing for a hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override       //for aesthetic to give the spinner a hint value
            public View getDropDownView(int pposition, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(pposition, convertView, parent);
                TextView tv = (TextView) view;
                if(pposition == 0){
                    tv.setBackgroundColor(Color.rgb(139,153,155));
                    tv.setTextColor(Color.WHITE);
                }
                else {
                    tv.setBackgroundColor(Color.WHITE);
                    tv.setTextColor(Color.rgb(139,153,155));
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); //initializing the drop down adapter
        spinner1.setAdapter(spinnerArrayAdapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pposition, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(pposition);
                if(selectedItemText == "UNT Theme"){
                    setUNTTheme();
                }else if(selectedItemText == "Halloween Theme"){
                    setHalloweenTheme();
                }else if(selectedItemText == "Christmas Theme"){
                    setChristmasTheme();
                }else if(selectedItemText == "Rainbow Theme"){
                    setRainbowTheme();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "nothing Selected : " , Toast.LENGTH_LONG).show();
            }
        });
        setThemeOnclickListener();

        projset = getIntent().getParcelableExtra("settings");

    }

    private void setUNTTheme(){
        Intent intent = getIntent();
        String name = intent.getStringExtra("UNT");

    }

    private void setChristmasTheme(){
        Intent intent = getIntent();
        String name = intent.getStringExtra("UNT");

    }

    private void setRainbowTheme(){
        Intent intent = getIntent();
        String name = intent.getStringExtra("Rainbow");

    }

    private void setHalloweenTheme(){
        Intent intent = getIntent();
        String name = intent.getStringExtra("UNT");

    }

    public void initThemeWidgits(){
        selThemebtn = (Button) findViewById(R.id.themeBtn);
        // Get reference of widgets from XML layout
        spinner1 = (Spinner) findViewById(R.id.themespinner);
    }

    public void setThemeOnclickListener(){
        selThemebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //will start from themectivity to launchactivity class

                Log.v("LOGV", "onActivityResult: trying to go to launch");
                Intent sintent = new Intent(ThemeActivity.this, LaunchActivity.class);
                sintent.putExtra("settings", projset);
                startActivity(sintent); //will trigger the intent

            }
        });
    }






}
