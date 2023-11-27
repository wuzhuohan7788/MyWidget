package com.inkbird.recyclerdemo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.inkbird.recyclerdemo.databinding.ActivityBlurViewBinding;

public class BlurViewActivity extends AppCompatActivity {
    private ActivityBlurViewBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlurViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
