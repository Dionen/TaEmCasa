package com.usp.icmc.taemcasa.Perfil.VagaResponse;

import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fabio on 20/06/17.
 */

public class VagaRequest_EDIT extends StringRequest {
    private static final String EDIT_REQUEST_URL = "https://dionen.000webhostapp.com/Vagas/edit.php";

    private Map<String, String> params;

    public VagaRequest_EDIT(int id_vaga, String tipoString, String individualString, String preco, Response.Listener<String> listener){
        super(Method.POST, EDIT_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id_vaga", new Integer(id_vaga).toString());
        params.put("tipo", tipoString);
        params.put("individual", individualString);
        params.put("preco", preco);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
