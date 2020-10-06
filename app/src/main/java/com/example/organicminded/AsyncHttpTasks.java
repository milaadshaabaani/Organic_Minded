//package com.example.organicminded;
//
//import android.os.AsyncTask;
//import android.util.Log;
//
//import java.net.Socket;
//
//public class AsyncHttpTasks extends AsyncTask<String, Void, Integer> {
//
//
//
//    @Override
//    protected Integer doInBackground(String... params) {
//        Integer result = 0;
//        try {
//            Socket s = new Socket(params[0], 80);
//            s.close();
//            result = 1;
//        } catch (Exception e) {
//            result = 0;
//        }
//        return result;
//    }
//
//
//    @Override
//    protected void onPostExecute(Integer result) {
//        if (result == 1) {
//            Log.e("C", "onPostExecute: Connected ");
////                Toast.makeText(getApplicationContext(),"Connected",Toast.LENGTH_SHORT).show();
////                setContentView(R.layout.activity_main);
//            connectionCheck.connectionStatus()
//        } else {
//            Log.e("C", "onPostExecute: NotConnected ");
////                Toast.makeText(getApplicationContext(),"Not Connected",Toast.LENGTH_SHORT).show();
////                setContentView(R.layout.no_internet_connection);
//        }
//    }
//}