package com.develjitsu.baccus.controller.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.develjitsu.baccus.R;
import com.develjitsu.baccus.controller.activity.SettingsActivity;

/**
 * Created by hadock on 12/10/15.
 *
 */
public class SettingsFragment extends DialogFragment implements View.OnClickListener{

    public static final String ARG_WINE_IMAGE_SCALE_TYPE = "com.develjitsu.baccus.controller.activity.SettingsFragment.ARG_WINE_IMAGE_SCALE_TYPE";

    public static final String PREF_IMAGE_SCALE_TYPE = "imageScaleType";

    //Vistas
    private RadioGroup mRadioGroup = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_settings,container,false);

        mRadioGroup = (RadioGroup) root.findViewById(R.id.scale_type_radio);

        if(getArguments().getSerializable(ARG_WINE_IMAGE_SCALE_TYPE).equals(ImageView.ScaleType.FIT_XY)){
            mRadioGroup.check(R.id.fit_radio);
        }else if(getArguments().getSerializable(ARG_WINE_IMAGE_SCALE_TYPE).equals(ImageView.ScaleType.FIT_CENTER)){
            mRadioGroup.check(R.id.center_radio);
        }

        Button cancelButton = (Button) root.findViewById(R.id.cancel_button);
        Button saveButton = (Button) root.findViewById(R.id.save_button);

        cancelButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.cancel_button:
                cancelSettings();
                break;
            case R.id.save_button:
                saveSettings();
                break;
        }
    }

    private void cancelSettings() {

        if(getTargetFragment()!=null){
            getTargetFragment().onActivityResult(getTargetRequestCode(),Activity.RESULT_CANCELED,null);
            dismiss();
        }else{
            getActivity().setResult(Activity.RESULT_CANCELED);
            getActivity().finish();
        }
    }

    private void saveSettings() {

        ImageView.ScaleType selectedScaleType=null;


        Intent config = new Intent();
        //Salvo preferences
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();


        if(mRadioGroup.getCheckedRadioButtonId()==R.id.fit_radio){
            selectedScaleType = ImageView.ScaleType.FIT_XY;
        }else if(mRadioGroup.getCheckedRadioButtonId()==R.id.center_radio){
            selectedScaleType = ImageView.ScaleType.FIT_CENTER;
        }else{
            selectedScaleType=ImageView.ScaleType.FIT_CENTER;
        }

        config.putExtra(SettingsActivity.EXTRA_WINE_IMAGE_SCALE_TYPE, selectedScaleType);
        editor.putString(PREF_IMAGE_SCALE_TYPE,selectedScaleType.toString());
        editor.commit();

        //Si me han  llamado desde un fragment
        if(getTargetFragment()!=null){
            getTargetFragment().onActivityResult(getTargetRequestCode(),Activity.RESULT_OK,config);
            dismiss();
        }else{
            //Si me llaman desde actividad...
            getActivity().setResult(Activity.RESULT_OK,config);
            getActivity().finish();
        }


    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(R.string.action_settings);
        return dialog;
    }
}
