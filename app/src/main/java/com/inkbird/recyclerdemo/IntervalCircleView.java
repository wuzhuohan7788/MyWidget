package com.inkbird.recyclerdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class IntervalCircleView extends ConstraintLayout {
    public IntervalCircleView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public IntervalCircleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_int_14_internal, this, true);
    }
}
