package com.mybea1109.SmartChatApp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private final String CHANNEL_ID = "chat_notification_channel";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ✅ 로그로 메시지 확인
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            Log.d("FCM", "Title: " + title);
            Log.d("FCM", "Body: " + body);

            // ✅ 실제 알림 표시
            showNotification(title, body);
        }
    }

    private void showNotification(String title, String message) {
        // ✅ 알림 클릭 시 이동할 액티비티 설정
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        );

        // ✅ 사운드 설정
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // ✅ 알림 빌더
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)  // 🔔 앱 아이콘
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)  // 클릭 시 제거
                .setSound(soundUri)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        // ✅ 채널 생성 (안드로이드 8.0 이상 필수)
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "채팅 알림",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, builder.build());
    }

    @Override
    public void onNewToken(String token) {
        Log.d("FCM", "New token: " + token);
        // TODO: 서버에 토큰 저장
    }
}
