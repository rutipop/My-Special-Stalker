package com.example.my_special_stalker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class MSGReciver extends BroadcastReceiver {

    String DELIVERED = "SMS_DELIVERED";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if(action != null && action.equals(DELIVERED)){

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1")
                    .setSmallIcon(R.drawable.ic_action_name)
                    .setContentTitle("message status...")
                    .setContentText("message sent successfully")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(1, builder.build());

        }


    }
}
