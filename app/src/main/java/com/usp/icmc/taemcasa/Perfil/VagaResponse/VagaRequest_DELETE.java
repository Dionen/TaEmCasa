package com.usp.icmc.taemcasa.Perfil.VagaResponse;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.*;

/**
 * Created by fabio on 19/06/17.
 */

public class VagaRequest_DELETE extends StringRequest {
    private static final String DELETE_REQUEST_URL = "https://dionen.000webhostapp.com/Vagas/delete.php";

    private Map<String, String> params;

    public VagaRequest_DELETE(int id, Response.Listener<String> listener){
        super(Method.POST, DELETE_REQUEST_URL, listener, null);
        params = new HashMap<>();
//        GAMBIAAAAAAAAAAAAAARRRA
        params.put("id", new Integer(id).toString());
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
