package com.develjitsu.baccus.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.develjitsu.baccus.R;
import com.develjitsu.baccus.model.Wine;

import static android.widget.ImageView.*;

public class WineActivity extends AppCompatActivity {

    private static final String TAG = WineActivity.class.getSimpleName();
    private static final int SETTINGS_REQUEST = 1;
    private static final String STATE_IMAGE_SCALETYPE = "com.develjitsu.baccus.controller.WineActivity.STATE_IMAGE_SCALETYPE";
    private static final String EXTRA_WINE = "com.develjitsu.baccus.controller.WineActivity.EXTRA_WINE";



    //Modelo
    private Wine mWine1 = null;
    private Wine mWine2 = null;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //Creamos modelo
        createDumyModels();

        //Conexion V/C
        setWineModel();

        //Damos valor a la vista
        setWineModelValues(mWine1);

        //Configuramos botones
        configButtons();

        if(savedInstanceState!=null && savedInstanceState.containsKey(STATE_IMAGE_SCALETYPE)){
            mWineImage.setScaleType((ScaleType) savedInstanceState.getSerializable(STATE_IMAGE_SCALETYPE));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Call to action Button", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this,SettingsActivity.class);
            settingsIntent.putExtra(SettingsActivity.EXTRA_WINE_IMAGE_SCALE_TYPE,mWineImage.getScaleType());
            startActivityForResult(settingsIntent,SETTINGS_REQUEST);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createDumyModels(){
        mWine1 = new Wine("Bembibre",
                "Tinto",
                R.drawable.bembibre,
                "Dominio de Tares",
                "http://www.dominiodetares.com/portfolio/benbibre/",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus nec tincidunt neque, vel viverra tortor. Sed vel metus pharetra, eleifend ligula sed, pellentesque risus. Ut pharetra porttitor tortor, sit amet consectetur orci semper et. Nam ac tincidunt augue. Curabitur aliquet interdum congue. Vivamus nec ullamcorper felis. Sed ultricies, ligula ac semper accumsan, nunc arcu porttitor nisi, eget maximus urna ipsum ac elit. Integer eleifend iaculis consequat.",
                "Valdepeñas",5);
        mWine1.addGrape("Mencia");
        mWine1.addGrape("Cabernet");

        mWine2 = new Wine("Vegamar",
                "Blanco",
                R.drawable.vegamar,
                "Marqués de Vegamar",
                "http://www.dominiodetares.com/portfolio/cepas-viejas/",
                "Praesent efficitur magna ex, sit amet dictum lacus aliquet in. Duis placerat rutrum efficitur. In hac habitasse platea dictumst. Donec quis bibendum neque, vel sodales neque. Sed dignissim egestas tellus, in vestibulum neque posuere volutpat. Vivamus pellentesque nibh vitae nulla vulputate placerat. Donec eget dui egestas, tempus justo non, varius elit. Nulla dapibus dolor ac fermentum ultricies. Curabitur sed mi euismod, interdum nisi at, hendrerit odio. Sed sagittis mi a sagittis lobortis. Aliquam fermentum arcu a facilisis posuere.",
                "Ipsum Lorem",5);
        mWine2.addGrape("Sirah");
        mWine2.addGrape("Merlot");

    }

    public void setWineModel(){
        //Conexion V/C
        mWineImage= (ImageView) findViewById(R.id.wine_image);
        mWineNameText = (TextView) findViewById(R.id.wine_name);
        mWineTypeText = (TextView) findViewById(R.id.wine_type);
        mWineOriginText = (TextView) findViewById(R.id.wine_origin);
        mWineCompanyText = (TextView) findViewById(R.id.wine_company);
        mWineNotesText = (TextView) findViewById(R.id.wine_notes);
        mWineRatingBar = (RatingBar) findViewById(R.id.wine_rating);
        mWineGrapesContainer = (ViewGroup) findViewById(R.id.grapes_container);
        mWineGrapesContainer = (ViewGroup) findViewById(R.id.grapes_container);
        mGotoWebButton = (ImageButton) findViewById(R.id.goto_web_button);
    }

    public void setWineModelValues(Wine wine){
        mWineNameText.setText(wine.getName());
        mWineTypeText.setText(wine.getnType());
        mWineOriginText.setText(wine.getOrigin());
        mWineCompanyText.setText(wine.getCompanyName());
        mWineNotesText.setText(wine.getNotes());
        mWineRatingBar.setRating(wine.getRating());

        for(int i=0;i<wine.getGrapeCount();i++){
            TextView grapeText = new TextView(this);
            grapeText.setText(wine.getGrape(i));
            grapeText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            mWineGrapesContainer.addView(grapeText);
        }

    }

    public void configButtons(){
        mGotoWebButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(WineActivity.this,WebActivity.class);
                webIntent.putExtra(WebActivity.EXTRA_WINE, Wine.getDumyWine(1));
                startActivity(webIntent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SETTINGS_REQUEST && resultCode==RESULT_OK){
            ImageView.ScaleType scaleType = (ScaleType) data.getSerializableExtra(SettingsActivity.EXTRA_WINE_IMAGE_SCALE_TYPE);
            mWineImage.setScaleType(scaleType);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STATE_IMAGE_SCALETYPE,mWineImage.getScaleType());
    }
}
