package com.example.joao.taemcasa.Structures;

/**
 * Created by Joao on 24/05/2017.
 */

public class Vaga {
    private String title;
    private String description;
    private String address;
    private String price;
    private String type;

    public Vaga(String title, String description, String address, String price, String type){
        this.title = title;
        this.description = description;
        this.address = address;
        this.price = price;
        this.type = type;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getAddress(){
        return address;
    }

    public String getPrice(){
        return price;
    }

    public String getType(){
        return type;
    }
}