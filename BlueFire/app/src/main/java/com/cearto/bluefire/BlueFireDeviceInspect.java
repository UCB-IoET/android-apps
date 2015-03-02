package com.cearto.bluefire;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;


public class BlueFireDeviceInspect extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_fire_device_inspect);


        String title;
        String addr;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                title= null;
                addr = null;
            } else {
                title= extras.getString(BlueFireDevice.NAME);
                addr= extras.getString(BlueFireDevice.ADDR);
            }
        } else {
            addr = (String) savedInstanceState.getSerializable(BlueFireDevice.ADDR);
            title = (String) savedInstanceState.getSerializable(BlueFireDevice.NAME);
        }
        this.setTitle(title);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_blue_fire_device_inspect, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
