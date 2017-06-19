package com.usp.icmc.taemcasa.MinhasVagas.MoradiaResponse;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dionen on 17/06/2017.
 */

public class VagaRequest_ADD extends StringRequest {
    private static final String GET_REQUEST_URL = "https://dionen.000webhostapp.com/Vagas/addVaga.php";

    private Map<String, String> params;

    public VagaRequest_ADD(String id_rep, String preco, String tipo, String individual, Response.Listener<String> listener){
        super(Method.POST, GET_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id_rep", id_rep);
        params.put("preco", preco);
        params.put("tipo", tipo);
        params.put("individual", individual);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }


}