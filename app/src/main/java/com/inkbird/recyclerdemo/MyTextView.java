package com.inkbird.recyclerdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

public class MyTextView extends View {
    private String mText;
    private int mTextSize = 15;
    private int mTextColor = Color.GRAY;
    private Paint mPaint;
    private Rect bound;

    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyTextView, defStyleAttr, 0);
        mText = array.getString(R.styleable.MyTextView_my_text);
        mTextSize = array.getDimensionPixelSize(R.styleable.MyTextView_my_textSize, sp2px(mTextSize));
        mTextColor = array.getColor(R.styleable.MyTextView_my_textColor, mTextColor);

        array.recycle();

        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMod = MeasureSpec.getMode(widthMeasureSpec);
        int heightMod = MeasureSpec.getMode(heightMeasureSpec);

        bound = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), bound);
        int width = 0;
        int height = 0;

        if (widthMod == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        } else if (widthMod == MeasureSpec.AT_MOST) {
            width = bound.width() + getPaddingLeft() + getPaddingRight();
        }
        if (heightMod == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        } else if (heightMod == MeasureSpec.AT_MOST) {
            height = bound.height() + getPaddingTop() + getPaddingBottom();
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(mText, getPaddingLeft(), baseLine, mPaint);
    }

    private int sp2px(int size) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, getResources().getDisplayMetrics());
    }
}
