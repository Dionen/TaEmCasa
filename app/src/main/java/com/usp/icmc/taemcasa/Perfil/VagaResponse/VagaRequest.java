package com.usp.icmc.taemcasa.Perfil.VagaResponse;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class VagaRequest extends StringRequest {

    private static final String VAGA_REQUEST_URL = "http://dionen.000webhostapp.com/GetMinhasMoradias.php";
    private Map<String, String> params;

    public VagaRequest(String idRep, Response.Listener<String> listener){
        super(Method.POST, VAGA_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id_rep", idRep);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }}