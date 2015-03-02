package com.cearto.bluefire;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.List;


/**
 * A fragment representing a list of Items.
 */
public class BlueFireDeviceFragment extends ListFragment {
    private static final String TAG = "BlueFireDeviceFragment";
    private static List<BlueFireDevice> mDevices;
    private static ArrayAdapter<BlueFireDevice> bfd_adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.title_activity_prisoners_of_war);

        setUpList();
    }

    public void setUpList(){
        mDevices = BlueFireDeviceWorld.get(getActivity()).getDevices();
        bfd_adapter = new ArrayAdapter<BlueFireDevice>(getActivity(),
                android.R.layout.simple_list_item_1,
                mDevices);
        setListAdapter(bfd_adapter);
        bfd_adapter.notifyDataSetChanged();
    }

    public static void updateList(){
        Log.i(TAG, "Updating list...");
        bfd_adapter.notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        BlueFireDevice d = (BlueFireDevice) getListAdapter().getItem(position);
        Log.d(TAG, d.getName() + "was clicked");
        Intent i = new Intent(getActivity(), BlueFireDeviceInspect.class);
        i.putExtra(BlueFireDevice.NAME, d.getName());
        i.putExtra(BlueFireDevice.ADDR, d.getAddress());
        startActivity(i);
    }
}
