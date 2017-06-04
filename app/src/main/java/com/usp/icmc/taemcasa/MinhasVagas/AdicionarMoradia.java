package com.usp.icmc.taemcasa.MinhasVagas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.usp.icmc.taemcasa.Autenticacao.RegisterActivity;
import com.usp.icmc.taemcasa.Autenticacao.loginRequest.RegisterRequest;
import com.usp.icmc.taemcasa.MinhasVagas.MoradiaResponse.MoradiaRequest;
import com.usp.icmc.taemcasa.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AdicionarMoradia extends AppCompatActivity {

    private String user_id;
    private String user_nome;
    private String user_sobrenome;
    private String user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_moradia);

        // Dados do usuário ativo
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        user_nome = intent.getStringExtra("nome");
        user_sobrenome = intent.getStringExtra("sobrenome");
        user_email = intent.getStringExtra("email");

        Button adicionarMoradia = (Button) findViewById(R.id.adicionarMoradia);
        adicionarMoradia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarMoradia(v);
            }
        });

    }

    /**
     * Coleta os dados do formulário de registro de uma moradia e os insere na database.
     * Ainda não checa se todos os dados foram inseridos
     * @param view
     */
    private void cadastrarMoradia(View view){
        final Context context = getApplicationContext();
        String animais = "0";     // 0 = Não aceita animais, 1 = aceita animais
        String perfil = "-1";      // 0 = Apartamento, 1 = República
        String tipo = "-1";        // 0 = Masculina, 1 = Feminina, 2 = Unissex

        EditText titulo = (EditText) findViewById(R.id.nomeMoradia);
        EditText logradouro = (EditText) findViewById(R.id.logradouro);
        EditText numero = (EditText) findViewById(R.id.numero);
        EditText bairro = (EditText) findViewById(R.id.bairro);
        EditText complemento = (EditText) findViewById(R.id.complemento);
        EditText cidade = (EditText) findViewById(R.id.cidade);
        EditText estado = (EditText) findViewById(R.id.estado);
        RadioButton apartamento = (RadioButton) findViewById(R.id.apartamento);
        RadioButton republica = (RadioButton) findViewById(R.id.republica);
        RadioButton masculina = (RadioButton) findViewById(R.id.masculina);
        RadioButton feminina = (RadioButton) findViewById(R.id.feminina);
        RadioButton mista = (RadioButton) findViewById(R.id.mista);
        EditText nMoradores = (EditText) findViewById(R.id.nMoradores);
        CheckBox aceitamAnimais = (CheckBox) findViewById(R.id.aceitamAnimais);
        EditText telefone = (EditText) findViewById(R.id.telefone);
        EditText descricao = (EditText) findViewById(R.id.descricao);

        if (masculina.isChecked()){
            tipo = "0";
        } else if (feminina.isChecked()){
            tipo = "1";
        } else if (mista.isChecked()){
            tipo = "2";
        }
        if (apartamento.isChecked()){
            perfil = "0";
        } else if (republica.isChecked()){
            perfil = "1";
        }
        if (aceitamAnimais.isChecked()) animais = "1";

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success){
                                // MORADIA CRIADA
                        Toast toast = Toast.makeText(context, "Moradia cadastrada com sucesso", Toast.LENGTH_SHORT);
                        toast.show();
                        finish();
                    } else {
                        Toast toast = Toast.makeText(context, "Cadastro não autorizado", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } catch (JSONException e) {
                            // PROBLEMAS
                    e.printStackTrace();
                    Toast toast = Toast.makeText(context, "Erro ao acessar database", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        };
        MoradiaRequest moradiaRequest = new MoradiaRequest(user_email, titulo.getText().toString(), descricao.getText().toString(), logradouro.getText().toString(), numero.getText().toString(),
                complemento.getText().toString(), bairro.getText().toString(), cidade.getText().toString(), estado.getText().toString(), telefone.getText().toString(),
                tipo, perfil, nMoradores.getText().toString(), animais, responseListener);
        RequestQueue queue = Volley.newRequestQueue(AdicionarMoradia.this);
        queue.add(moradiaRequest);

    }
}
