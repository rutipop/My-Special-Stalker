package com.example.my_special_stalker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import static android.support.v4.content.ContextCompat.getSystemService;


public class CallReciver extends BroadcastReceiver {
    Gson gson = new Gson();
    String CHANNEL_ID_FOR_NOTIF ="1";
    Info_entered info ;
    SharedPreferences sp;


    @Override
    public void onReceive(Context context, Intent intent) {
        sp = context.getSharedPreferences("saved_info_in_brodcast",Context.MODE_PRIVATE);
        String action = intent.getAction();
        loadSP();


        //*****************************************************************************************
        // create notification channel with id 1 for this app notifications::

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID_FOR_NOTIF, "ringing", importance);
            channel.setDescription("this phone is ringing now");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager notificationManager =getSystemService(context,NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }



        //*****************************************************************************************

        if(action.equals("updated_info")){

            Gson g=new Gson();
            Type type=new TypeToken<Info_entered>(){}.getType();
            info = new Info_entered();
            String state2 = intent.getExtras().getString("extra");
            info = g.fromJson(state2,type);
            saveSP();
        }


        if (action.equals(Intent.ACTION_NEW_OUTGOING_CALL)) { // this is an outgoing call:

                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //send notification of outgoing call:
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID_FOR_NOTIF)
                        .setSmallIcon(R.drawable.ic_action_name)
                        .setContentTitle("hey...")
                        .setContentText("your phone is ringing!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                notificationManager.notify(1, builder.build());

                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                if(info!=null && !info.getPhone_number().isEmpty()) {

                        //opening our service which is responsible for sending the SMS and updating the notification:
                        Intent intentService = new Intent(context, MyService.class);
                        intentService.setAction("opening_service");
                        intentService.putExtra("send_to",info.getPhone_number());
                        intentService.putExtra("text_to_send", info.getMsg_to_send() + " " + number);
                        context.startService(intentService);

                }
        }


    }




    public void saveSP(){
        //save preferences:
        SharedPreferences.Editor editor = sp.edit();
        String json = gson.toJson(info);
        editor.putString("saved_info_in_brodcast",json);
        editor.apply();
    }

    public  void  loadSP(){
        //load info:
        String jsonLoad =sp.getString("saved_info_in_brodcast",null);
        Type type = new TypeToken<Info_entered>(){}.getType();
        info = gson.fromJson(jsonLoad,type);
        if (info == null) {
            info = new Info_entered();

        }
    }




}
