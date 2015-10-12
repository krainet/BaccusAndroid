package com.develjitsu.baccus.controller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

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
        arguments.putSerializable(WebFragment.ARG_WINE,getIntent().getSerializableExtra(EXTRA_WINE));
        WebFragment fragment = new WebFragment();
        fragment.setArguments(arguments);
        return fragment;
    }
}
