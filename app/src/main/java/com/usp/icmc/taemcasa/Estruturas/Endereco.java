package com.usp.icmc.taemcasa.Estruturas;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * Created by Juliana on 07/06/2017.
 */

public class Endereco {
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private LatLng latLng;

    public Endereco(String rua, String numero, String complemento, String bairro, String cidade, String estado) {
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        latLng = null;
    }

    /* Gera as cordenadas para dado endereço.
     * faz certo se o endereço existir, mas trava se não existe.
     */
    public boolean generateLatLng(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return false;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();
            latLng = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException e) {
            Toast.makeText(context, "Endereço não encontrado", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    /* Gera endereço para gerar LatLng */
    public String enderecoLongo(){
        return rua + ", " + numero + ", " + bairro + ". " + cidade + " - " + estado;
    }

    public String enderecoCurto(){
        return bairro + ", " + cidade + " - " + estado;
    }

    public String getRua() {
        return rua;
    }

    public String getBairro() { return bairro; }

    public String getComplemento() {
        return complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getNumero() {
        return numero;
    }
}