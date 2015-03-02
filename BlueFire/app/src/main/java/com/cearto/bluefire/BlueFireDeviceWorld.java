package com.cearto.bluefire;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Singleton data structure pg. 169.
 * Created by cearto on 3/1/15.
 */
public class BlueFireDeviceWorld {
    private static final String TAG = "BlueFireWorld";
    private static boolean isSorted = false;

    private List<BlueFireDevice> mDevices;
    private static BlueFireDeviceWorld sBlueFireDeviceWorld;
    private Context mAppContext;


    private BlueFireDeviceWorld(Context appContext){
        mAppContext = appContext;
        mDevices = new ArrayList<BlueFireDevice>();
    }


    public static BlueFireDeviceWorld get(Context c){
        if(sBlueFireDeviceWorld == null){
            sBlueFireDeviceWorld = new BlueFireDeviceWorld(c.getApplicationContext());
        }
        return sBlueFireDeviceWorld;
    }


    public List<BlueFireDevice> getDevices(){
        if(isSorted){
            //by signal strength
            Collections.sort(mDevices, new Comparator<BlueFireDevice>() {
                @Override
                public int compare(BlueFireDevice b1, BlueFireDevice b2) {
                    if (b1.getDistance() > b2.getDistance())
                        return 1;
                    if (b1.getDistance() < b2.getDistance())
                        return -1;
                    return 0;
                }
            });
        } else{
            //by recency
            Collections.sort(mDevices, new Comparator<BlueFireDevice>() {
                @Override
                public int compare(BlueFireDevice b1, BlueFireDevice b2) {
                    if (b1.getLastSeen().after(b2.getLastSeen()))
                        return -1;
                    if (b1.getLastSeen().before(b2.getLastSeen()))
                        return 1;
                    return 0;
                }
            });
        }
        return mDevices;
    }

    public void addDevice(BlueFireDevice bfd){
        BlueFireDevice listed = inList(bfd);
        if(listed != null){
            listed.setSignal(bfd.getSignal());
            listed.updateTime();
        } else {
            mDevices.add(bfd);
        }
        BlueFireDeviceFragment.updateList();
    }

    public BlueFireDevice inList(BlueFireDevice other){
        for(BlueFireDevice d: mDevices){
            if(d.getAddress().equals(other.getAddress())) {
                Log.i(TAG, other.toString() + "is already seen");
                return d;
            }
        }
        return null;
    }

    public BlueFireDevice getDevice(UUID id){
        for(BlueFireDevice d: mDevices){
            if(d.getId().equals(id))
                return d;
        }
        return null;
    }
    public boolean toggleSorted(){
        isSorted = !isSorted;
        return isSorted;
    }


}
