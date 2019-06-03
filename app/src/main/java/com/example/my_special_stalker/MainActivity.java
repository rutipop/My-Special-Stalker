package com.example.my_special_stalker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_ALL = 123;
    String[] PERMISSIONS = {
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.PROCESS_OUTGOING_CALLS,

    };

    EditText phone_num;
    EditText message_to_show;
    TextView info_updater_headline;

    Gson gson = new Gson();
    Info_entered info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        phone_num = (EditText) findViewById(R.id.filled_phone);
        message_to_show = (EditText) findViewById(R.id.what_to_send);
        info_updater_headline = (TextView) findViewById(R.id.filled_in_status);


        loadSP();
        //*****************************************************************************************
        //show the sp on the screen:
        message_to_show.setText(info.getMsg_to_send());
        phone_num.setText(info.getPhone_number());
        info_updater_headline.setText(info.getInfo_headline());

        //*****************************************************************************************
        //check permissions:
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        } else {
            if_has_permissions();
        }


    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults){

        switch (requestCode)
        {
            case PERMISSION_ALL:
            {
                if (grantResults.length == 3
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED)
                {
                    if_has_permissions();
                }

            }
        }

    }







    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }







    public void setPhoneNumberWatcher(final EditText num, final TextView status_headline ){

        num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                info.setPhoneNumber(num.getText().toString());
                status_headline.setText(info.getInfo_headline());
                saveSP();

                    Intent intent = new Intent("updated_info");
                    Gson gson = new Gson();
                    Type type = new TypeToken<Info_entered>() {
                    }.getType();
                    String json = gson.toJson(info, type);
                    intent.putExtra("extra", json);
                    sendBroadcast(intent);




            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public void setMSGrWatcher(final EditText msg, final TextView status_headline ) {
        msg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                info.setMSG(msg.getText().toString());
                status_headline.setText(info.getInfo_headline());
                saveSP();


                    Intent intent = new Intent("updated_info");
                    Gson gson = new Gson();
                    Type type = new TypeToken<Info_entered>() {
                    }.getType();
                    String json = gson.toJson(info, type);
                    intent.putExtra("extra", json);
                    sendBroadcast(intent);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    protected void if_has_permissions(){

        //*****************************************************************************************
        //start the text watchers:
        setPhoneNumberWatcher(phone_num, info_updater_headline);
        setMSGrWatcher(message_to_show, info_updater_headline);
        //*****************************************************************************************



    }


    public void saveSP(){
        //save preferences:
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String json = gson.toJson(info);
        editor.putString("saved_info",json);
        editor.apply();
    }


    public  void  loadSP(){
        //load info:
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        String jsonLoad =sp.getString("saved_info",null);
        Type type = new TypeToken<Info_entered>(){}.getType();
        info = gson.fromJson(jsonLoad,type);
        if (info == null) {
            info = new Info_entered();

        }
    }
}
