package com.inkbird.recyclerdemo.waveprogress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class Solid extends View {
    private Paint aboveWavePaint;
    private Paint blowWavePaint;

    private int aboveStartColor;
    private int aboveEndColor;
    private int belowStartColor;
    private int belowEndColor;

    public Solid(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Solid(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        setLayoutParams(params);
    }

    public void setAboveWavePaint(Paint aboveWavePaint) {
        this.aboveWavePaint = aboveWavePaint;
    }

    public void setBlowWavePaint(Paint blowWavePaint) {
        this.blowWavePaint = blowWavePaint;
    }

    public int getAboveStartColor() {
        return aboveStartColor;
    }

    public void setAboveStartColor(int aboveStartColor) {
        this.aboveStartColor = aboveStartColor;
    }

    public int getAboveEndColor() {
        return aboveEndColor;
    }

    public void setAboveEndColor(int aboveEndColor) {
        this.aboveEndColor = aboveEndColor;
    }

    public int getBelowStartColor() {
        return belowStartColor;
    }

    public void setBelowStartColor(int belowStartColor) {
        this.belowStartColor = belowStartColor;
    }

    public int getBelowEndColor() {
        return belowEndColor;
    }

    public void setBelowEndColor(int belowEndColor) {
        this.belowEndColor = belowEndColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int blowColorSweep[] = {belowStartColor, belowEndColor};
        blowWavePaint.setShader(new LinearGradient(getLeft(), 0, getRight(), getBottom(), blowColorSweep, null, Shader.TileMode.CLAMP));
        canvas.drawRect(getLeft(), 0, getRight(), getBottom(), blowWavePaint);
        int aboveColorSweep[] = {aboveStartColor, aboveEndColor};
        aboveWavePaint.setShader(new LinearGradient(getLeft(), 0, getRight(), getBottom(), aboveColorSweep, null, Shader.TileMode.CLAMP));
        canvas.drawRect(getLeft(), 0, getRight(), getBottom(), aboveWavePaint);
    }
}
