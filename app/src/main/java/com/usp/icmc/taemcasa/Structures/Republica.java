package com.usp.icmc.taemcasa.Structures;

/**
 * Created by fabio on 11/06/17.
 */

public class Republica {

    int id_rep;
    String username;
    String nome;
    String descricao;
    String rua;
    String numero;
    String complemento;
    String bairro;
    String cidade;
    String estado;
    float latitude;
    float longitude;
    String telefone;
    String link;
    int tipo;
    int perfil;
    int qtd_moradores;
    boolean aceita_animais;

    public Republica(int id, String username, String nome, String descricao, String rua, String numero, String complemento, String bairro, String cidade, String estado, float latitude, float longitude, String telefone, String link, int tipo, int perfil, int qtd_moradores, boolean aceita_animais) {
        this.id_rep = id;
        this.username = username;
        this.nome = nome;
        this.descricao = descricao;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.latitude = latitude;
        this.longitude = longitude;
        this.telefone = telefone;
        this.link = link;
        this.tipo = tipo;
        this.perfil = perfil;
        this.qtd_moradores = qtd_moradores;
        this.aceita_animais = aceita_animais;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getQtd_moradores() {
        return qtd_moradores;
    }
}
