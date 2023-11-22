package com.example.sagarassignment2.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.sagarassignment2.R;

/**
 * Broadcast receiver to receive broadcast from alarm manager and notification
 */
public class NotificationClass extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("NOTIFICATION",intent.getStringExtra(titleExtra));

        Notification notification = new NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(intent.getStringExtra(titleExtra))
                .setContentText(intent.getStringExtra(messageExtra))
                .build();

        NotificationManager  manager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notificationID, notification);
    }

    public static final int notificationID = 1;
    public static final String channelID = "channel1";
    public static final String titleExtra = "titleExtra";
    public static final String messageExtra = "messageExtra";
}
