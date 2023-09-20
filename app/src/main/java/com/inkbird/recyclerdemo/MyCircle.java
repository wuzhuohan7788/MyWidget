package per.lijuan.circleprogress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class MyCircle extends View {
    private Paint mPaint;
    private Context context;

    public MyCircle(Context context) {
        super(context);
    }

    public MyCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.STROKE);
    }

    public MyCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int center = getWidth() / 2;
        int innerCircle = dp2px(context, 50); //內圆半径
        int ringWidth = dp2px(context, 5); //圆环宽度

        //绘制內圆
        this.mPaint.setColor(0xff165824);
        this.mPaint.setStrokeWidth(0);
        canvas.drawCircle(center, center, innerCircle, this.mPaint);

        //绘制圆环
        this.mPaint.setColor(0xffffaaff);
        this.mPaint.setStrokeWidth(ringWidth);
        canvas.drawCircle(center, center, innerCircle + 1 + ringWidth / 2, this.mPaint);

        this.mPaint.setColor(0xff000000);
        this.mPaint.setStrokeWidth(2);
        canvas.drawCircle(center, center, innerCircle + ringWidth, this.mPaint);
        super.onDraw(canvas);
    }

    private int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
