package com.example.organicminded;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class verifySmsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verifysms);

        final String[] digitCodeInput = {""};
        final smsCodeSender smsSender = (smsCodeSender) getIntent().getSerializableExtra("Sender");

        requsetSMSPermission();

        final EditText verifyEditText = findViewById(R.id.verify_edit_text);
        final Button btnVerifyOkButton = findViewById(R.id.verify_ok_button);
        final Button resendVerifyCode = findViewById(R.id.resend_verify_code);

        new verifyReciver().setEditText(verifyEditText);

        btnVerifyOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                digitCodeInput[0] = verifyEditText.getText().toString();
                String digitCode = smsSender.getDigitCode();
//                Log.e("inpput", "onClick: " + digitCodeInput[0] );
//                Log.e("genrate", "onClick: " + digitCode );
                if (digitCodeInput[0].equals(digitCode))
                {
                    Toast.makeText(getApplicationContext(),"yes",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                }
            }
        });



        resendVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsSender.smsCodeSend(getApplicationContext(), new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {

                    }
                });
            }
        });





    }

    private void requsetSMSPermission() {

        String permission = Manifest.permission.RECEIVE_SMS;

        int grant = ContextCompat.checkSelfPermission(this,permission);

        if(grant != PackageManager.PERMISSION_GRANTED)
        {
            String[] permissionLists = new String[1];
            permissionLists[0] = permission;
            ActivityCompat.requestPermissions(this,permissionLists,1);
        }
    }
}
