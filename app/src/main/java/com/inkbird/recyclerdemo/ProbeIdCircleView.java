package com.inkbird.recyclerdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ProbeIdCircleView extends View {
    private Context context;
    private int circleColor = 0xff000000;
    private int textColor = 0xffffffff;
    private String text = "1";
    private int textSize = 20;
    private Paint circlePaint;
    private Paint textPaint;
    private Rect mBound;
    private float ringWidth = 1;
    private int width;
    private int height;
    private boolean isFull = false;

    public ProbeIdCircleView(Context context) {
        this(context, null);
    }

    public ProbeIdCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProbeIdCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        if (attrs == null) return;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProbeIdCircleView);
        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.ProbeIdCircleView_pid_circleColor:
                    circleColor = array.getColor(attr, 0xff000000);
                    break;
                case R.styleable.ProbeIdCircleView_pid_textColor:
                    textColor = array.getColor(attr, 0xff000000);
                    break;
                case R.styleable.ProbeIdCircleView_pid_isFull:
                    isFull = array.getBoolean(attr, false);
                    break;
                case R.styleable.ProbeIdCircleView_pid_ringWidth:
                    ringWidth = array.getInteger(attr, 1);
                    break;
                case R.styleable.ProbeIdCircleView_pid_text:
                    text = array.getString(attr);
                    break;

            }
        }
        array.recycle();
        init();
    }

    private void init() {

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStrokeWidth(ringWidth);
        circlePaint.setColor(circleColor);
        circlePaint.setStrokeCap(Paint.Cap.ROUND);
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(DisplayUtil.dp2px(context, textSize));
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.STROKE);
        mBound = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), mBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMod = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMod = View.MeasureSpec.getMode(heightMeasureSpec);

        if (widthMod == View.MeasureSpec.EXACTLY) {
            width = View.MeasureSpec.getSize(widthMeasureSpec);
        } else if (widthMod == View.MeasureSpec.AT_MOST) {
            width = getWidth() + getPaddingLeft() + getPaddingRight();
        }

        if (heightMod == View.MeasureSpec.EXACTLY) {
            height = View.MeasureSpec.getSize(heightMeasureSpec);
        } else if (heightMod == View.MeasureSpec.AT_MOST) {
            height = getHeight() + getPaddingTop() + getPaddingBottom();
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = (int) (Math.min(centerX, centerY) - ringWidth / 2);
        drawCircle(centerX, centerY, radius, canvas);
        drawText(centerX, centerY, canvas);
    }

    private void drawText(int centerX, int centerY, Canvas canvas) {
        canvas.drawText(text, centerX - mBound.width() / 2, centerY + mBound.height() / 2, textPaint);
    }

    private void drawCircle(int centerX, int centerY, int radius, Canvas canvas) {
        canvas.drawCircle(centerX, centerY, radius, circlePaint);
    }
}
