/**
 * Created by hadock on 11/10/15.
 */
package com.develjitsu.baccus.model;

import com.develjitsu.baccus.R;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Wine implements Serializable {

    private String mName = null;
    private String nType = null;
    private int mPhoto = 0;
    private String mCompanyName = null;
    private String mCompanyWeb =null;
    private String mNotes = null;
    private String mOrigin = null;
    private int mRating = 0; // 0 to 5
    private List<String> mGrapes = new LinkedList<>();

    public Wine(String name, String nType, int photo, String companyName, String companyWeb, String notes, String origin, int rating) {
        mName = name;
        this.nType = nType;
        mPhoto = photo;
        mCompanyName = companyName;
        mCompanyWeb = companyWeb;
        mNotes = notes;
        mOrigin = origin;
        mRating = rating;
    }



    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getnType() {
        return nType;
    }

    public void setnType(String nType) {
        this.nType = nType;
    }

    public int getPhoto() {
        return mPhoto;
    }

    public void setPhoto(int photo) {
        mPhoto = photo;
    }

    public String getCompanyName() {
        return mCompanyName;
    }

    public void setCompanyName(String companyName) {
        mCompanyName = companyName;
    }

    public String getCompanyWeb() {
        return mCompanyWeb;
    }

    public void setCompanyWeb(String companyWeb) {
        mCompanyWeb = companyWeb;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

    public String getOrigin() {
        return mOrigin;
    }

    public void setOrigin(String origin) {
        mOrigin = origin;
    }

    public int getRating() {
        return mRating;
    }

    public void setRating(int rating) {
        mRating = rating;
    }

    public void addGrape(String grape){
        mGrapes.add(grape);
    }

    public int getGrapeCount(){
        return mGrapes.size();
    }

    public String getGrape(int index){
        return mGrapes.get(index);
    }

    public static Wine getDumyWine(int number){

        Wine dummyWine = null;

        if(number == 1){
            dummyWine = new Wine("Bembibre",
                    "Tinto",
                    R.drawable.bembibre,
                    "Dominio de Tares",
                    "http://www.dominiodetares.com/portfolio/benbibre/",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus nec tincidunt neque, vel viverra tortor. Sed vel metus pharetra, eleifend ligula sed, pellentesque risus. Ut pharetra porttitor tortor, sit amet consectetur orci semper et. Nam ac tincidunt augue. Curabitur aliquet interdum congue. Vivamus nec ullamcorper felis. Sed ultricies, ligula ac semper accumsan, nunc arcu porttitor nisi, eget maximus urna ipsum ac elit. Integer eleifend iaculis consequat.",
                    "Valdepeñas",5);
            dummyWine.addGrape("Mencia");
            dummyWine.addGrape("Cabernet");
        }else if(number==2){
            dummyWine = new Wine("Vegamar",
                    "Blanco",
                    R.drawable.vegamar,
                    "Marqués de Vegamar",
                    "http://www.dominiodetares.com/portfolio/cepas-viejas/",
                    "Praesent efficitur magna ex, sit amet dictum lacus aliquet in. Duis placerat rutrum efficitur. In hac habitasse platea dictumst. Donec quis bibendum neque, vel sodales neque. Sed dignissim egestas tellus, in vestibulum neque posuere volutpat. Vivamus pellentesque nibh vitae nulla vulputate placerat. Donec eget dui egestas, tempus justo non, varius elit. Nulla dapibus dolor ac fermentum ultricies. Curabitur sed mi euismod, interdum nisi at, hendrerit odio. Sed sagittis mi a sagittis lobortis. Aliquam fermentum arcu a facilisis posuere.",
                    "Ipsum Lorem",5);
            dummyWine.addGrape("Sirah");
            dummyWine.addGrape("Merlot");
        }else{
            dummyWine = new Wine("Bembibre",
                    "Tinto",
                    R.drawable.bembibre,
                    "Dominio de Tares",
                    "http://www.dominiodetares.com/portfolio/benbibre/",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus nec tincidunt neque, vel viverra tortor. Sed vel metus pharetra, eleifend ligula sed, pellentesque risus. Ut pharetra porttitor tortor, sit amet consectetur orci semper et. Nam ac tincidunt augue. Curabitur aliquet interdum congue. Vivamus nec ullamcorper felis. Sed ultricies, ligula ac semper accumsan, nunc arcu porttitor nisi, eget maximus urna ipsum ac elit. Integer eleifend iaculis consequat.",
                    "Valdepeñas",5);
            dummyWine.addGrape("Mencia");
            dummyWine.addGrape("Cabernet");
        }

        return dummyWine;
    }

}
