package com.develjitsu.baccus.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.develjitsu.baccus.R;
import com.develjitsu.baccus.controller.fragment.SettingsFragment;

/**
 * Created by hadock on 11/10/15.
 *
 */
public class SettingsActivity extends FragmentContainerActivity {

    public static final String EXTRA_WINE_IMAGE_SCALE_TYPE = "com.develjitsu.baccus.controller.activity.SettingsActivity.EXTRA_WINE_IMAGE_SCALE_TYPE";

    @Override
    protected Fragment createFragment() {
        Bundle arguments = new Bundle();
        arguments.putSerializable(SettingsFragment.ARG_WINE_IMAGE_SCALE_TYPE,getIntent().getSerializableExtra(EXTRA_WINE_IMAGE_SCALE_TYPE));

        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

}
