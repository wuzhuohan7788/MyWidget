package com.inkbird.recyclerdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CircleView extends View {
    //头部色
    private int startColor = 0xfff37813;
    //尾部色
    private int endColor = 0xfff37813;
    //背景色
    private int bgProgressColor = 0xfff4f4f4;
    //目标进度
    private int max;
    //当前进度
    private int current;
    private int width = 300;
    private int height = 300;

    private float roundWidth = 30;
    private int pattern = 0;
    private boolean isHead = false;
    private Bitmap progressHead = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_int_14_circle_progress_head);
    //背景色画笔
    private Paint bgProgressPaint;
    //进度条画笔
    private Paint progressPaint;
    private Paint bitmapPaint;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs == null) return;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = array.getIndex(i);
            if (attr == R.styleable.CircleView_cv_progressStartColor) {
                startColor = array.getColor(attr, 0xfff37813);
            } else if (attr == R.styleable.CircleView_cv_progressEndColor) {
                endColor = array.getColor(attr, 0xfff37813);
            } else if (attr == R.styleable.CircleView_cv_progressBackGroundColor) {
                bgProgressColor = array.getColor(attr, 0xfff4f4f4);
            } else if (attr == R.styleable.CircleView_cv_ringWidth) {
                roundWidth = array.getInteger(attr, 30);
            } else if (attr == R.styleable.CircleView_cv_pattern) {
                pattern = array.getInteger(attr, 0);
            } else if (attr == R.styleable.CircleView_cv_progressHead) {
                isHead = array.getBoolean(attr, false);
            }
        }
        array.recycle();
        init();
    }

    private void init() {
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStrokeCap(Paint.Cap.SQUARE);
        progressPaint.setStrokeWidth(roundWidth);
        progressPaint.setColor(startColor);
        progressPaint.setStyle(Paint.Style.STROKE);

        bgProgressPaint = new Paint();
        bgProgressPaint.setAntiAlias(true);
        bgProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        bgProgressPaint.setStrokeWidth(roundWidth);
        bgProgressPaint.setColor(bgProgressColor);
        bgProgressPaint.setStyle(Paint.Style.STROKE);

        bitmapPaint = new Paint();
        bitmapPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMod = MeasureSpec.getMode(widthMeasureSpec);
        int heightMod = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMod == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        } else if (widthMod == MeasureSpec.AT_MOST) {
            width = getWidth() + getPaddingLeft() + getPaddingRight();
        }

        if (heightMod == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        } else if (heightMod == MeasureSpec.AT_MOST) {
            height = getWidth() + getPaddingTop() + getPaddingBottom();
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    float centerX;
    float centerY;
    float radius;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        radius = (int) (Math.min(centerX, centerY) - roundWidth / 2);
        RectF rf = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        drawBgProgress(canvas);
        drawProgress(canvas, rf);
    }

    /**
     * 进度槽
     *
     * @param canvas
     */
    private void drawBgProgress(Canvas canvas) {
        canvas.drawCircle(centerX, centerY, radius, bgProgressPaint);
    }

    /**
     * 进度条
     *
     * @param canvas
     * @param rf
     */
    private void drawProgress(Canvas canvas, RectF rf) {
        if (pattern == 1) {
            int colorSweep[] = {Color.TRANSPARENT, Color.parseColor("#F37813"), Color.parseColor("#E70345")};
            progressPaint.setShader(new SweepGradient(rf.centerX(), rf.centerY(), colorSweep, null));
        }
        int angle = 360 * current / max;
        canvas.drawArc(rf, 2, angle, false, progressPaint);
        canvas.drawBitmap(progressHead, getPointX(centerX - progressHead.getWidth() / 2, angle + 2), getPointY(centerY - progressHead.getHeight() / 2, angle + 2), bitmapPaint);
    }

    public void setCurrent(int current) {
        this.current = current;
        postInvalidate();
    }

    public void setMax(int max) {
        this.max = max;
        postInvalidate();
    }

    public float getPointX(float left, float angle) {
        return (float) (left + radius * Math.cos(angle * 3.14 / 180));
    }

    public float getPointY(float left, float angle) {
        return (float) (left + radius * Math.sin(angle * 3.14 / 180));
    }
}
