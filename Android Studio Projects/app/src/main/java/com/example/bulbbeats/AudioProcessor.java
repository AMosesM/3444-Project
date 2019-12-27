package com.example.bulbbeats;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.util.Log;

import java.util.Arrays;
import java.util.Date;

public class AudioProcessor{
    private float[] FFT;
    private float[] Keys;
    private Visualizer mVisualizer;
    private static int numBins = 500;
    private static int[] KeyToFreq;
    private static int[] FreqToKeys;
    private int PERMISSION_CODE = 1;
    private fftListener listener;
    private Date date1 = new Date();
    private Date date2 = null;

    //constructor
    public AudioProcessor(MediaPlayer mPlayer, Context context)
    {
        mVisualizer = new Visualizer(mPlayer.getAudioSessionId());
        mVisualizer.setEnabled(false);
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        //Keys = new float[88];
        FFT = new float[500];
        Arrays.fill(Keys, 0);
        //KeyToFreq = new int[]{7,14,19,23,26,29,31,33,35,37,38};
        //FreqToKeys = new int[]{28,30,32,34,36,38,40,43,46,49,52,55,58,62,65,69,74,78,83,88,93,99,105,111,118,125,133,141,149,159,168,178,189,200,212};

        Visualizer.OnDataCaptureListener captureListener = new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
                //nothing because we only want fft
            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
                updateFFT(fft);
                if(listener == null)
                    return;
                listener.onUpdate(FFT);
            }
        };

        //this sets up the listener but does not actually enable the visualizer. That happens back in launch.
        mVisualizer.setDataCaptureListener(captureListener,
                20000, false, true);
    }

    public void updateFFT(byte[] fft)
    {
    //--------Below is the code for mapping to 500 frequency bins in Float[] FFT. ------------------//
    //--------You can change the number of bins to draw by changing numBins and the size of FFT[]---//
        int numBuckets = fft.length/2;
        int binSize = numBuckets / numBins;

        for(int j = 0; j < numBins; j++){
            int max = 0;
            int temp = 0;
            int offset = j*binSize;
            for (int i = 0; i < binSize; i++) {
                byte rfk = fft[offset + i];
                byte ifk = fft[offset + i + 1];
                temp = (rfk * rfk + ifk * ifk);
                if(temp > max)
                    max = temp;
            }
            FFT[j] = (float)max;
        }

        //-------Below is the code for mapping to ~88 piano keys stored in Float[] Keys----------//
        /*
        int cnt = 0;

        //  There has to be better way of doing this using a map or dictionary.
        for(int i = 0; i <= 212; i++)
        {
            byte rfk = fft[i * 2 + 4];
            byte ifk = fft[i * 2 + 5];

            if(i < 11)
                Keys[KeyToFreq[i]-1] = (float) (rfk * rfk + ifk * ifk)/20;
            if(i >= 11 && i < 20)
                Keys[i+28] = (float) (rfk * rfk + ifk * ifk)/20;
            if(i>=21 && i<24)
                Keys[i+27] = (float) (rfk * rfk + ifk * ifk)/20;
            if(i==25 || i==26)
                Keys[i+26] = (float) (rfk * rfk + ifk * ifk)/20;
            //Log.v("LaunchActivity.onUpdate", String.format("%d:",Arrays.binarySearch(FreqToKeys, i)));
            if(Arrays.binarySearch(FreqToKeys, i) >= 0) {
                Keys[53 + cnt] = (float) (rfk * rfk + ifk * ifk)/20;
                cnt++;
            }

        }*/

        //roundtrip time
        date2 = new Date();
        long capture = date2.getTime() - date1.getTime();
        Log.v("LaunchActivity.onUpdate", String.format("%6.1fms: %6.1f %6.1f %6.1f %6.1f %6.1f %6.1f %6.1f %6.1f %6.1f %6.1f %6.1f %6.1f %6.1f %6.1f %6.1f %6.1f",
                (float)capture,FFT[0], FFT[1], FFT[2], FFT[3],FFT[4], FFT[5], FFT[6], FFT[7],FFT[8], FFT[9], FFT[10], FFT[11],FFT[12], FFT[13], FFT[14], FFT[15]));
        date1 = new Date();
    }

    public void release()
    {
        mVisualizer.release();
    }

    public void enable()
    {
        mVisualizer.setEnabled(true);
    }

    public void disable()
    {
        mVisualizer.setEnabled(false);
    }

    public void setFFTListener(fftListener listener) {this.listener = listener;};
}
