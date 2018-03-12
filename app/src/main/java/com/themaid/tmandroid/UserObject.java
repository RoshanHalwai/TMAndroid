package com.themaid.tmandroid;

import java.io.Serializable;

class UserObject implements Serializable {

    private String UID;
    private String fullName;
    private String mobileNumber;
    private String userType;
    private String uriUserAadhar;

    String getUID() {
        return UID;
    }

    void setUID(String UID) {
        this.UID = UID;
    }

    String getFullName() {
        return fullName;
    }

    void setFullName(String fullName) {
        this.fullName = fullName;
    }

    String getMobileNumber() {
        return mobileNumber;
    }

    void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    String getUserType() {
        return userType;
    }

    void setUserType(String userType) {
        this.userType = userType;
    }

    String getUriUserAadhar() {
        return uriUserAadhar;
    }

    void setUriUserAadhar(String uriUserAadhar) {
        this.uriUserAadhar = uriUserAadhar;
    }

}
