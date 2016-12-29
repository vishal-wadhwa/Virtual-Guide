package com.stonecode.virtualguide;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class RailwayActivity extends AppCompatActivity {

    //private String TRAIN_DATA = "http://10.0.0.30/Virtual-Guide/virtual-guide/v1/train-list/";
    ArrayList<String> trainList=new ArrayList<>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_railway);

        new JsonData().execute();
    }


    public class HttpHandler {

        private final String TAG = HttpHandler.class.getSimpleName();

        public HttpHandler() {
        }

        public String makeServiceCall(String reqUrl) {
            String response = null;
            URL req = null;
            try {
                URL url = new URL(reqUrl);
                req = url;
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                Log.d(TAG, "URL: " + req);
                // read the response
                InputStream in = new BufferedInputStream(conn.getInputStream());
                response = convertStreamToString(in);
                Log.d(TAG, "response: " + response);
            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException: " + e.getMessage());
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException: " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
            return response;
        }

        private String convertStreamToString(InputStream is) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                Log.d(TAG, "inputStream: ");
                while ((line = reader.readLine()) != null) {
                    Log.d(TAG, "inputStream: " + line);
                    sb.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//        Log.d(TAG,"inputStream: "+sb.toString());
            return sb.toString();
        }
    }

    private class JsonData extends AsyncTask<Void, Void, Void> {
        HttpHandler sh = new HttpHandler();
        String reqUrl = "http://10.0.0.30/Virtual-Guide/virtual-guide/v1/train-list/";
        JSONObject jsonObject;

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(RailwayActivity.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //station code
            String jsonStr = sh.makeServiceCall(reqUrl+"nzm");    //The JSON comes into jsonStr;
            JSONObject j = null;
            try {
                j = new JSONObject(jsonStr);
//                JSONArray j1 = new JSONArray(j);
//                Log.d(TAG,"j1: "+j1);
//                String s = j.getString("place");
//                Log.d(TAG, "s:: " + s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("json", "jsonStr: " + j);

//                Log.d(TAG, "jsonStr: " + j.getString("url"));
//                audioURI = Uri.parse(j.getString("url"));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
//            startDownload();

            progressDialog.dismiss();
            super.onPostExecute(aVoid);
        }
    }


}
