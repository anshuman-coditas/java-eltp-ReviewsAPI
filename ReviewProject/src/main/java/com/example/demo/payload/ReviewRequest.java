package com.example.demo.payload;

public class ReviewRequest {
    private float ambience;
    private String uname;
    private float clean;
    private float food;
    private float drinks;
    private float service;

    public float getAmbience() {
        return ambience;
    }

    public void setAmbience(float ambience) {
        this.ambience = ambience;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public float getClean() {
        return clean;
    }

    public void setClean(float clean) {
        this.clean = clean;
    }

    public float getFood() {
        return food;
    }

    public void setFood(float food) {
        this.food = food;
    }

    public float getDrinks() {
        return drinks;
    }

    public void setDrinks(float drinks) {
        this.drinks = drinks;
    }

    public float getService() {
        return service;
    }

    public void setService(float service) {
        this.service = service;
    }
}
