package com.abayomi.stockbay.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.abayomi.stockbay.ProgressBarAnimation;
import com.abayomi.stockbay.R;

public class LoadingActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar = findViewById(R.id.progress_loading);
        textView = findViewById(R.id.txt_loading);

        progressBar.setMax(100);
        progressBar.setScaleY(3f);


        progressBarAnimation();
    }

    public void progressBarAnimation() {

        ProgressBarAnimation animation = new ProgressBarAnimation(this, progressBar, textView, 0f, 100f);
        animation.setDuration(800);
        progressBar.setAnimation(animation);
    }
}