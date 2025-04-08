package com.mybea1109.SmartChatApp.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.mybea1109.SmartChatApp.R;

public class ChatRoomListActivity extends AppCompatActivity {

    private ListView chatRoomListView;
    private String[] chatRooms = {"group123", "group456", "group789"}; // 예시 채팅방 목록

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_list);

        chatRoomListView = findViewById(R.id.chatRoomListView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                chatRooms
        );
        chatRoomListView.setAdapter(adapter);

        chatRoomListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedRoom = chatRooms[position];
            Intent intent = new Intent(ChatRoomListActivity.this, GroupChatActivity.class);
            intent.putExtra("groupId", selectedRoom);
            startActivity(intent);
        });
    }
}
