package com.themaid.tmandroid;

import android.content.Context;
import android.graphics.Typeface;

public class Constants {

    /* Firebase Child Constants */
    public static final String FIREBASE_CHILD_USERS = "users";
    public static final String FIREBASE_CHILD_ALL = "all";
    public static final String FIREBASE_CHILD_CUSTOMERS = "customers";
    public static final String FIREBASE_CHILD_MAIDS = "maids";
    public static final String FIREBASE_CHILD_PRIVATE = "private";
    public static final String FIREBASE_CHILD_AADHAR_CARD = "aadharCard";
    public static final String FIREBASE_CHILD_FULL_NAME = "fullName";
    public static final String FIREBASE_CHILD_MOBILE_NUMBER = "mobileNumber";
    public static final String FIREBASE_CHILD_SERVICES = "services";
    public static final String FIREBASE_CHILD_USER_TYPE = "userType";

    /* Intent Keys */
    public static final String USER_OBJECT_INTENT_KEY = "UserObject";

    /* User Types */
    public static final String USER_TYPE_CUSTOMER = "Customer";
    public static final String USER_TYPE_MAID = "Maid";

    /* Font Types */
    public static Typeface setLatoLightFont(Context c) {
        return Typeface.createFromAsset(c.getAssets(), "fonts/Lato-Light.ttf");
    }

    public static Typeface setLatoLightItalicFont(Context c) {
        return Typeface.createFromAsset(c.getAssets(), "fonts/Lato-LightItalic.ttf");
    }

}
