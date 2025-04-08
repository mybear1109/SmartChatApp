package com.mybea1109.SmartChatApp.chat;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mybea1109.SmartChatApp.R;

import java.util.ArrayList;
import java.util.List;

public class GroupChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText messageInput;
    private Button sendButton;

    private ChatAdapter adapter;
    private List<ChatMessage> messageList = new ArrayList<>();

    private final String groupId = "group123";  // 예시 그룹 ID
    private String senderId = "guest";  // 기본값 설정

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        recyclerView = findViewById(R.id.chatRecyclerView);
        messageInput = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);

        // RecyclerView 설정, senderId 추가 전달
        adapter = new ChatAdapter(messageList, senderId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Firebase에서 현재 로그인한 사용자의 ID 가져오기
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            senderId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }

        // 메시지 전송 버튼 클릭 시 처리
        sendButton.setOnClickListener(v -> {
            String message = messageInput.getText().toString().trim();
            if (!message.isEmpty()) {
                sendMessageToGroup(groupId, message, senderId);  // Firestore에 메시지 전송
                messageInput.setText("");  // 입력창 초기화
            }
        });

        // Firestore에서 메시지 수신 리스너 등록
        listenToGroupMessages(groupId);
    }

    private void sendMessageToGroup(String groupId, String message, String senderId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        long timestamp = System.currentTimeMillis();  // 메시지 전송 시각

        // 새로운 채팅 메시지 객체 생성
        ChatMessage chatMessage = new ChatMessage(senderId, message, timestamp);

        // Firestore에 메시지 추가
        db.collection("chats")
                .document(groupId)
                .collection("messages")
                .add(chatMessage)
                .addOnSuccessListener(documentReference -> {
                    // 메시지 전송 성공 시 처리 (추가적인 로직이 필요하면 여기에 작성)
                })
                .addOnFailureListener(e -> {
                    // 메시지 전송 실패 시 처리
                });
    }

    private void listenToGroupMessages(String groupId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Firestore에서 채팅 메시지들을 가져옴
        db.collection("chats")
                .document(groupId)
                .collection("messages")
                .orderBy("timestamp")  // 메시지 시간 기준으로 정렬
                .addSnapshotListener((snapshots, error) -> {
                    if (error != null) {
                        // 에러 처리 (예: 로그 출력)
                        return;
                    }

                    if (snapshots != null && !snapshots.isEmpty()) {
                        messageList.clear();  // 기존 메시지 목록 초기화

                        // Firestore에서 메시지 목록을 가져와서 messageList에 추가
                        for (var doc : snapshots.getDocuments()) {
                            ChatMessage msg = doc.toObject(ChatMessage.class);
                            if (msg != null) {
                                messageList.add(msg);
                            }
                        }

                        // 어댑터에 데이터 변경을 알리고 RecyclerView 갱신
                        adapter.notifyDataSetChanged();

                        // 최신 메시지가 화면에 보이도록 스크롤
                        recyclerView.scrollToPosition(messageList.size() - 1);
                    }
                });
    }
}
