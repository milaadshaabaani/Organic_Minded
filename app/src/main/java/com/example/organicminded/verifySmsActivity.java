package com.example.organicminded;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class verifySmsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verifysms);
        String digitCode = "";
        smsCodeSender smsSender = (smsCodeSender) getIntent().getSerializableExtra("Sender");


    }
}
