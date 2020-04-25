package com.example.chatproject;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT =800;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {  // 쓰레드를 생성하여 실행
            @Override
            public void run() {
                finish();
            }
        },SPLASH_TIME_OUT); // 8초만큼 화면을 보여준 후 종료
    }
}