package com.develjitsu.baccus.controller;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TabHost;

import com.develjitsu.baccus.R;
import com.develjitsu.baccus.model.Wine;

public class WineryActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winery);


        //Creamos models
        Wine bembibre = Wine.getDumyWine(1);
        Wine vegaval = Wine.getDumyWine(2);


        //Creamos TABS
        Intent intent = new Intent(this,WineActivity.class);
        intent.putExtra(WineActivity.EXTRA_WINE, bembibre);
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec = tabHost.newTabSpec(bembibre.getName()).setIndicator(bembibre.getName()).setContent(intent);
        tabHost.addTab(spec);


        intent = new Intent(this,WineActivity.class);
        intent.putExtra(WineActivity.EXTRA_WINE,vegaval);
        spec = tabHost.newTabSpec(vegaval.getName()).setIndicator(vegaval.getName()).setContent(intent);

        tabHost.addTab(spec);


/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

}
