package com.usp.icmc.taemcasa.Structures;

/**
 * Created by fabio on 11/06/17.
 */

public class Republica {

    int id_rep;
    String username;
    String nome;
    String descricao;
    Endereco endereco;
    float latitude;
    float longitude;
    String telefone;
    String imagem;
    int tipo;
    int perfil;
    int qtd_moradores;
    boolean aceita_animais;

    public Republica(int id, String username, String nome, String descricao, String rua, String numero, String complemento, String bairro, String cidade, String estado, float latitude, float longitude, String telefone, String imagem, int tipo, int perfil, int qtd_moradores, boolean aceita_animais) {
        this.id_rep = id;
        this.username = username;
        this.nome = nome;
        this.descricao = descricao;
        this.endereco = new Endereco(rua, numero, complemento, bairro, cidade, estado);
        this.latitude = latitude;
        this.longitude = longitude;
        this.telefone = telefone;
        this.imagem = imagem;
        this.tipo = tipo;
        this.perfil = perfil;
        this.qtd_moradores = qtd_moradores;
        this.aceita_animais = aceita_animais;
    }

    public int getId_rep() {
        return id_rep;
    }

    public void setId_rep(int id_rep) {
        this.id_rep = id_rep;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getPerfil() {
        return perfil;
    }

    public void setPerfil(int perfil) {
        this.perfil = perfil;
    }

    public void setQtd_moradores(int qtd_moradores) {
        this.qtd_moradores = qtd_moradores;
    }

    public boolean isAceita_animais() {
        return aceita_animais;
    }

    public void setAceita_animais(boolean aceita_animais) {
        this.aceita_animais = aceita_animais;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Endereco getEndereco() { return endereco; }

    public int getQtd_moradores() {
        return qtd_moradores;
    }

    public String getImagem() {return imagem; }

    public int getId() {
        return id_rep;
    }
}
