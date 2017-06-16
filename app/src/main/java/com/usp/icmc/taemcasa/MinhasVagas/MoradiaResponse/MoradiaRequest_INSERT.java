package com.usp.icmc.taemcasa.MinhasVagas.MoradiaResponse;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joao on 16/05/2017.
 *
 */


public class MoradiaRequest_INSERT extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://dionen.000webhostapp.com/RegisterMoradia.php";
    private Map<String, String> params;

    public MoradiaRequest_INSERT(String username, String nome, String descricao, String rua, String numero, String complemento,
                                 String bairro, String cidade, String estado, String telefone, String tipo, String perfil,
                                 String qtd_moradores, String aceita_animais, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("username", username);
        params.put("nome", nome);
        params.put("descricao", descricao);
        params.put("rua", rua);
        params.put("numero", numero);
        params.put("complemento", complemento);
        params.put("bairro", bairro);
        params.put("cidade", cidade);
        params.put("estado", estado);
        params.put("telefone", telefone);
        params.put("tipo", tipo);
        params.put("perfil", perfil);
        params.put("qtd_moradores", qtd_moradores);
        params.put("aceita_animais", aceita_animais);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
