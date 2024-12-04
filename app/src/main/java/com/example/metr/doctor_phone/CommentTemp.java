package com.example.metr.doctor_phone;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.Exclude;

public class CommentTemp   {
    private String Name_text;
    private String Date_text;
    private String description_text;
    private String Image_uri;
    private Button  btn_Accept;
    private String mKey;
    private boolean exist;

    public CommentTemp() {
    }

    public CommentTemp(String name_text, String date_text, String description_text, String image_uri,boolean exist) {
        Name_text = name_text;
        Date_text = date_text;
        this.description_text = description_text;
        Image_uri = image_uri;

        //this.btn_Accept = btn_Accept;
        this.exist=exist;
       /* if(exist==true){
            this.btn_Accept = btn_Accept;
        }else{
            this.btn_Accept = null;
        }*/
    }

    @Exclude
    public String getmKey() {
        return mKey;
    }

    @Exclude
    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

    public String getName_text() {
        return Name_text;
    }

    public void setName_text(String name_text) {
        Name_text = name_text;
    }

    public String getDate_text() {
        return Date_text;
    }

    public void setDate_text(String date_text) {
        Date_text = date_text;
    }

    public String getDescription_text() {
        return description_text;
    }

    public void setDescription_text(String description_text) {
        this.description_text = description_text;
    }

    public String getImage_uri() {
        return Image_uri;
    }

    public void setImage_uri(String image_uri) {
        Image_uri = image_uri;
    }

    @Exclude
    public Button getBtn_Accept() {
        return btn_Accept;
    }

    @Exclude
    public void setBtn_Accept(Button btn_Accept) {
        this.btn_Accept = btn_Accept;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }
}
