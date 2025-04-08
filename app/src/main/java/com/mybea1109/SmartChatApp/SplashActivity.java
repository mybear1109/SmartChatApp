package com.mybea1109.SmartChatApp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // 레이아웃 연결

        new Handler().postDelayed(() -> {
            // 2초 뒤 MainActivity로 이동
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish(); // 현재 액티비티 종료
        }, 2000);
    }
}
