package com.example.organicminded;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

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
        final TextInputEditText txt_number_input = findViewById(R.id.text_number_input);
        final View view = findViewById(R.id.btn_sing_up);

        view.setEnabled(false);
        final progressButton progressButton_ = new progressButton(MainActivity.this,view,R.string.str_send,R.color.Button);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final smsCodeSender smsSender = new smsCodeSender.Builder()
                        .setApiKey("482B65573437724E33306E4C7A5A6F324E4B4F526561427766694E445834695A6F6E506E32496F4D712B733D")
                        .GeneratedigitCode(5)
                        .setNumber(txt_number_input.getText().toString())
                        .setMessage("Your.OrganicMinded.Code:")
                        .setSender("981000596446")
                        .setUrl("https://api.kavenegar.com/v1/")
                        .setApiCommand()
                        .create();

                smsSender.smsCodeSend(getApplicationContext(), new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        try {
                            String status = ((JSONObject) result.get("return")).get("status").toString();
                            smsSender.setStatusCode(status);
                            Log.e("smsCodeSendApiRespons", "smsCodeSend: " + status);
                        } catch (JSONException e) {
                            Log.e("smsCodeSendApiRespons", "smsCodeSend catch: " + e.getMessage());
                        }
                    }
                });


                progressButton_.buttonActivated(R.string.str_sending);
                view.setEnabled(false);
                final Handler  handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        String status = smsSender.getStatusCode();
                        if(status.equals("200")) // if api response was '200' verify code send success
                        {
                            progressButton_.buttonFinished(R.string.str_sended);
                            Handler handler1 = new Handler();
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(MainActivity.this, verifySmsActivity.class);
                                    intent.putExtra("Sender", smsSender);
                                    startActivity(intent);
                                }
                            },1000);
                        }
                        else
                        {
                            progressButton_.buttonError(R.string.str_error);
                            Handler handler1 = new Handler();
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressButton_.buttonRestart(R.string.str_send);
                                    view.setEnabled(true);
                                }
                            },1000);
                        }



                    }
                },3000);

            }
        });


        txt_number_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (patternNumber.matcher(txt_number_input.getText().toString()).matches()) {
                    view.setEnabled(true);
                } else {
                    view.setEnabled(false);
                }

            }
        });

    }
}



