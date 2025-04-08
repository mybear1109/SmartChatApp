package com.mybea1109.SmartChatApp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mybea1109.SmartChatApp.chat.GroupChatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 시스템 UI 침범 방지 (StatusBar, NavigationBar 등)
        EdgeToEdge.enable(this);

        // 레이아웃 설정
        setContentView(R.layout.activity_main);

        // 채팅방 이동 버튼 처리
        Button sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GroupChatActivity.class);
            startActivity(intent);
        });

        // 상태바/네비바 영역을 제외한 패딩 적용
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
