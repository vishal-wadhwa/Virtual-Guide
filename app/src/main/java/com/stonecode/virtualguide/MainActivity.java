package com.stonecode.virtualguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.manager.ProximityManagerContract;
import com.kontakt.sdk.android.ble.manager.listeners.EddystoneListener;
import com.kontakt.sdk.android.common.KontaktSDK;
import com.kontakt.sdk.android.common.profile.IEddystoneDevice;
import com.kontakt.sdk.android.common.profile.IEddystoneNamespace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProximityManagerContract proximityManager;
    private HashMap<String,Integer> hmIID=new HashMap<>();
    private ArrayList<Integer> discoveredBeacons=new ArrayList<>();
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        KontaktSDK.initialize("YBVNhCQdbYqQtkxOZyAPmyshZkQXhKUl");
        proximityManager = new ProximityManager(this);
        proximityManager.setEddystoneListener(createEddystoneListener());

        hmIID.put("0117c55be3a8",1);
        hmIID.put("0117c55d6660",2);
        hmIID.put("0117c555c65f",3);
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
                Log.d("Sjsj", "Eddystone discovered: namespace=" + eddystone.getNamespace()+"instance id ="+eddystone.getInstanceId()+" uid="+eddystone.getUniqueId());
                if(!discoveredBeacons.contains(hmIID.get(eddystone.getInstanceId()))){
                    discoveredBeacons.add(hmIID.get(eddystone.getInstanceId()));
                }

            }

            @Override
            public void onEddystonesUpdated(List<IEddystoneDevice> eddystones, IEddystoneNamespace namespace) {

            }

            @Override
            public void onEddystoneLost(IEddystoneDevice eddystone, IEddystoneNamespace namespace) {
                if(discoveredBeacons.contains(hmIID.get(eddystone.getInstanceId()))){
                    discoveredBeacons.remove(hmIID.get(eddystone.getInstanceId()));
                }
            }
        };
    }
}
