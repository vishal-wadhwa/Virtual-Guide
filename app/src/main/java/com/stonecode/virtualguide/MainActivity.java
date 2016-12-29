package com.stonecode.virtualguide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.manager.ProximityManagerContract;
import com.kontakt.sdk.android.ble.manager.listeners.EddystoneListener;
import com.kontakt.sdk.android.common.KontaktSDK;
import com.kontakt.sdk.android.common.profile.IEddystoneDevice;
import com.kontakt.sdk.android.common.profile.IEddystoneNamespace;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProximityManagerContract proximityManager;
    private HashMap<String, Integer> hmIID = new HashMap<>();
    private HashMap<String, String> room = new HashMap<>();
    private ArrayList<Integer> discoveredBeacons = new ArrayList<>();
    private static final String TAG = "MainActivity";
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static String placeName[] = new String[4];
    public static String placeIntro[] = new String[4];
    public static String placeImg[] = new String[4];
    public static String placeAudio[] = new String[4];
    public int count = 0;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = new Intent(MainActivity.this, TicketActivity.class);
        startActivity(i);
        KontaktSDK.initialize("YBVNhCQdbYqQtkxOZyAPmyshZkQXhKUl");
        proximityManager = new ProximityManager(this);
        proximityManager.setEddystoneListener(createEddystoneListener());

        hmIID.put("0117c55be3a8", 1);
        hmIID.put("0117c55d6660", 2);
        hmIID.put("0117c555c65f", 3);

        rv = (RecyclerView) findViewById(R.id.beacon_list);
        mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet(), MainActivity.this);
        rv.setAdapter(mAdapter);
        hmIID.put("0117c55ec086", 4);
        hmIID.put("0117c55fc452", 5);

        room.put("0117c55be3a8", "Humayun Tomb");
        room.put("0117c55d6660", "Humayun Tomb");
        room.put("0117c555c65f", "Humayun Tomb");

        room.put("0117c55ec086", "Entrance");

        room.put("0117c55fc452", "Railway Station");


        rv = (RecyclerView) findViewById(R.id.beacon_list);


    }

    @Override
    protected void onStart() {
        super.onStart();
        startScanning();
    }

    @Override
    protected void onStop() {
        proximityManager.stopScanning();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        proximityManager.disconnect();
        proximityManager = null;
        super.onDestroy();
    }

    private ArrayList<CardObject> getDataSet() {
        ArrayList results = new ArrayList<CardObject>();
        for (int index = 0; index < discoveredBeacons.size(); index++) {
//            CardObject obj = new CardObject(imageId[index], sourceId[index]); // make a map of images and the service and provide that here

//            CardObject obj = new CardObject(placeImg[index], placeName[index]); // make a map of images and the service and provide that here
            CardObject obj = null;
            if (discoveredBeacons.get(index) == 1) {
                obj = new CardObject(R.drawable.barcode, "Isa Khan's Complex");
            }
            if (discoveredBeacons.get(index) == 2) {
                obj = new CardObject(R.drawable.barcode, "Humanyun Room");
            }
            if (discoveredBeacons.get(index) == 3) {
                obj = new CardObject(R.drawable.barcode, "Hasi Ali Room");
            }
            if (discoveredBeacons.get(index) == 4) {
                obj = new CardObject(R.drawable.barcode, "ENtry Room");
            }
            if (discoveredBeacons.get(index) == 3) {
                obj = new CardObject(R.drawable.barcode, "Railway station");
            }

//            CardObject obj = new CardObject(R.drawable.uberlogo,"UBER");
            Log.d(TAG, "index:: " + index);
            results.add(index, obj);
        }
        return results;
    }

    private void startScanning() {
        proximityManager.connect(new OnServiceReadyListener() {
            @Override
            public void onServiceReady() {
                proximityManager.startScanning();
                Log.d(TAG, "onServiceReady: added");
            }
        });
    }

    private EddystoneListener createEddystoneListener() {
        return new EddystoneListener() {
            @Override
            public void onEddystoneDiscovered(IEddystoneDevice eddystone, IEddystoneNamespace namespace) {
                Log.d("Sjsj", "Eddystone discovered: namespace=" + eddystone.getNamespace() + "instance id =" + eddystone.getInstanceId() + " uid=" + eddystone.getUniqueId());
                if (!discoveredBeacons.contains(hmIID.get(eddystone.getInstanceId()))) {
                    discoveredBeacons.add(hmIID.get(eddystone.getInstanceId()));
                }

                mAdapter = new MyRecyclerViewAdapter(getDataSet(), MainActivity.this);
                rv.setAdapter(mAdapter);

                for (int i = 0; i < discoveredBeacons.size(); i++) {
                    Log.d(TAG, "disoc: " + discoveredBeacons.get(i));

                }
//                    mAdapter.notifyDataSetChanged();
            }


            @Override
            public void onEddystonesUpdated(List<IEddystoneDevice> eddystones, IEddystoneNamespace namespace) {
                double min = eddystones.get(0).getDistance();
                String IID = eddystones.get(0).getInstanceId();
                for (IEddystoneDevice i : eddystones) {
                    if (i.getDistance() < min) {
                        min = i.getDistance();
                        IID = i.getInstanceId();
                    }
                }
                setTitle(room.get(IID));
            }

            @Override
            public void onEddystoneLost(IEddystoneDevice eddystone, IEddystoneNamespace namespace) {
                if (discoveredBeacons.contains(hmIID.get(eddystone.getInstanceId()))) {
                    discoveredBeacons.remove(hmIID.get(eddystone.getInstanceId()));

                    mAdapter = new MyRecyclerViewAdapter(getDataSet(), MainActivity.this);
                    rv.setAdapter(mAdapter);

//                    mAdapter.notifyDataSetChanged();
                }
            }
        };
    }

    private class getAudio extends AsyncTask<Void, Void, Void> {
        HttpHandler sh = new HttpHandler();
        String reqUrl = "http://10.0.0.30/Virtual-Guide/virtual-guide/v1/humayun-tomb/1";
        JSONObject jsonObject;

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String jsonStr = sh.makeServiceCall(reqUrl);    //The JSON comes into jsonStr;
            JSONObject j = null;
            try {
                j = new JSONObject(jsonStr);
                JSONObject j1 = j.getJSONObject("place");
                placeAudio[count] = j1.getString("audio_url");
                placeImg[count] = j1.getString("image_url");
                placeIntro[count] = j1.getString("info");
                placeName[count] = j1.getString("name");

                Log.d(TAG, "place: " + placeAudio[count] + "\n" + placeName[count]);
                Log.d(TAG, "j1: " + j1);
                String s = j.getString("place");
                Log.d(TAG, "s:: " + s);
                count++;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d(TAG, "jsonStr: " + j);

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
