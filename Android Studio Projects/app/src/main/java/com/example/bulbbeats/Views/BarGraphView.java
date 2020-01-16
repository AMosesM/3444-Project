package com.example.bulbbeats.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.bulbbeats.fftListener;


public class BarGraphView extends View{

    Paint barPaint;
    float offsetLength;
    float[] viewFFT;
    boolean enable;
    enum mode {key, fft}
    mode paintMode;

    public BarGraphView(Context context){
        super(context);

        init(null);
    }

    public BarGraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BarGraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set){
        barPaint = new Paint();
        barPaint.setStyle(Paint.Style.STROKE);
        barPaint.setColor(Color.GREEN);
        keypaint();
        enable = false;
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        float offset = 0;
        if(enable) {
            if(paintMode == mode.fft) {
                for (int i = 0; i < viewFFT.length; i++) {
                    barPaint.setAlpha((int) viewFFT[i] / 20);
                    canvas.drawLine(offset, 0.0f, offset, 725.0f, barPaint);
                    offset += offsetLength;
                }
            }
            if(paintMode == mode.key)
            {
                for (int i = 0; i < viewFFT.length; i++)
                {
                    canvas.drawLine(offset, 720.0f - viewFFT[i], offset, 725.0f, barPaint);
                    offset += offsetLength;
                }
            }
        }
    }

    public void updateFFT(float[] fft){
        viewFFT = fft;
    }

    public void enable(){
        enable = true;
    }

    public void disable(){
        enable = false;
    }

    public void keypaint(){
        barPaint.setStrokeWidth(8.0f);
        barPaint.setAlpha(255);
        offsetLength = 10.0f;
        paintMode = mode.key;
    }

    public void fftpaint(){
        barPaint.setStrokeWidth(1.0f);
        offsetLength = 2.0f;
        paintMode = mode.fft;
    }

    public void updateScale(int progress){
        barPaint.setStrokeWidth(512f/progress);
        offsetLength = barPaint.getStrokeWidth() + 1.0f;
    }
}
