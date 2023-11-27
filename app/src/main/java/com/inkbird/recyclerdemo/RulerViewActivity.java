package com.inkbird.recyclerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.inkbird.recyclerdemo.ruler.RulerView;

public class RulerViewActivity extends AppCompatActivity {
    private RulerView rulerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruler_view);

        rulerView = findViewById(R.id.ruler_view);

        rulerView.setScope(0, 300, 5, "℉");
    }
}