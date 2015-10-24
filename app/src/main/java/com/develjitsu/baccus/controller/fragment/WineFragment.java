package com.develjitsu.baccus.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.develjitsu.baccus.R;
import com.develjitsu.baccus.controller.activity.SettingsActivity;
import com.develjitsu.baccus.controller.activity.WebActivity;
import com.develjitsu.baccus.model.Wine;

import java.io.IOException;

/**
 * Created by hadock on 12/10/15.
 * 
 */
public class WineFragment extends Fragment {

    public static final String ARG_WINE = "com.develjitsu.baccus.controller.activity.WineFragment.ARG_WINE";

    private static final String TAG = WineFragment.class.getSimpleName();
    private static final int SETTINGS_REQUEST = 1;
    private static final String STATE_IMAGE_SCALETYPE = "com.develjitsu.baccus.controller.activity.WineFragment.STATE_IMAGE_SCALETYPE";

    //Modelo
    private Wine mWine1 = null;

    //Vistas
    private ImageView mWineImage = null;
    private TextView mWineNameText = null;
    private TextView mWineTypeText = null;
    private TextView mWineOriginText = null;
    private RatingBar mWineRatingBar = null;
    private TextView mWineCompanyText = null;
    private TextView mWineNotesText = null;
    private ViewGroup mWineGrapesContainer = null;
    private ImageButton mGotoWebButton = null;

    private View root = null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        root = inflater.inflate(R.layout.fragment_wine,container,false);

        //Creamos modelo
        getModelFromTab();

        //Conexion V/C
        setWineModel(root);


        //Damos valor a la vista
        try {
            setWineModelValues(mWine1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Configuramos botones
        configButtons();

        if(savedInstanceState!=null && savedInstanceState.containsKey(STATE_IMAGE_SCALETYPE)){
            mWineImage.setScaleType((ImageView.ScaleType) savedInstanceState.getSerializable(STATE_IMAGE_SCALETYPE));
        }

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_winery, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

/*
            //Llamada desde activity , llamamos desde dialog con fragment
            Intent settingsIntent = new Intent(getActivity(),SettingsActivity.class);
            settingsIntent.putExtra(SettingsActivity.EXTRA_WINE_IMAGE_SCALE_TYPE, mWineImage.getScaleType());
            startActivityForResult(settingsIntent, SETTINGS_REQUEST);
*/

            SettingsFragment settingsFragment = new SettingsFragment();
            Bundle arguments = new Bundle();
            arguments.putSerializable(SettingsFragment.ARG_WINE_IMAGE_SCALE_TYPE,mWineImage.getScaleType());
            settingsFragment.setArguments(arguments);
            settingsFragment.setTargetFragment(this, SETTINGS_REQUEST);
            settingsFragment.show(getFragmentManager(),null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getModelFromTab(){
        mWine1 = (Wine) getArguments().getSerializable(ARG_WINE);
    }

    public void setWineModel(View root){
        //Conexion V/C
        mWineImage= (ImageView) root.findViewById(R.id.wine_image);
        mWineNameText = (TextView) root.findViewById(R.id.wine_name);
        mWineTypeText = (TextView) root.findViewById(R.id.wine_type);
        mWineOriginText = (TextView) root.findViewById(R.id.wine_origin);
        mWineCompanyText = (TextView) root.findViewById(R.id.wine_company);
        mWineNotesText = (TextView) root.findViewById(R.id.wine_notes);
        mWineRatingBar = (RatingBar) root.findViewById(R.id.wine_rating);
        mWineGrapesContainer = (ViewGroup) root.findViewById(R.id.grapes_container);
        mWineGrapesContainer = (ViewGroup) root.findViewById(R.id.grapes_container);
        mGotoWebButton = (ImageButton) root.findViewById(R.id.goto_web_button);
    }

    public void setWineModelValues(Wine wine) throws IOException {

        if(wine!=null){
            mWineNameText.setText(wine.getName());
            mWineTypeText.setText(wine.getType());
            mWineOriginText.setText(wine.getOrigin());
            mWineCompanyText.setText(wine.getCompanyName());
            mWineNotesText.setText(wine.getNotes());
            mWineRatingBar.setRating(wine.getRating());
            mWineImage.setImageBitmap(wine.getPhoto(getActivity()));

            for(int i=0;i<wine.getGrapeCount();i++){
                TextView grapeText = new TextView(getActivity());
                grapeText.setText(wine.getGrape(i));
                grapeText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                mWineGrapesContainer.addView(grapeText);
            }
        }
    }

    public void configButtons(){
        mGotoWebButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(getActivity(),WebActivity.class);
                webIntent.putExtra(WebActivity.EXTRA_WINE, mWine1);
                startActivity(webIntent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SETTINGS_REQUEST && resultCode== Activity.RESULT_OK){
            ImageView.ScaleType scaleType = (ImageView.ScaleType) data.getSerializableExtra(SettingsActivity.EXTRA_WINE_IMAGE_SCALE_TYPE);
            mWineImage.setScaleType(scaleType);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            if(mWineImage!=null){
                outState.putSerializable(STATE_IMAGE_SCALETYPE,mWineImage.getScaleType());
            }
        } catch (Exception e){
            Log.v(TAG,e.getMessage());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(PreferenceManager.getDefaultSharedPreferences(getActivity()).contains(SettingsFragment.PREF_IMAGE_SCALE_TYPE)){
            String scaleTypeString = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(SettingsFragment.PREF_IMAGE_SCALE_TYPE,null);
            ImageView.ScaleType scaleType = ImageView.ScaleType.valueOf(scaleTypeString);
            mWineImage.setScaleType(scaleType);
        }
    }
}
