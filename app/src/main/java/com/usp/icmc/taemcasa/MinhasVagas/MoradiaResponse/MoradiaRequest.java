package com.usp.icmc.taemcasa.MinhasVagas.MoradiaResponse;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joao on 16/05/2017.
 *
 */

/*
CREATE TABLE republica (
	id_rep INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(20) NOT NULL, ----- Passa o e-mail
	nome VARCHAR(20),
	descricao VARCHAR(512),
	rua VARCHAR(64),
	numero VARCHAR(8),
	complemento VARCHAR(64),
	bairro VARCHAR(20),
	cidade VARCHAR(20),
	estado VARCHAR(20),
	latitude FLOAT,  -------- Da pra conseguir esses valores quando linkarmos com a API do maps
	longitude FLOAT,
	telefone VARCHAR(12),
	link VARCHAR(64), ------- Não adicionei ainda. Deve ter um jeito mais prático de linkar
	                          uma moradia com um link que não seja através de EditText
	tipo INT,
	perfil INT,
	qtd_moradores INT,
	aceita_animais BOOLEAN
);
 */

public class MoradiaRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://dionen.000webhostapp.com/RegisterMoradia.php";
    private Map<String, String> params;

    public MoradiaRequest(String username, String nome, String descricao, String rua, String numero, String complemento,
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
