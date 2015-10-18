package com.develjitsu.baccus.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.develjitsu.baccus.R;
import com.develjitsu.baccus.controller.fragment.WineListFragment;
import com.develjitsu.baccus.controller.fragment.WineryFragment;

/**
 * Created by hadock on 17/10/15.
 *
 */
public class WineListActivity extends AppCompatActivity implements WineListFragment.OnWineSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_list);

        //Seteamos toolbar
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        FragmentManager fm = getSupportFragmentManager();

        if(findViewById(R.id.list)!=null){
            Fragment listFragment = fm.findFragmentById(R.id.list);
            if(listFragment==null){
                listFragment = new WineListFragment();
                fm.beginTransaction().add(R.id.list,listFragment).commit();
            }
        }

        if(findViewById(R.id.winery)!=null){
            Fragment wineryFragment = fm.findFragmentById(R.id.winery);
            if(wineryFragment == null){
                int prefSelectedWine = PreferenceManager.getDefaultSharedPreferences(this).getInt(WineryFragment.PREF_LAST_WINE_INDEX,0);
                wineryFragment = WineryFragment.newInstance(prefSelectedWine);
                fm.beginTransaction().add(R.id.winery,wineryFragment).commit();
            }
        }
    }

    @Override
    public void onWineSelected(int wineIndex) {
        //detectamos dipositivo
        WineryFragment wineryFragment = (WineryFragment) getSupportFragmentManager().findFragmentById(R.id.winery);
        //tablet horizontal
        if(wineryFragment!=null){
            wineryFragment.changeWine(wineIndex);
        }else{
            //no tablet horizontal
            Intent wineryIntent = new Intent(this, WineryActivity.class);
            wineryIntent.putExtra(WineryActivity.EXTRA_WINE_INDEX,wineIndex);
            startActivity(wineryIntent);
        }
    }
}
