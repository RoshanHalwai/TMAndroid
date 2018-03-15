package com.themaid.tmandroid.onboarding.pojo;

import java.io.Serializable;

public class MaidServiceObject implements Serializable {

    private boolean vegCooking;
    private boolean nonVegCooking;
    private boolean houseCleaning;
    private boolean utensilsWashing;
    private boolean clothesWashing;
    private boolean dusting;
    private boolean deepCleaning;

    public boolean isHouseCleaning() {
        return houseCleaning;
    }

    public void setHouseCleaning(boolean houseCleaning) {
        this.houseCleaning = houseCleaning;
    }

    public boolean isUtensilsWashing() {
        return utensilsWashing;
    }

    public void setUtensilsWashing(boolean utensilsWashing) {
        this.utensilsWashing = utensilsWashing;
    }

    public boolean isClothesWashing() {
        return clothesWashing;
    }

    public void setClothesWashing(boolean clothesWashing) {
        this.clothesWashing = clothesWashing;
    }

    public boolean isDusting() {
        return dusting;
    }

    public void setDusting(boolean dusting) {
        this.dusting = dusting;
    }

    public boolean isVegCooking() {
        return vegCooking;
    }

    public void setVegCooking(boolean vegCooking) {
        this.vegCooking = vegCooking;
    }

    public boolean isNonVegCooking() {
        return nonVegCooking;
    }

    public void setNonVegCooking(boolean nonVegCooking) {
        this.nonVegCooking = nonVegCooking;
    }

    public boolean isDeepCleaning() {
        return deepCleaning;
    }

    public void setDeepCleaning(boolean deepCleaning) {
        this.deepCleaning = deepCleaning;
    }

}
