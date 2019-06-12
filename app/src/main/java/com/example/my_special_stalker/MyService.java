package com.example.my_special_stalker;


import android.app.IntentService;
import android.app.PendingIntent;

import android.content.Intent;
import android.support.annotation.Nullable;

import android.telephony.SmsManager;


public class MyService extends IntentService {

    String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";

    public MyService() {
        super("notification Service By Ruti");
    }


    @Override
    public void onCreate() {
        super.onCreate();



    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        // create pending intents to listen to the message and update the notification accordingly:
        Intent sentIntent = new Intent(this,MSGsent.class);
        sentIntent.setAction(SENT);

        Intent recivedIntent = new Intent(this,MSGReciver.class);
        recivedIntent.setAction(DELIVERED);


        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent, 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,recivedIntent, 0);

        //*****************************************************************************************



        SmsManager.getDefault().sendTextMessage(intent.getStringExtra("send_to"),
                null, intent.getStringExtra("text_to_send"),
                sentPI, deliveredPI);



    }





}
