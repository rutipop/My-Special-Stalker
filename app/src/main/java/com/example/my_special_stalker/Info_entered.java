package com.example.my_special_stalker;

public class Info_entered {

    private String phone_number;
    private  String msg_to_send;
    private String info_headline;

    Info_entered(){
        phone_number="";
        msg_to_send = "I'm going to call this number: ";
        info_headline = "I NEED MORE INFORMATION :";
    }

    void setPhoneNumber (String number){
        this.phone_number = number;
        update_headline();

    }
    void setMSG (String msg){
        this.msg_to_send = msg;
        update_headline();
    }

    String getPhone_number(){
        return this.phone_number;
    }

    String getMsg_to_send(){
        return this.msg_to_send;
    }

    String getInfo_headline(){
        return this.info_headline;
    }

    void update_headline (){
        if (phone_number.isEmpty() || msg_to_send.isEmpty()){
            info_headline = "I NEED MORE INFORMATION :";
        }
        else {
            info_headline = "APP IS READY TO SEND MESSAGES !";
        }
    }


}
