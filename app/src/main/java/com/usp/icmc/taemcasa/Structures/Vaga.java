package com.usp.icmc.taemcasa.Structures;

/**
 * Created by Joao on 24/05/2017.
 */

import java.io.Serializable;

/**
 * Classe dummy que representa algumas informações de uma vaga/moradia.
 * Deve no futuro conter todas as informações necessárias.
 */
public class Vaga implements Serializable{
    private String title;
    private String description;
    private Endereco address;
    private String price;
    private String tipoMoradia;
    private boolean individual;
    private String tipoMorador;
    private int id_rep;
    private int id;

    public Vaga(String title, String description, Endereco address, String price, String type){
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


    public Vaga(int id, int id_rep, String price, String tipoMorador, boolean individual) {
        this.id = id;
        this.id_rep = id_rep;
        this.price = price;
        this.individual = individual;
        this.tipoMorador = tipoMorador;
    }

    public Vaga(String price){
        this.price = price;
    }

    public Vaga(){
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public Endereco getAddress(){
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(Endereco address) {
        this.address = address;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTipoMoradia(String tipoMoradia) {
        this.tipoMoradia = tipoMoradia;
    }

    public void setIndividual(boolean individual) {
        this.individual = individual;
    }

    public void setTipoMorador(String tipoMorador) {
        this.tipoMorador = tipoMorador;
    }

    public void setId_rep(int id_rep) {
        this.id_rep = id_rep;
    }
}