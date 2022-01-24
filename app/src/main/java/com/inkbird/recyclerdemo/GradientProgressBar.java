package com.inkbird.recyclerdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

public class GradientProgressBar extends View {
    /**
     * 字体大小
     */
    private int mTextSize;
    /**
     * 字体颜色
     */
    private int mTextColor;
    /**
     * 渐变开始的颜色
     */
    private int mStartColor;
    /**
     * 渐变结束的颜色
     */
    private int mEndColor;
    /**
     * 进度条的宽
     */
    private int mProgressWidth;
    /**
     * 进度条的圆角大小
     */
    private int mRadius;
    /**
     * 默认进度条的颜色
     */
    private int mBgColor;
    /**
     * 进度条的当前进度
     */
    private float mCurrentProgress;
    /**
     * 加载的速度
     */
    private int mLoadSpeed;

    private String mContent = "0%";
    private Rect mBounds;
    private Paint mPaint;

    public GradientProgressBar(Context context) {
        this(context, null);
    }

    public GradientProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GradientProgressBar, defStyleAttr, 0);
        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {
            int index = array.getIndex(i);
            switch (index) {
                case R.styleable.GradientProgressBar_textSize:
                    /**
                     * 默认设置为16sp，TypeValue也可以把sp转化为px
                     */
                    mTextSize = array.getDimensionPixelSize(index, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.GradientProgressBar_textColor:
                    /**
                     * 默认设置为黑色
                     */
                    mTextColor = array.getColor(index, Color.BLACK);
                    break;
                case R.styleable.GradientProgressBar_startColor:
                    mStartColor = array.getColor(index, Color.BLACK);
                    break;
                case R.styleable.GradientProgressBar_endColor:
                    mEndColor = array.getColor(index, Color.BLACK);
                    break;
                case R.styleable.GradientProgressBar_bgColor:
                    mBgColor = array.getColor(index, Color.BLACK);
                    break;
                case R.styleable.GradientProgressBar_rectRadius:
                    mRadius = array.getDimensionPixelSize(index, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()
                    ));
                    break;
                case R.styleable.GradientProgressBar_lineWidth:
                    mProgressWidth = array.getDimensionPixelSize(index, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.GradientProgressBar_loadSpeed:
                    mLoadSpeed = array.getInt(index, 10);
                    break;
            }
        }
        array.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mBounds = new Rect();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mCurrentProgress < mProgressWidth) {
                    mCurrentProgress = mCurrentProgress + 1;
                    mContent = Math.round((mCurrentProgress / mProgressWidth) * 100) + "%";
                    try {
                        postInvalidate();
                        Thread.sleep(mLoadSpeed);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 设置画笔的属性
         */
        mPaint.setColor(mBgColor);
        mPaint.setStyle(Paint.Style.FILL);

        /**
         * 绘制背景圆角矩形
         */
        canvas.drawRoundRect(0, 0, mProgressWidth, getHeight(), mRadius, mRadius, mPaint);

        /**
         * 设置线性渐变,设置渐变开始的起点坐标和终点坐标，渐变开始和结束的颜色，设置镜像
         * 对于这个方法不太明白的可以google一下，这里不再详细说明
         */
        LinearGradient gradient = new LinearGradient(0, getHeight() / 2, mProgressWidth, getHeight() / 2,
                mStartColor, mEndColor, Shader.TileMode.MIRROR);
        mPaint.setShader(gradient);
        /**
         * 根据进度绘制圆角矩形
         */
        canvas.drawRoundRect(0, 0, mCurrentProgress, getHeight(), mRadius, mRadius, mPaint);

        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);

        /**
         * 获取绘制文本所需的矩形大小
         */
        mPaint.getTextBounds(mContent, 0, mContent.length(), mBounds);
        canvas.drawText(mContent, getWidth() / 2 - mBounds.width() / 2, getHeight() / 2 + mBounds.height() / 2, mPaint);
    }
}
