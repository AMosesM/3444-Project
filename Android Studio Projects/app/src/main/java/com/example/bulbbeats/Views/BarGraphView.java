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

    float[] viewFFT;
    boolean enable;

    public BarGraphView(Context context) {
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

    private void init(@Nullable AttributeSet set)
    {
        barPaint = new Paint();
        barPaint.setStyle(Paint.Style.STROKE);
        barPaint.setStrokeWidth(1.0f);
        barPaint.setColor(Color.GREEN);

        enable = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float offset = 0;
        if(enable) {
            for (int i = 0; i < viewFFT.length; i++) {
                canvas.drawLine(offset, 725.0f - viewFFT[i], offset, 750.0f, barPaint);
                offset += 2.0f;
            }
        }
    }

    public void updateFFT(float[] fft)
    {
        viewFFT = fft;
    }

    public void enable()
    {
        enable = true;
    }

    public void disable()
    {
        enable = false;
    }
}
