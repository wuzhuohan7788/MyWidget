package com.inkbird.recyclerdemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import androidx.annotation.Nullable;

public class ProgressView extends View {
    private Context context;
    private Paint backgroundPaint;
    private Paint progressPaint;
    private Paint textPaint;
    private Paint picPaint;
    private int backgroundColor = Color.alpha(0xff000000);
    private int progressColor = Color.alpha(0xff341234);
    private int textColor = Color.alpha(0xffffffff);
    private int textSize = 28;
    private int mWidth;
    private int mHeight;
    private int progressHeight = 30;
    private int picWidth;
    private int picHeight;
    private float[] radiusArr = new float[]{0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};
    private float mProgress;
    private String textValue;

    public ProgressView(Context context) {
        super(context);
        initView(context);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        this.context = context;
        backgroundPaint = new Paint();
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(2);
        backgroundPaint.setAntiAlias(true);

        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        progressPaint.setStrokeWidth(2);
        progressPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);

        picPaint = new Paint();
        picPaint.setStyle(Paint.Style.STROKE);
        picPaint.setAntiAlias(true);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        textPaint.setColor(textColor);
        backgroundPaint.setColor(backgroundColor);
        progressPaint.setColor(progressColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPic(canvas);
        drawText(canvas);
        canvas.translate(0, picHeight);
        drawBackground(canvas);
        drawProgress(canvas);
    }

    private void drawPic(Canvas canvas) {
        if (mProgress == 0) {
            return;
        }

        float percent = this.mProgress / 100 * 1.0f;
        int width = (int) (percent * mWidth);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.pic);
        picWidth = bitmap.getWidth();
        picHeight = bitmap.getHeight();
        int pos = width - picWidth / 2;
        canvas.drawBitmap(bitmap, pos, 0, picPaint);
    }

    private void drawText(Canvas canvas) {
        if (mProgress == 0) {
            return;
        }

        float percent = this.mProgress / 100 * 1.0f;
        int width = (int) (percent * mWidth);
        float length = textPaint.measureText(textValue);
        canvas.drawText(textValue, width - length / 2, picHeight / 2 + 5, textPaint);
    }

    private void drawBackground(Canvas canvas) {
        RectF rectF = new RectF();
        rectF.top = 0;
        rectF.left = 0;
        rectF.right = mWidth;
        rectF.bottom = progressHeight + 6;

        allRoundRadius();
        Path path = new Path();
        path.addRoundRect(rectF, radiusArr, Path.Direction.CW);
        canvas.drawPath(path, backgroundPaint);
    }

    private void drawProgress(Canvas canvas) {
        if (mProgress == 0) {
            return;
        }
        float percent = this.mProgress / 100 * 1.0f;
        int width = (int) (percent * mWidth);
        RectF rectF = new RectF();
        rectF.top = 6;
        rectF.left = 6;
        rectF.right = width;
        rectF.bottom = progressHeight;

        if (mProgress < 100) {
            radiusArr[0] = 20;
            radiusArr[1] = 20;
            radiusArr[2] = 0;
            radiusArr[3] = 0;
            radiusArr[4] = 0;
            radiusArr[5] = 0;
            radiusArr[6] = 20;
            radiusArr[7] = 20;
        } else {
            allRoundRadius();
        }
        Path path = new Path();
        path.addRoundRect(rectF, radiusArr, Path.Direction.CW);
        canvas.drawPath(path, progressPaint);
    }

    private void allRoundRadius() {
        radiusArr[0] = 20;
        radiusArr[1] = 20;
        radiusArr[2] = 20;
        radiusArr[3] = 20;
        radiusArr[4] = 20;
        radiusArr[5] = 20;
        radiusArr[6] = 20;
        radiusArr[7] = 20;
    }

    public void setRoundRect(boolean isAllRound) {
        if (isAllRound) {
            allRoundRadius();
        }
        invalidate();
    }

    public void setProgress(float progress, float maxProgress) {
        this.mProgress = maxProgress;
        float percent = progress * 100.0f / maxProgress;
        if (percent < 0) {
            percent = 0;
        } else if (percent > 100) {
            percent = 100;
        }

        ValueAnimator animator = ValueAnimator.ofFloat(0, percent);
        animator.setDuration(1000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mProgress = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }

    public void setBackgroundColor(int color) {
        this.backgroundColor = color;
        invalidate();
    }

    public void setProgressColor(int color) {
        this.progressColor = color;
        invalidate();
    }

    public void setTextValue(String text) {
        this.textValue = text;
        invalidate();
    }

    public void setTextColor(int color) {
        this.textColor = color;
        invalidate();
    }
}
