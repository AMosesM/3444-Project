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
    private static int numBins = 16;
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
        Keys = new float[88];
        Arrays.fill(Keys, 0);
        KeyToFreq = new int[]{7,14,19,23,26,29,31,33,35,37,38};
        FreqToKeys = new int[]{28,30,32,34,36,38,40,43,46,49,52,55,58,62,65,69,74,78,83,88,93,99,105,111,118,125,133,141,149,159,168,178,189,200,212};

        Visualizer.OnDataCaptureListener captureListener = new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
                //nothing because we only want fft
            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
                FFT = new float[numBins];
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

    public void setfftListener(fftListener listener) {
        this.listener = listener;
    }

    public void updateFFT(byte[] fft)
    {
        /*int n = fft.length;
        int[] magnitudes = new int[n / 2 + 1];
        magnitudes[0] = Math.abs(fft[0]);
        magnitudes[n / 2] = Math.abs(fft[1]);

        for(int k = 1; k < n / 2; k++){
            int x = k * 2;
            magnitudes[k] = (int)Math.log10((float)Math.hypot(fft[x], fft[x + 1])) * 40;
        }

        for (int i = 1; i <= numBins; i++) {
            float avg = 0;

            for(int j = 0; j < (magnitudes.length-1) / numBins; j++)
                 avg += magnitudes[j*i+1];

            avg /= numBins;
            FFT[i-1] = avg;
        }*/

        /*int numBuckets = fft.length/2;
        int binSize = numBuckets / numBins;

        for(int j = 0; j < numBins; j++){
            int avg = 0;
            int offset = j*binSize;
            for (int i = 0; i < binSize; i++) {
                byte rfk = fft[offset + i];
                byte ifk = fft[offset + i + 1];
                avg += (int) Math.log10(rfk * rfk + ifk * ifk)*4;
            }
            FFT[j] = (float)avg / binSize;
            FFT[j] = (float)Math.pow(FFT[j],4)/64;
        }*/

        int cnt = 0;

        //  There has to be better way of doing this using a map or dictionary.
        for(int i = 0; i <= 212; i++)
        {
            byte rfk = fft[i * 2 + 4];
            byte ifk = fft[i * 2 + 5];

            if(i < 11)
                Keys[KeyToFreq[i]-1] = (float) Math.pow((int) Math.log10(rfk * rfk + ifk * ifk) * 4, 4) / 64;
            if(i >= 11 && i < 20)
                Keys[i+28] = (float) Math.pow((int) Math.log10(rfk * rfk + ifk * ifk) * 4, 4) / 64;
            if(i>=21 && i<24)
                Keys[i+27] = (float) Math.pow((int) Math.log10(rfk * rfk + ifk * ifk) * 4, 4) / 64;
            if(i==25 || i==26)
                Keys[i+26] = (float) Math.pow((int) Math.log10(rfk * rfk + ifk * ifk) * 4, 4) / 64;
            //Log.v("LaunchActivity.onUpdate", String.format("%d:",Arrays.binarySearch(FreqToKeys, i)));
            if(Arrays.binarySearch(FreqToKeys, i) >= 0) {
                Keys[53 + cnt] = (float) Math.pow((int) Math.log10(rfk * rfk + ifk * ifk) * 4, 4) / 64;
                cnt++;
            }

        }

        /*for(int i = 28; i < 41; i+=2)
        {

            byte rfk = fft[i * 2 + 4];
            byte ifk = fft[i * 2 + 5];
            Keys[53+cnt] = (float) Math.pow((int) Math.log10(rfk * rfk + ifk * ifk) * 4, 4) / 64;
            cnt++;
        }
        cnt = 0;
        for(int i = 43; i < 59; i+=3)
        {
            byte rfk = fft[i * 2 + 4];
            byte ifk = fft[i * 2 + 5];
            Keys[60+cnt] = (float) Math.pow((int) Math.log10(rfk * rfk + ifk * ifk) * 4, 4) / 64;
            cnt++;
        }
        for(int i = 62; i < 79; i++)
        {
            byte rfk = fft[i * 2 + 4];
            byte ifk = fft[i * 2 + 5];
            if(i==62)
                Keys[66] = (float) Math.pow((int) Math.log10(rfk * rfk + ifk * ifk) * 4, 4) / 64;
            if(i==65)
                Keys[67]
        }*/

        //roundtrip time
        date2 = new Date();
        long capture = date2.getTime() - date1.getTime();
        //Log.v("LaunchActivity.onUpdate", String.format("%6.1fms: %6.1f %6.1f %6.1f %6.1f %6.1f %6.1f %6.1f %6.1f %6.1f %6.1f %6.1f %6.1f %6.1f %6.1f %6.1f %6.1f",
         //       (float)capture,Keys[6], Keys[28], Keys[36], Keys[40],Keys[59], Keys[65], Keys[68], Keys[70],Keys[72], Keys[74], Keys[76], Keys[78],Keys[80], Keys[82], Keys[85], Keys[87]));
        date1 = new Date();
    }

    public void release()
    {
        mVisualizer.release();
    }

    public float[] getBytesFFT()
    {
        return FFT;
    }

    public void enable()
    {
        mVisualizer.setEnabled(true);
    }

    public void disable()
    {
        mVisualizer.setEnabled(false);
    }


}
