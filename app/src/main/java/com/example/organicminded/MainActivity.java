package com.example.organicminded;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        new connectionCheck(this, (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).connectionStatus();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Pattern patternNumber = Pattern.compile("^09[0|1|2|3][0-9]{8}$");
        final Button btn_go_sms = findViewById(R.id.btn_sing_up);
        final TextInputEditText txt_number_input = findViewById(R.id.text_number_input);

        txt_number_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                btn_go_sms.setEnabled(true);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (patternNumber.matcher(txt_number_input.getText().toString()).matches()) {

                    btn_go_sms.setClickable(true);
                    btn_go_sms.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));


                } else {
                    btn_go_sms.setClickable(false);
                    btn_go_sms.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));
                }

            }
        });

        btn_go_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsCodeSender smsSender = new smsCodeSender.Builder()
                        .setApiKey("482B65573437724E33306E4C7A5A6F324E4B4F526561427766694E445834695A6F6E506E32496F4D712B733D")
                        .GeneratedigitCode(5)
                        .setNumber(txt_number_input.getText().toString())
                        .setMessage("Your.OrganicMinded.Code:")
                        .setSender("981000596446")
                        .setUrl("https://api.kavenegar.com/v1/")
                        .setApiCommand()
                        .create();
                boolean status = smsSender.smsCodeSend(getApplicationContext());
                Log.e("sd", "onClick: " + String.valueOf(status));
                if (status) {
                    Toast.makeText(getApplicationContext(), R.string.str_sms_send, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, verifySmsActivity.class);
                    intent.putExtra("Sender", smsSender);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.str_sms_send_error, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}



