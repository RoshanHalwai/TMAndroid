package com.themaid.tmandroid;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.themaid.tmandroid.onboarding.pojo.CustomerRequest;

import java.util.Map;

/**
 * TheMaid
 * Created by Roshan Halwai on 3/24/2018.
 * Copyright © 2016 TheMaid Inc. All rights reserved.
 */

public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getData() != null) {
            Map<String, String> data = remoteMessage.getData();
            String title = data.get("title");
            String message = data.get("text");
            CustomerRequest customerRequest = new CustomerRequest(
                    data.get("customerName"),
                    data.get("customerPhone"),
                    data.get("customerAddress"),
                    data.get("serviceRequested")
            );
            sendNotification(title, message, customerRequest);
        }
    }

    private void sendNotification(String title, String message, CustomerRequest customerRequest) {
        Intent intent = new Intent(this, MaidBookings.class);
        intent.putExtra("CustomerRequest", customerRequest);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri uri = RingtoneManager.getDefaultUri(Notification.DEFAULT_SOUND);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(uri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(0, notificationBuilder.build());
        }

    }

}
