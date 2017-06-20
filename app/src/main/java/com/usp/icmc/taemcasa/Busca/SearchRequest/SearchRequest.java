package com.usp.icmc.taemcasa.Busca.SearchRequest;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joao on 08/06/2017.
 */

public class SearchRequest extends StringRequest {

    /*$min_preco = $_POST["min_preco"];
    $max_preco = $_POST["max_preco"];
    $tipo = $_POST["tipo"];
    $individual = $_POST["individual"];
    $aceita_animais = $_POST["aceita_animais"];*/

    private static final String LOGIN_REQUEST_URL = "https://dionen.000webhostapp.com/Busca/BuscaVaga.php";
    private Map<String, String> params;

    public SearchRequest(String min_preco, String max_preco, String tipo_M_F, String individual, String aceita_animais, String tipo_A_R, String perfil, Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("min_preco", min_preco);
        params.put("max_preco", max_preco);
        params.put("tipo_M_F", tipo_M_F);
        params.put("individual", individual);
        params.put("aceita_animais", aceita_animais);
        params.put("tipo_A_R", tipo_A_R);
        params.put("perfil", perfil);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}