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
    private Republica republica;

    public Vaga(String preco, boolean individual, String tipo){
        this.preco = Float.parseFloat(preco);
        this.individual = individual;
        this.tipo = Integer.parseInt(tipo);
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
}