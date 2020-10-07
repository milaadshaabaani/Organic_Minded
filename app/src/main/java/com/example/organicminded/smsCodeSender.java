package com.example.organicminded;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Random;
import java.util.regex.Pattern;

public class smsCodeSender implements Serializable {
    private String apiKey;
    private String digitCode;
    private String number;
    private String message;
    private String url;
    private String apiCommand;
    private String sender;
    private String statusCode;

    private smsCodeSender(final Builder builder) {
        apiKey = builder.apiKey;
        digitCode = builder.digitCode;
        number = builder.number;
        message = builder.message;
        url = builder.url;
        apiCommand = builder.apiCommand;
        sender = builder.sender;
        statusCode = builder.statusCode;
    }


    public String getDigitCode(){return this.digitCode;}

    public void setStatusCode(String s) {
        this.statusCode = s.equals(null)?"":s;
    }

    public String getStatusCode() {
        return this.statusCode;
    }

    public boolean smsCodeSend(Context context) {

        final String[] status = new String[1];
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        final JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, this.apiCommand, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("smsCodeSendApiRespons", "smsCodeSend: Rest Response = " + response.toString());
                        try {
                            status[0] = ((JSONObject) response.get("return")).get("status").toString();
                            Log.e("smsCodeSendApiRespons", "smsCodeSend: " + status[0]);
                        } catch (JSONException e) {
                            Log.e("smsCodeSendApiRespons", "smsCodeSend catch: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("smsCodeSendApiError", "smsCodeSend: Rest Response = " + error.toString());
            }
        });
        requestQueue.add(objectRequest);
        this.setStatusCode(status[0]);
        return this.statusCode.equals("200") ? false : true;
    }


    static class Builder {
        private String apiKey;
        private String digitCode;
        private String number;
        private Pattern patternNumber = Pattern.compile("^09[0|1|2|3][0-9]{8}$");
        private String message;
        private String url;
        private String apiCommand;
        private String sender;
        private String statusCode;

        public Builder setApiKey(final String key) {
            this.apiKey = key;
            return this;
        }

        public Builder setSender(final String num) {
            this.sender = num;
            return this;
        }

        public Builder GeneratedigitCode(final double digit) {
            double range_start = Math.pow(10, digit - 1);
            double range_end = Math.pow(10, digit) - 1;
            double random = new Random().nextDouble() * (range_end - range_start) + range_start;
            this.digitCode = String.valueOf(Math.round(random));
            return this;
        }

        public Builder setNumber(final String num) {
            this.number = num;
            return this;
        }

        public Builder setMessage(final String message) {
            this.message = message;
            return this;
        }

        public Builder setUrl(final String url) {
            this.url = url;
            return this;
        }

        public Builder setApiCommand() {
            this.apiCommand = this.url + this.apiKey + "/sms/send.json?receptor=" + this.number + ",&message=" + this.message + this.digitCode;
            Log.e("command", "setApiCommand: " + this.apiCommand);
            return this;
        }

        public smsCodeSender create() {
            smsCodeSender smscodesender = new smsCodeSender(this);
            if (this.apiKey.equals("")) {
                throw new IllegalStateException(
                        "API key is empty!"
                );
            }
            if (!this.patternNumber.matcher(this.number).matches()) {
                throw new IllegalStateException(
                        "Mobile number format not valid!"
                );
            }
            if (this.sender.equals("")) {
                throw new IllegalStateException(
                        "Sender number is empty!"
                );
            }
            return smscodesender;
        }
    }
}
