package com.usp.icmc.taemcasa.Estruturas;

/**
 * Created by fabio on 11/06/17.
 */

public class Moradia {

    int id_rep;
    String username;
    String nome;
    String descricao;
    Endereco endereco;
    float latitude;
    float longitude;
    String telefone;
    String link;
    int tipo;
    int perfil;
    int qtd_moradores;
    boolean aceita_animais;

    // Construtor teste, para exibir os dados em ListViews
    public Moradia(String nome, String descricao, String rua, String numero, String complemento, String bairro, String cidade, String estado){
        this.nome = nome;
        this.descricao = descricao;
        this.endereco = new Endereco(rua, numero, complemento, bairro, cidade, estado);
    }

    // Construtor completo para exibir todos os dados
    public Moradia(int id, String username, String nome, String descricao, String rua, String numero, String complemento, String bairro, String cidade, String estado, double latitude, double longitude, String telefone, String link, int tipo, int perfil, int qtd_moradores, boolean aceita_animais) {
        this.id_rep = id;
        this.endereco = new Endereco(rua, numero, complemento, bairro, cidade, estado);
        this.username = username;
        this.nome = nome;
        this.descricao = descricao;
        this.latitude = (float) latitude;
        this.longitude = (float) longitude;
        this.telefone = telefone;
        this.link = link;
        this.tipo = tipo;
        this.perfil = perfil;
        this.qtd_moradores = qtd_moradores;
        this.aceita_animais = aceita_animais;
    }

    public int getId(){
        return id_rep;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Endereco getEndereco(){
        return endereco;
    }

    public int getQtd_moradores() {
        return qtd_moradores;
    }
}
