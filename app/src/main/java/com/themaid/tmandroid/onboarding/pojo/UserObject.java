package com.themaid.tmandroid.onboarding.pojo;

import java.io.Serializable;

public class UserObject implements Serializable {

    private String UID;
    private String fullName;
    private String mobileNumber;
    private String userType;
    private String uriUserAadhar;
    private MaidServiceObject maidServiceObject;

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUriUserAadhar() {
        return uriUserAadhar;
    }

    public void setUriUserAadhar(String uriUserAadhar) {
        this.uriUserAadhar = uriUserAadhar;
    }

    public MaidServiceObject getMaidServiceObject() {
        return maidServiceObject;
    }

    public void setMaidServiceObject(MaidServiceObject maidServiceObject) {
        this.maidServiceObject = maidServiceObject;
    }

}
