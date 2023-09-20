package com.inkbird.recyclerdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WaveView extends View {
    private static int centerColor;
    private Paint mPaint;
    private float alpharate;
    private boolean isStartAnim = false;
    private int mSpace;
    private int mWidth;
    private boolean isFillstyle;
    private List<Float> mCount = new ArrayList();
    private List<Integer> mAlphas = new ArrayList();
    private List<Float> widths = new ArrayList<>();


    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyWaveView, defStyleAttr, 0);
        centerColor = typedArray.getColor(R.styleable.MyWaveView_wavecolor, getResources().getColor(R.color.colorAccent));
        mSpace = typedArray.getInteger(R.styleable.MyWaveView_space, 100);
        isFillstyle = typedArray.getBoolean(R.styleable.MyWaveView_fillstyle, true);
        mWidth = typedArray.getInteger(R.styleable.MyWaveView_width, 400);
        typedArray.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        if (isFillstyle) {
            mPaint.setStyle(Paint.Style.FILL);
        } else {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(3);
        }
        alpharate = 255f / mWidth; //注意这里 如果为int类型就会为0,除数中f一定要加,默认int ;
        mAlphas.add(255);
        mCount.add(0f);
        widths.add(0f);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (isStartAnim) {
            invalidate();
        }
        mPaint.setColor(centerColor);
        for (int i = 0; i < mCount.size(); i++) { //遍历圆数目
            Integer cuAlpha = mAlphas.get(i);
            mPaint.setAlpha(cuAlpha);
            Float aFloat = widths.get(i);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, aFloat, mPaint); //画圆
            if (aFloat < mWidth) {  //扩散直径和透明度
                mAlphas.set(i, (int) (255 - alpharate * aFloat));
                mCount.set(i, aFloat + 1);
            }

        }

        if (widths.size() >= 5) {
            mAlphas.remove(0);
            mCount.remove(0);
        }

        if (widths.get(widths.size() - 1) == mSpace) {
            mAlphas.add(255);
            mCount.add(0f);
        }

    }


    public void startAnim() {
        isStartAnim = true;
        invalidate();
    }

    public void pauseAnim() {
        isStartAnim = false;
    }
}
