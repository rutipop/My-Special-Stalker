package com.example.my_special_stalker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class MSGsent extends BroadcastReceiver {



    String SENT = "SMS_SENT";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if( action!=null && action.equals(SENT)){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1")
                    .setSmallIcon(R.drawable.ic_action_name)
                    .setContentTitle("message status...")
                    .setContentText("your phone is sending the message")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(1, builder.build());


        }



    }
}
