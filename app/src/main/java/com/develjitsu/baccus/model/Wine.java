/**
 * Created by hadock on 11/10/15.
 *
 */
package com.develjitsu.baccus.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.develjitsu.baccus.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class Wine implements Serializable {

    private String mId = null;
    private String mName = null;
    private String mType = null;
    private Bitmap mPhoto = null;
    private String mPhotoURL = null;
    private String mCompanyName = null;
    private String mCompanyWeb =null;
    private String mNotes = null;
    private String mOrigin = null;
    private int mRating = 0; // 0 to 5
    private List<String> mGrapes = new LinkedList<>();

    public Wine(String id, String name, String nType, String photoURL, String companyName, String companyWeb, String notes, String origin, int rating) {
        mId=id;
        mName = name;
        this.mType = nType;
        mPhotoURL = photoURL;
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

    public String getType() {
        return mType;
    }

    public void setType(String nType) {
        this.mType = nType;
    }

    public Bitmap getPhoto(Context context) throws IOException {
        if(mPhoto==null){
            mPhoto=getBitmapFromURL(getPhotoURL(),context);
        }
        return mPhoto;
    }

    public void setPhoto(Bitmap photo) {
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

    @Override
    public String toString() {
        return getName();
    }

    public String getPhotoURL() {
        return mPhotoURL;
    }

    public void setPhotoURL(String photoURL) {
        mPhotoURL = photoURL;
    }

    private Bitmap getBitmapFromURL(String photoURL,Context context) {
        File imageFile = new File(context.getCacheDir(),getId());
        if(imageFile.exists()){
            return BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        }

        InputStream in = null;
        try {
            in=new URL(photoURL).openStream();
            Bitmap img = BitmapFactory.decodeStream(in);

            //guardamos en cache
            FileOutputStream fos = new FileOutputStream(imageFile);
            img.compress(Bitmap.CompressFormat.PNG,90,fos);

            return img;
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error downloading image", e);
            return null;
        }finally {
            try{
                if(in!=null){
                    in.close();
                }
            }catch (Exception e){

            }
        }
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }
}
