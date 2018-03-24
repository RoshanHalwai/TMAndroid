package com.themaid.tmandroid;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.themaid.tmandroid.onboarding.pojo.CustomerRequest;

import java.util.Map;

/**
 * CityXcape
 * Created by Roshan Halwai on 3/24/2018.
 * Copyright © 2016 CityXcape Inc. All rights reserved.
 */

public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String message = remoteMessage.getNotification().getBody();

            Map<String, String> data = remoteMessage.getData();
            Object[] keys = data.values().toArray();
            CustomerRequest customerRequest = new CustomerRequest(
                    keys[0].toString(),
                    keys[1].toString(),
                    keys[2].toString(),
                    keys[3].toString()
            );
            sendNotification(title, message, customerRequest);
        }
    }

    private void sendNotification(String title, String message, CustomerRequest customerRequest) {
        Intent intent = new Intent(this, MaidBookings.class);
        intent.putExtra("CustomerRequest", customerRequest);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri uri = RingtoneManager.getDefaultUri(Notification.DEFAULT_SOUND);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(uri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(0, notificationBuilder.build());
        }
    }


}
