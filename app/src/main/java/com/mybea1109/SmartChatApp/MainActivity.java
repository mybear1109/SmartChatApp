package com.mybea1109.SmartChatApp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.mybea1109.SmartChatApp.chat.GroupChatActivity;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendButton = findViewById(R.id.chatButton);
        progressBar = findViewById(R.id.progressBar);

        // 채팅방 이동 버튼 클릭 시
        sendButton.setOnClickListener(v -> {
            // 로딩 시작
            progressBar.setVisibility(View.VISIBLE);

            // 채팅방으로 이동 (애니메이션 추가)
            Intent intent = new Intent(MainActivity.this, GroupChatActivity.class);
            startActivity(intent);

            // 애니메이션 추가: 화면 전환을 부드럽게 만들어줍니다.
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            // 로딩 종료 (새로운 화면이 열리면 로딩을 숨깁니다)
            progressBar.setVisibility(View.GONE);
        });
    }
}
