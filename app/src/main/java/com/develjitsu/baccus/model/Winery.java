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
import java.util.HashMap;
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

    public static enum WineType {
        RED,WHITE,ROSE,OTHER
    }

    private HashMap<WineType,List<Wine>> mWinesByType = null;


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

    public static boolean isInstanceAviable(){
        return sInstance!=null;
    }

    private static Winery downloadWines() throws IOException, JSONException {
        Winery winery = new Winery();
        winery.mWines = new LinkedList<>();
        winery.mWinesByType = new HashMap<>();
        for(WineType type : WineType.values()){
            winery.mWinesByType.put(type,new LinkedList<Wine>());
        }


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
            String id = null;
            String name = null;
            String type=null;
            String company = null;
            String companyWeb=null;
            String notes=null;
            String picture=null;
            int rating=0;
            String origin = null;

            JSONObject jsonWine = wines.getJSONObject(wineIndex);
            if(jsonWine.has("name")){
                id=jsonWine.getString("_id");
                name=jsonWine.getString("name");
                type=jsonWine.getString("type");
                company=jsonWine.getString("company");
                companyWeb=jsonWine.getString("company_web");
                notes=jsonWine.getString("notes");
                rating=jsonWine.getInt("rating");
                origin=jsonWine.getString("origin");
                picture=jsonWine.getString("picture");
                //picture="http://sr1.wine-searcher.net/images/labels/15/35/finca-coronado-vino-de-la-tierra-castilla-la-mancha-spain-10361535.jpg";
                Wine wine = new Wine(id,name,type, picture,company,companyWeb,notes,origin,rating);
                JSONArray jsonGrapes = jsonWine.getJSONArray("grapes");
                for(int grapeIndex=0;grapeIndex<jsonGrapes.length();grapeIndex++){
                    wine.addGrape(jsonGrapes.getJSONObject(grapeIndex).getString("grape"));
                }

                //cambiamos esto por hashMap winery.mWines.add(wine);

                if(type.equalsIgnoreCase("tinto")){
                    winery.mWinesByType.get(WineType.RED).add(wine);
                }else if(type.equalsIgnoreCase("blanco")){
                    winery.mWinesByType.get(WineType.WHITE).add(wine);
                }else if(type.equalsIgnoreCase("rosado")){
                    winery.mWinesByType.get(WineType.ROSE).add(wine);
                }else{
                    winery.mWinesByType.get(WineType.OTHER).add(wine);
                }
            }
        }

        for(WineType type : WineType.values()){
            List<Wine> wineList = winery.mWinesByType.get(type);
            winery.mWines.addAll(wineList);
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
    public Wine getWine(WineType type,int index){
        return mWinesByType.get(type).get(index);
    }

    public int getWineCount(){
        return mWines.size();
    }

    public int getWineCount(WineType type){
        return mWinesByType.get(type).size();
    }

    public List<Wine> getWineList() {
        return mWines;
    }

    public int getAbsolutePosition(WineType type,int relativePosition){
        Wine wine = getWine(type,relativePosition);
        return mWines.indexOf(wine);
    }




}
