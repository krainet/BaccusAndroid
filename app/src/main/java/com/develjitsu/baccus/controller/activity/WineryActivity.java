package com.develjitsu.baccus.controller.activity;

import android.support.v4.app.Fragment;

import com.develjitsu.baccus.controller.fragment.WineryFragment;

public class WineryActivity extends FragmentContainerActivity {

    @Override
    protected Fragment createFragment() {
        return new WineryFragment();
    }
}
