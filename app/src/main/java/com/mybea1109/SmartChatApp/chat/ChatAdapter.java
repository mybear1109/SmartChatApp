package com.mybea1109.SmartChatApp.chat;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mybea1109.SmartChatApp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<ChatMessage> messageList;
    private String senderId;

    public ChatAdapter(List<ChatMessage> messageList, String senderId) {
        this.messageList = messageList;
        this.senderId = senderId;
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;
        TextView timestampTextView;

        public ChatViewHolder(View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        // 내 메시지인지, 상대방의 메시지인지 구분
        ChatMessage message = messageList.get(position);
        if (message.getSenderId().equals(senderId)) {
            return 1; // 내 메시지
        } else {
            return 2; // 상대방 메시지
        }
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
            // 내 메시지 레이아웃
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_sent, parent, false);
        } else {
            // 상대방 메시지 레이아웃
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_received, parent, false);
        }
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        ChatMessage message = messageList.get(position);
        holder.messageTextView.setText(message.getMessage());

        // 시간 포맷
        String time = new SimpleDateFormat("HH:mm", Locale.getDefault())
                .format(new Date(message.getTimestamp()));
        holder.timestampTextView.setText(time);

        // 내 메시지와 상대방 메시지 구분
        if (message.getSenderId().equals(senderId)) {
            // 내 메시지 - 오른쪽 정렬
            holder.messageTextView.setGravity(Gravity.END);
            holder.timestampTextView.setGravity(Gravity.START); // 내 메시지 시간은 왼쪽
        } else {
            // 상대방 메시지 - 왼쪽 정렬
            holder.messageTextView.setGravity(Gravity.START);
            holder.timestampTextView.setGravity(Gravity.END); // 상대방 메시지 시간은 오른쪽
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
