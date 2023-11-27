package com.inkbird.recyclerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.airbnb.lottie.LottieDrawable;
import com.inkbird.recyclerdemo.databinding.ActivityLottieViewBinding;

public class LottieViewActivity extends AppCompatActivity {
    private ActivityLottieViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLottieViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.lavLottieView.setAnimation("myJson/pairing.json");
        binding.lavLottieView.setRepeatCount(LottieDrawable.INFINITE);
        binding.lavLottieView.playAnimation();
    }
}