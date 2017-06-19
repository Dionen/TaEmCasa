package com.usp.icmc.taemcasa.Structures;

import java.io.Serializable;

/**
 * Created by Juliana on 07/06/2017.
 */

public class Endereco implements Serializable{
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;

    public Endereco(String rua, String numero, String complemento, String bairro, String cidade, String estado) {
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public Endereco(){}

    public String enderecoLongo(){ return rua + ", " + numero + ", " + bairro + ". " + cidade + " - " + estado; }

    public String enderecoCurto(){
        return bairro + ", " + cidade + " - " + estado;
    }

    public String getRua() {
        return rua;
    }

    public String getBairro() { return bairro; }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setBairro(String bairro) { this.bairro = bairro; }
}