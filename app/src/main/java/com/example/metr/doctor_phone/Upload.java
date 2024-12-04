package com.example.metr.doctor_phone;

import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.Exclude;

import java.util.List;

public class Upload {

    private String mName;
    private String mImageUri;
    private String mImageUri_profile;
    private String text_describtion;
    private String text_title;
    private String currentDateTimeString;
    private String mKey;

     private List<CommentTemp> Comments;


    public Upload() {
     // empty constructer needed.
    }

    public Upload(String name, String imageUri){

        if (name.trim().equals("")){
            name="No Name";
        }

        mName=name;
        mImageUri=imageUri;
    }

    public Upload(String mName, String mImageUri, String text_title, String text_describtion,String currentDateTimeString,String mImageUri_profile,List<CommentTemp> Comments) {
        this.mName = mName;
        this.mImageUri = mImageUri;
        this.text_describtion = text_describtion;
        this.text_title = text_title;
        this.currentDateTimeString=currentDateTimeString;
        this.mImageUri_profile=mImageUri_profile;
        this.Comments=Comments;
       // this.mImageUri_profile=mImageUri_profile;
        Comments = null;
    }

    public Upload(String mName, String mImageUri, String mImageUri_profile, String text_describtion, String text_title, String currentDateTimeString, String mKey, List<CommentTemp> comments) {
        this.mName = mName;
        this.mImageUri = mImageUri;
        this.mImageUri_profile = mImageUri_profile;
        this.text_describtion = text_describtion;
        this.text_title = text_title;
        this.currentDateTimeString = currentDateTimeString;
        this.mKey = mKey;
        Comments = comments;
    }

    public String getName(){
        return mName;
    }

    public void setName(String name){
        mName=name;
    }

    public String getImageUri(){
        return mImageUri;
    }

    public void setImageUri(String ImageUri){
        mImageUri=ImageUri;
    }

    public String getText_describtion() {
        return text_describtion;
    }

    public void setText_describtion(String text_describtion) {
        this.text_describtion = text_describtion;
    }

    public String getText_title() {
        return text_title;
    }

    public void setText_title(String text_title) {
        this.text_title = text_title;
    }

    public String getmImageUri_profile() {
        return mImageUri_profile;
    }

    public void setmImageUri_profile(String mImageUri_profile) {
        this.mImageUri_profile = mImageUri_profile;
    }

    public String getCurrentDateTimeString() {
        return currentDateTimeString;
    }

    public void setCurrentDateTimeString(String currentDateTimeString) {
        this.currentDateTimeString = currentDateTimeString;
    }

    public List<CommentTemp> getComments() {
        return Comments;
    }

    public void setComments(List<CommentTemp> comments) {
        Comments = comments;
    }

    @Exclude
    public String getmKey() {
        return mKey;
    }

    @Exclude
    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

}
