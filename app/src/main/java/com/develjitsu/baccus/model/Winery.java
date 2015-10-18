package com.develjitsu.baccus.model;

import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import com.develjitsu.baccus.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hadock on 12/10/15.
 *
 */
public class Winery implements Serializable {

    private static Winery sInstance = null;
    private List<Wine> mWines = null;
    private static final String winesURL="http://golang.bz/baccus/wines.json";


    public static Winery getInstance(){
        if(sInstance==null){
            try {
                //Dejamos que se bloquee el hilo principal.
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                sInstance = downloadWines();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return sInstance;
    }

    private static Winery downloadWines() throws IOException, JSONException {
        Winery winery = new Winery();
        winery.mWines = new LinkedList<>();

        //GET URL JSON
        URLConnection conn = new URL(winesURL).openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line = null;

        while((line=reader.readLine())!=null){
            response.append(line);
        }

        reader.close();

        JSONArray wines = new JSONArray(response.toString());
        for(int wineIndex=0;wineIndex<wines.length();wineIndex++){
            String name = null;
            String type=null;
            String company = null;
            String companyWeb=null;
            String notes=null;
            int rating=0;
            String origin = null;

            JSONObject jsonWine = wines.getJSONObject(wineIndex);
            if(jsonWine.has("name")){
                name=jsonWine.getString("name");
                type=jsonWine.getString("type");
                company=jsonWine.getString("company");
                companyWeb=jsonWine.getString("company_web");
                notes=jsonWine.getString("notes");
                rating=jsonWine.getInt("rating");
                origin=jsonWine.getString("origin");
                Wine wine = new Wine(name,type, R.drawable.zarate,company,companyWeb,notes,origin,rating);
                JSONArray jsonGrapes = jsonWine.getJSONArray("grapes");
                for(int grapeIndex=0;grapeIndex<jsonGrapes.length();grapeIndex++){
                    wine.addGrape(jsonGrapes.getJSONObject(grapeIndex).getString("grape"));
                }
                winery.mWines.add(wine);
            }
        }
        return winery;
    }

/*
    public Winery() {

        Wine wine1 = Wine.getDumyWine(1);
        Wine wine2 = Wine.getDumyWine(2);
        Wine wine3 = Wine.getDumyWine(3);
        Wine wine4 = Wine.getDumyWine(4);


        mWines = Arrays.asList(new Wine[]{wine1,wine2,wine3,wine4});

    }
*/

    public Wine getWine(int index){
        return mWines.get(index);
    }

    public int getWineCount(){
        return mWines.size();
    }

    public List<Wine> getWineList() {
        return mWines;
    }

}
