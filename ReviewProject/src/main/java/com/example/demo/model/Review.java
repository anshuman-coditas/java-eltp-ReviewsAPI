package com.example.demo.model;

import javax.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(targetEntity = User.class,cascade = CascadeType.ALL)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private float ambience;
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

    public Review(){}
    public Review(Long id, User user, float ambience, float clean, float food, float drinks, float service) {
        this.id = id;
        this.user = user;
        this.ambience = ambience;
        this.clean = clean;
        this.food = food;
        this.drinks = drinks;
        this.service = service;
    }
}
