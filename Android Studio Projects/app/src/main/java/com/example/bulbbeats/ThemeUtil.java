package com.example.bulbbeats;

import java.util.ArrayList;

public class ThemeUtil {

    public ThemeUtil(){

    }

    private static void initAllColors(){

        String name = "";
        int one_hue;
        String one_on = "";
        int one_bri;
        int one_sat;
        int two_hue;
        String two_on= "";
        int two_bri;
        int two_sat;
        int three_hue;
        String three_on= "";
        int three_bri;
        int three_sat;

        name = "UNT";
        one_hue = 25500; //green
        one_on = "true";
        one_bri= 254;
        one_sat = 41;
        two_hue= 25500; //another green
        two_on= "false";
        two_bri= 254;
        two_sat=100;
        three_hue= 25844; //another green
        three_on= "false";
        three_bri= 254;
        three_sat=41;

        name = "Halloween";
        one_hue = 6006; //orange
        one_on = "true";
        one_bri= 254;
        one_sat =100;
        two_hue= 8008; //yellow
        two_on= "false";
        two_bri= 254;
        two_sat=100;
        three_hue= 4444;//another orange
        three_on= "false";
        three_bri= 254;
        three_sat=100;

        name = "Rainbow";
        one_hue = 6006; //orange
        one_on = "true";
        one_bri= 254;
        one_sat =100;
        two_hue= 8008; //yellow
        two_on= "false";
        two_bri= 254;
        two_sat=100;
        three_hue= 4444;//another orange
        three_on= "false";
        three_bri= 254;
        three_sat=100;


        name = "Christmas";
        one_hue = 0; //red
        one_sat = 100;
        one_on = "true";
        one_bri= 254;
        two_hue= 25500; //green
        two_on= "false";
        two_bri= 254;
        two_sat=46;
        three_hue= 64974; //another red
        three_on= "false";
        three_bri= 254;
        three_sat=46;

    }

}
