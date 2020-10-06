package com.example.organicminded;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.Socket;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button btn_go_sms = findViewById(R.id.btn_sing_up);
        final TextInputEditText txt_number_input = findViewById(R.id.text_number_input);


        new AsyncHttpTask().ConnectionStatus(this);



//            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext())
//                    .setTitle("")
//                    .setMessage("")
//                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    })
//                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    })
//                    .setIcon(R.drawable.phone_vector)
//                    .show();


//        new AsyncHttpTask().execute("www.google.com");


        btn_go_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pattern patternNumber = Pattern.compile("^09[0|1|2|3][0-9]{8}$");
                if(!patternNumber.matcher(txt_number_input.getText().toString()).matches())
                {
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
                    Log.e("sd", "onClick: "+String.valueOf(status ));
                    if (status) {
                        Toast.makeText(getApplicationContext(),R.string.str_sms_send,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, verifySmsActivity.class);
                        intent.putExtra("Sender", smsSender);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),R.string.str_sms_send_error,Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),R.string.str_phoneError,Toast.LENGTH_LONG).show();
                }

            }
        });


    }




    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            try {
                Socket s = new Socket(params[0], 80);
                s.close();
                result = 1;
            } catch (Exception e) {
                result = 0;
            }
            return result;
        }


        @Override
        protected void onPostExecute(Integer result) {
            if (result == 1) {
                Log.e("C", "onPostExecute: Connected ");
//                Toast.makeText(getApplicationContext(),"Connected",Toast.LENGTH_SHORT).show();
                setContentView(R.layout.activity_main);
            } else {
                Log.e("C", "onPostExecute: NotConnected ");
//                Toast.makeText(getApplicationContext(),"Not Connected",Toast.LENGTH_SHORT).show();
                setContentView(R.layout.no_internet_connection);
            }
        }

        public boolean ConnectionStatus(final Context context) {
            try {
                ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
                boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

                if (is3g)
                    return true;
                else if (isWifi)
                    return true;
                else {
                    String a = "0";
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setPositiveButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    builder.setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                ConnectionStatus(context);
                        }
                    });

                    builder.setTitle(R.string.str_Hoshdar);
                    builder.setMessage(R.string.str_noInternetConnection);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return false;
                }
            } catch (NullPointerException e) {
                return false;
            }


        }
    }
}


//    private void token_create(String phoneNumber) {
//
//
//
//
//        Context context;
//        final RequestQueue requestQueue = Volley.newRequestQueue(this);
//        final JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        Log.e("Resp", "onResponse: Rest Response" + response.toString());
//
//                        String Status;
//                        try {
//                            Status = ((JSONObject)response.get("return")).get("status").toString();
//                            Log.e("Sms Response", "onResponse: " + Status );
//                            if (Status.equals("200")) {
//                                Toast.makeText(getApplicationContext(), R.string.str_sms_send, Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(MainActivity.this,verifySmsActivity.class);
//                                intent.putExtra("digitCode",digitCode);
//                                MainActivity.this.startActivity(intent);
//                            }
//                            else
//                            {
//                                Toast.makeText(getApplicationContext(), Status.toString() + " Error", Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            Log.e("response", "onResponse: " + e.getMessage());
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("Resp", "onResponse: Rest Response" + error.toString());
//            }
//        });
//
//        try {
//            requestQueue.add(objectRequest);
//        } catch (Exception e){
//            Log.e("sms_token", "token_create: " + e.getMessage() );
//        }
//
//    }


