package com.usp.icmc.taemcasa.Structures;

/**
 * Created by fabio on 11/06/17.
 */

public class Republica {

    private int id_rep;
    private String username;
    private String nome;
    private String descricao;
    private Endereco endereco;
    private float latitude;
    private float longitude;
    private String telefone;
    private String imagem;
    private int tipo;   // República ou apartamento
    private int perfil;
    private int qtd_moradores;
    private boolean aceita_animais;

    public Republica(int id, String username, String nome, String descricao, String rua, String numero, String complemento, String bairro, String cidade, String estado, float latitude, float longitude, String telefone, String link, int tipo, int perfil, int qtd_moradores, boolean aceita_animais) {
        this.id_rep = id;
        this.username = username;
        this.nome = nome;
        this.descricao = descricao;
        this.endereco = new Endereco(rua, numero, complemento, bairro, cidade, estado);
        this.latitude = latitude;
        this.longitude = longitude;
        this.telefone = telefone;
        this.imagem = link;
        this.tipo = tipo;
        this.perfil = perfil;
        this.qtd_moradores = qtd_moradores;
        this.aceita_animais = aceita_animais;
    }

    public Republica(){}

    public int getId_rep() {
        return id_rep;
    }

    public void setId_rep(String id_rep) {
        this.id_rep = Integer.parseInt(id_rep);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Endereco getEndereco() {
        return endereco;
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

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = Integer.parseInt(tipo);
    }

    public int getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = Integer.parseInt(perfil);
    }

    public int getQtd_moradores() {
        return qtd_moradores;
    }

    public void setQtd_moradores(String qtd_moradores) {
        this.qtd_moradores = Integer.parseInt(qtd_moradores);
    }

    public boolean isAceita_animais() {
        return aceita_animais;
    }

    public void setAceita_animais(String aceita_animais) {
        if (Integer.parseInt(aceita_animais) == 1) this.aceita_animais = true;
        else this.aceita_animais = false;
    }
}
