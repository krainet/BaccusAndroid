package com.develjitsu.baccus.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hadock on 12/10/15.
 *
 */
public class Winery implements Serializable {

    private static Winery sInstance = null;
    private List<Wine> mWines = null;


    public static Winery getInstance(){
        if(sInstance==null){
            sInstance = new Winery();
        }
        return sInstance;
    }


    public Winery() {
        Wine wine1 = Wine.getDumyWine(1);
        Wine wine2 = Wine.getDumyWine(2);
        Wine wine3 = Wine.getDumyWine(3);
        Wine wine4 = Wine.getDumyWine(4);

        mWines = Arrays.asList(new Wine[]{wine1,wine2,wine3,wine4});

    }

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
