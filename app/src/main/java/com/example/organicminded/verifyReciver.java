package com.example.organicminded;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.EditText;

public class verifyReciver extends BroadcastReceiver {

    private  static EditText editText;

    public void setEditText(EditText editText)
    {
        verifyReciver.editText = editText;
    }


    @Override
    public void onReceive(Context context, Intent intent) {

        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

        for(SmsMessage sms:messages)
        {
            String message = sms.getMessageBody();
            String verifyCode = message.split(":")[1];
            editText.setText(verifyCode);
        }
    }
}
