package com.develjitsu.baccus.controller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.develjitsu.baccus.R;
import com.develjitsu.baccus.controller.fragment.WebFragment;

/**
 * Created by hadock on 11/10/15.
 *
 */

public class WebActivity extends FragmentContainerActivity{

    public static final String EXTRA_WINE = "com.develjitsu.baccus.controller.activity.WebActivity.wine";


    @Override
    protected Fragment createFragment() {
        Bundle arguments = new Bundle();
        arguments.putSerializable(WebFragment.ARG_WINE, getIntent().getSerializableExtra(EXTRA_WINE));
        WebFragment fragment = new WebFragment();
        fragment.setArguments(arguments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (Exception e){
            Log.v("RAMON", e.getMessage());
        }

        return fragment;
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
