package com.example.c196.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.c196.MainActivity;
import com.example.c196.R;
import com.example.c196.ui.assessments.detailedAssessment;

public class AlarmReceiver extends BroadcastReceiver {
    String channel_id = "test";

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel(context, channel_id);
        Notification notification = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.wgu_marketinglogo_natl_rgb_color_owl_wgu_notag_stacked_7_2021)
                .setContentText(intent.getStringExtra("key"))
                .setContentTitle("Notification Test")
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(MainActivity.numNotify++, notification);

        /*
        Intent i = new Intent(context, detailedAssessment.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_IMMUTABLE);

        String channelId = "Channel_ID_Notification";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,channelId)
                .setSmallIcon(R.drawable.baseline_assessment_24)
                .setContentTitle("ALERT")
                .setContentText("You have assessment in 15min or less!")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        //Intent intent = new Intent(context, assessmentsActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        NotificationManagerCompat notificationManagerCompat =NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(++MainActivity.numNotify, builder.build());

         */
    }

    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        String channelId = "Channel_ID_Notification";
        String description = "Channel description here";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelId, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

    }
}