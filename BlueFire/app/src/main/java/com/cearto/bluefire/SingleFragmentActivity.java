package com.cearto.bluefire;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

/**
 * Created by cearto on 3/2/15.
 */
public abstract class SingleFragmentActivity extends FragmentActivity {

    private final static int REQUEST_ENABLE_BT = 1;
    protected static final String TAG = "BlueFireListActivity";
    protected BluetoothAdapter mBluetoothAdapter;
    protected abstract Fragment createFragment();
    private BlueFireDeviceWorld mArrayAdapter;
    private Button mDiscoveryButton;
    private Button mSortButton;
    private Fragment fragment;


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            Log.i(TAG, "Action" + action + " " + BluetoothDevice.ACTION_FOUND);
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                short signal = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
                BlueFireDevice bfd = new BlueFireDevice(device, signal);

                Log.i(TAG, bfd.toString());
                mArrayAdapter.addDevice(bfd);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        mDiscoveryButton = (Button) findViewById(R.id.discovery_button);
        mSortButton = (ToggleButton) findViewById(R.id.sort_button);
        mSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSorted = mArrayAdapter.toggleSorted();
                BlueFireDeviceFragment.updateList();
            }
        });

        mDiscoveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getBluetooth();
            }
        });

        mArrayAdapter = BlueFireDeviceWorld.get(this);
        //start bluetooth process :)
        getBluetooth();

        FragmentManager fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.fragmentContainer);

        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

    public void getBluetooth(){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Log.i(TAG, "Registering the receiver");
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);


        if(mBluetoothAdapter == null){
            Log.i(TAG, "Bluetooth is not supported.");
            return;
        }
        Log.i(TAG, "BluetoothAdapter enabled? " + mBluetoothAdapter.isEnabled());
        if(!mBluetoothAdapter.isEnabled()){
            Log.i(TAG, "Starting bluetooth permission protocol");
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else{
            startBluetoothSearch();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK){
            Log.i(TAG, "Bluetooth enabled!");
            startBluetoothSearch();
        }
    }

    private void startBluetoothSearch(){
        Log.i(TAG, "Starting discovery");
//        mDiscoveryButton.setText(R.string.stop_discover);
        mBluetoothAdapter.startDiscovery();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

//    public void getPairedDevices(){
//        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
//        if(pairedDevices.size() > 0){
//            for(BluetoothDevice device : pairedDevices){
//                BlueFireDevice bfd = new BlueFireDevice(device, (short)-70);
//                mArrayAdapter.addDevice(bfd);
//            }
//        }
//    }
}

