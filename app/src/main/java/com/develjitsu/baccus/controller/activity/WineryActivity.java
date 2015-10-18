package com.develjitsu.baccus.controller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.develjitsu.baccus.R;
import com.develjitsu.baccus.controller.fragment.WineryFragment;

public class WineryActivity extends FragmentContainerActivity {

    public static final String EXTRA_WINE_INDEX = "com.develjitsu.baccus.controller.activity.WineryActivity.EXTRA_WINE_INDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (Exception e){
            Log.v("RAMON", e.getMessage());
        }
    }

    @Override
    protected Fragment createFragment() {
        return WineryFragment.newInstance(getIntent().getIntExtra(EXTRA_WINE_INDEX,0));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            //Podemos hacer un finish a lo burro o podemos hacerlo por navutils
            //esto implica definir padre en el XML manifest de la activity
            //finish();
            NavUtils.navigateUpFromSameTask(this);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
