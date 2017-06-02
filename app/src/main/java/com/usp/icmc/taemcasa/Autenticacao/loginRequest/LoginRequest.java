package com.usp.icmc.taemcasa.Autenticacao.loginRequest;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joao on 16/05/2017.
 *
 */

public class LoginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "https://dionen.000webhostapp.com/Login.php";
    private Map<String, String> params;

    public LoginRequest(String email, Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }}