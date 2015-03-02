package com.cearto.bluefire;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;



public class BlueFireListActivity extends SingleFragmentActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_prisoners_of_war, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) { return true;}
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected Fragment createFragment() {
        return new BlueFireDeviceFragment();
    }
}
