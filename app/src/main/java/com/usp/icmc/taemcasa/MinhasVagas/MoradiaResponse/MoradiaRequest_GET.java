package com.usp.icmc.taemcasa.MinhasVagas.MoradiaResponse;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fabio on 12/06/17.
 */

public class MoradiaRequest_GET extends StringRequest {
    private static final String GET_REQUEST_URL = "https://dionen.000webhostapp.com/Moradia/getMinhasVagas.php";

    private Map<String, String> params;

    public MoradiaRequest_GET(String username, Response.Listener<String> listener){
            super(Method.POST, GET_REQUEST_URL, listener, null);
            params = new HashMap<>();
            params.put("username", username);
    }

    @Override
    public Map<String, String> getParams() {
            return params;
        }


}
