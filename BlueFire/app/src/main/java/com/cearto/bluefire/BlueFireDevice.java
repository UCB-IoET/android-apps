package com.cearto.bluefire;

import android.bluetooth.BluetoothDevice;
import android.text.format.Time;
import android.util.Log;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by cearto on 3/1/15.
 */
public class BlueFireDevice{
    private final static String TAG = "BlueFireDevice";
    public static final String NAME = "com.cearto.bluefire.name";
    public static final String ADDR = "com.cearto.bluefire.addr";
    public static final int[] ranges = {-51, -76, -87, -98, -107, -121};
    private UUID mId;
    private String mName;
    private String mAddress;
    private short mSignal;
    private Time mSeen;


    /* Constructor */
    public BlueFireDevice(BluetoothDevice d, short signal){
        mId = UUID.randomUUID();
        mName = d.getName();
        mAddress = d.getAddress();
        mSignal = signal;
        mSeen = new Time();
        mSeen.setToNow();
    }

    @Override
    public boolean equals(Object o) {
        return this.mAddress.equals(((BlueFireDevice) o).getAddress());
    }
    public void updateTime(){
        mSeen.setToNow();
    }
    public Time getLastSeen(){
        return mSeen;
    }
    public UUID getId() {
        return mId;
    }

    public void setId(UUID mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public short getSignal() {
        return mSignal;
    }

    public void setSignal(short mSignal) {
        this.mSignal = mSignal;
    }

    public int getSignalStrength(){
        for(int i = 0; i < ranges.length; i++){
            if(mSignal > ranges[i]) return ranges.length - i;
       }
       return 0;
    }
    public int getDistance(){
        return (mSignal + 50) * -1;
    }

    @Override
    public String toString() {
        Time now = new Time();
        now.setToNow();

        long lastSeen = (now.toMillis(true) - mSeen.toMillis(true))/ (long)1000.0;
        String msg = String.format("Name %s \n Addr: %s \n Signal: %2.0f (%1.0f) | Distance: ~%2.0f m\n lastSeen: %5.0f (s)",
                mName, mAddress, (float) mSignal, (float) getSignalStrength(), (float) getDistance(), (float) lastSeen);
        return msg;
    }
}
