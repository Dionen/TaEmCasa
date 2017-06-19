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
    private float preco;
    private boolean individual;
    private int tipo;    // Masculina ou feminina
    private int id_vaga;
    private int id_rep;
    private Republica republica;

    public Vaga(String preco, boolean individual, String tipo){
        this.preco = Float.parseFloat(preco);
        this.individual = individual;
        this.tipo = Integer.parseInt(tipo);
    }

    public Vaga(int id, int id_rep, String price, String tipoMorador, boolean individual) {
        this.id_vaga = id;
        this.id_rep = id_rep;
        this.preco = Float.parseFloat(price);
        this.individual = individual;
        this.tipo = Integer.parseInt(tipoMorador);
    }

    public Vaga(){}

    public Vaga(String preco){
        this.preco = Float.parseFloat(preco);
    }


    public float getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = Float.parseFloat(preco);
    }

    public boolean isIndividual() {
        return individual;
    }

    public void setIndividual(String individual) {
        if (Integer.parseInt(individual) == 1) this.individual = true;
        else this.individual = false;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = Integer.parseInt(tipo);
    }

    public Republica getRepublica() {
        return republica;
    }

    public void setRepublica(Republica republica) {
        this.republica = republica;
    }

    public int getId_vaga() {
        return id_vaga;
    }

    public void setId_vaga(String id_vaga) {
        this.id_vaga = Integer.parseInt(id_vaga);
    }
}