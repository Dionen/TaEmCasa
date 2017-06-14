package com.usp.icmc.taemcasa.Structures;

/**
 * Created by Joao on 24/05/2017.
 */

/**
 * Classe dummy que representa algumas informações de uma vaga/moradia.
 * Deve no futuro conter todas as informações necessárias.
 */
public class Vaga {
    private String title;
    private String description;
    private String address;
    private String price;
    private String tipoMoradia;
    private boolean individual;
    private String tipoMorador;

    public Vaga(String title, String description, String address, String price, String type){
        this.title = title;
        this.description = description;
        this.address = address;
        this.price = price;
        this.tipoMoradia = type;
    }

    public Vaga(String price, boolean individual, String tipoMorador){
        this.price = price;
        this.individual = individual;
        this.tipoMorador = tipoMorador;
    }

    public Vaga(String price){
        this.price = price;
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

    public String getTipoMoradia(){
        return tipoMoradia;
    }

    public boolean getIndividual(){
        return individual;
    }

    public String gettipoMorador(){
        return tipoMorador;
    }
}