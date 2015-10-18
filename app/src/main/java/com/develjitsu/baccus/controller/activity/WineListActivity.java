package com.develjitsu.baccus.controller.activity;

import android.os.Bundle;
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
public class WineListActivity extends AppCompatActivity {

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
                Log.v("RAMON", "Entramos LIST");
                fm.beginTransaction().add(R.id.list,listFragment).commit();
            }
        }

        if(findViewById(R.id.winery)!=null){
            Fragment wineryFragment = fm.findFragmentById(R.id.winery);
            if(wineryFragment == null){
                Log.v("RAMON","Entramos WINERY");
                wineryFragment = WineryFragment.newInstance(0);
                fm.beginTransaction().add(R.id.winery,wineryFragment).commit();
            }
        }
    }
}
