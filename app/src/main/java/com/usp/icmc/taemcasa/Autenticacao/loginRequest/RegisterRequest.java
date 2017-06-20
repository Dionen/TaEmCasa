package com.usp.icmc.taemcasa.Autenticacao.loginRequest;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joao on 16/05/2017.
 *
 */

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://dionen.000webhostapp.com/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String nome, String sobrenome, String email, String salt, String hash, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("nome", nome);
        params.put("sobrenome", sobrenome);
        params.put("email", email);
        params.put("salt", salt);
        params.put("hash", hash);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
