package com.inkbird.recyclerdemo.rulerview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.inkbird.recyclerdemo.R;
import com.inkbird.recyclerdemo.databinding.ActivityRulerViewBinding;

public class RulerViewActivity extends AppCompatActivity {
    private ActivityRulerViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRulerViewBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        initViewListener();
    }

    private void initViewListener() {
        binding.rulerView.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {

            }

            @Override
            public void onScrollResult(String result) {
                binding.value.setText(result);
            }
        });

        binding.btnOk.setOnClickListener(v -> binding.rulerView.setFirstScale(Float.parseFloat(binding.value.getText().toString())));
    }
}