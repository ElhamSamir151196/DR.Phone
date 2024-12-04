package com.example.metr.doctor_phone;

import com.google.firebase.database.Exclude;

public class UserTemp  {
    private String mName;
    private String mImageUri;
    private String mPassword;
    private String mAddress;
    private String mE_mail;
    private String mCountry;
    private String mGender;
    private String mTypeUser;
    private int mday;
    private int mMonth;
    private int mYear;
    private String mPhoneNumber;
    private String mKey;



    public UserTemp() {
        // empty constructer needed.
    }

    public UserTemp(String mName, String mImageUri, String mPassword, String mAddress, String mE_mail, String mCountry,
                    String mGender, String mTypeUser, int mday, int mMonth, int mYear, String mPhoneNumber) {
        this.mName = mName;
        this.mImageUri = mImageUri;
        this.mPassword = mPassword;
        this.mAddress = mAddress;
        this.mE_mail = mE_mail;
        this.mCountry = mCountry;
        this.mGender = mGender;
        this.mTypeUser = mTypeUser;
        this.mday = mday;
        this.mMonth = mMonth;
        this.mYear = mYear;
        this.mPhoneNumber = mPhoneNumber;
    }


    @Exclude
    public String getmKey() {
        return mKey;
    }

    @Exclude
    public void setmKey(String mKey) {
        this.mKey = mKey;
    }


    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageUri() {
        return mImageUri;
    }

    public void setmImageUri(String mImageUri) {
        this.mImageUri = mImageUri;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmE_mail() {
        return mE_mail;
    }

    public void setmE_mail(String mE_mail) {
        this.mE_mail = mE_mail;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getmGender() {
        return mGender;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public String getmTypeUser() {
        return mTypeUser;
    }

    public void setmTypeUser(String mTypeUser) {
        this.mTypeUser = mTypeUser;
    }

    public int getMday() {
        return mday;
    }

    public void setMday(int mday) {
        this.mday = mday;
    }

    public int getmMonth() {
        return mMonth;
    }

    public void setmMonth(int mMonth) {
        this.mMonth = mMonth;
    }

    public int getmYear() {
        return mYear;
    }

    public void setmYear(int mYear) {
        this.mYear = mYear;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }
}
