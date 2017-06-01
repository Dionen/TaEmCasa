package com.example.joao.taemcasa.Perfil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joao.taemcasa.R;

public class PerfilVaga extends AppCompatActivity {
    private int autoriaVaga; // 1 = do usuário, 2 = do sistema

    // vaga
    private int idVaga;
    private String nomeVaga;
    private int qtdVaga;
    private String tipoVaga;
    private int qtdMoradores;
    private double precoVaga;
    private String descVaga;
    private String telefone;

    // endereço
    private int idEndereco;
    private String ruaEndereco;
    private int numEndereco;
    private String complEndereco;
    private String bairroEndereco;
    private String cidadeEndereco;
    private String latitudeEndereco;
    private String longitudeEndereco;

    public TextView nomeVagaTV;
    public TextView tipoVagaTV;
    public TextView telefoneTV;
    public TextView qtdVagaTV;
    public TextView precoVagaTV;
    public TextView descTV;
    public TextView enderecoTV;
    public TextView bairroTV;
    public TextView cidadeTV;
    public TextView coordenadasTV;
    public TextView qtdMoradoresTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_vaga);
/*
        nomeVagaTV = (TextView) findViewById(R.id.nomeVaga);
        nomeVagaTV.setText(nomeVaga);

        tipoVagaTV = (TextView) findViewById(R.id.tipoVaga);
        tipoVagaTV.setText(tipoVaga);

        telefoneTV = (TextView) findViewById(R.id.telefone);
        telefoneTV.setText(telefone);

        qtdVagaTV = (TextView) findViewById(R.id.qtdVagas);
        qtdVagaTV.setText(getString(R.string.qtdvagas, qtdVaga));

        precoVagaTV = (TextView) findViewById(R.id.precoVaga);
        precoVagaTV.setText(getString(R.string.precovaga, precoVaga));

        qtdMoradoresTV = (TextView) findViewById(R.id.numMoradores);
        qtdMoradoresTV.setText(getString(R.string.qtdmoradores, qtdMoradores));

        descTV = (TextView) findViewById(R.id.descText);
        descTV.setText(descVaga);

        enderecoTV = (TextView) findViewById(R.id.enderecoText);
        enderecoTV.setText(getString(R.string.enderecotext, ruaEndereco, numEndereco, complEndereco));

        bairroTV = (TextView) findViewById(R.id.bairroText);
        bairroTV.setText(bairroEndereco);

        cidadeTV = (TextView) findViewById(R.id.cidadeText);
        cidadeTV.setText(cidadeEndereco);

        coordenadasTV = (TextView) findViewById(R.id.coordenadasText);
        coordenadasTV.setText(getString(R.string.coordtext, latitudeEndereco, longitudeEndereco)); */
    }
}
