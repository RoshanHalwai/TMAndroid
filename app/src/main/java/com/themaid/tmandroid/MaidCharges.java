package com.themaid.tmandroid;

public class MaidCharges {

    private int clothesWashing;
    private int deepCleaning;
    private int dusting;
    private int houseCleaning;
    private int nonVegCooking;
    private int utensilsWashing;
    private int vegCooking;

    public MaidCharges() {
    }

    public MaidCharges(int clothesWashing, int deepCleaning, int dusting, int houseCleaning, int nonVegCooking, int utensilsWashing, int vegCooking) {
        this.clothesWashing = clothesWashing;
        this.deepCleaning = deepCleaning;
        this.dusting = dusting;
        this.houseCleaning = houseCleaning;
        this.nonVegCooking = nonVegCooking;
        this.utensilsWashing = utensilsWashing;
        this.vegCooking = vegCooking;
    }

    public int getClothesWashing() {
        return clothesWashing;
    }

    public int getDeepCleaning() {
        return deepCleaning;
    }

    public int getDusting() {
        return dusting;
    }

    public int getHouseCleaning() {
        return houseCleaning;
    }

    public int getNonVegCooking() {
        return nonVegCooking;
    }

    public int getUtensilsWashing() {
        return utensilsWashing;
    }

    public int getVegCooking() {
        return vegCooking;
    }

}
