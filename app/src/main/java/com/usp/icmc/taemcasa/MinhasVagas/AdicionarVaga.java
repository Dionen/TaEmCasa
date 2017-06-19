package com.usp.icmc.taemcasa.MinhasVagas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.usp.icmc.taemcasa.MinhasVagas.MoradiaResponse.VagaRequest_ADD;
import com.usp.icmc.taemcasa.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AdicionarVaga extends AppCompatActivity {

    private String nomeMoradia;
    private int id_rep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_vaga);

        Intent intent = getIntent();
        nomeMoradia = intent.getStringExtra("nome");
        id_rep = intent.getIntExtra("id_rep", -1);

        // Muda o titulo da activity para identificar a moradia selecionada.
        Resources res = getResources();
        String text = res.getString(R.string.nova_vaga, nomeMoradia);
        setTitle(text);

        Button adicionarVaga = (Button) findViewById(R.id.adicionarVaga);
        adicionarVaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checa_dados(v)){
                    vagas_request();
                }
            }
        });
    }

    private void vagas_request(){
        final Context context = getApplicationContext();
        EditText preco = (EditText) findViewById(R.id.precoVaga);
        RadioButton masculina = (RadioButton) findViewById(R.id.masculina);
        RadioButton feminina = (RadioButton) findViewById(R.id.feminina);
        CheckBox individual = (CheckBox) findViewById(R.id.compartilhado);
        String tipoString = "2";

        // Inicia tela de carregamento
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Adicionando nova vaga...");
        progress.setCancelable(false);
        progress.show();

        // Tipo da vag
        if (masculina.isChecked()){
            tipoString = "0";
        } else if (feminina.isChecked()){
            tipoString = "1";
        }
        // Quarto individual
        String individualString = "0";
        if (individual.isChecked()){
            individualString = "1";
        }

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    progress.dismiss();

                    if (success){
                        Toast toast = Toast.makeText(context, "Vaga cadastrada com sucesso", Toast.LENGTH_SHORT);
                        toast.show();
                        finish();
                    } else {
                        Toast toast = Toast.makeText(context, "Cadastro de vaga não autorizado", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(context, "Erro ao acessar database", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        };
        VagaRequest_ADD vagaRequest = new VagaRequest_ADD(id_rep + "", preco.getText().toString(), tipoString, individualString, responseListener);
        RequestQueue queue = Volley.newRequestQueue(AdicionarVaga.this);
        queue.add(vagaRequest);
    }

    private boolean checa_dados(View v){
        EditText preco;

        RadioGroup tipoGroup = (RadioGroup) findViewById(R.id.tipo);
        preco = (EditText) findViewById(R.id.precoVaga);
        Context context = v.getContext();

        if (preco.getText().toString().isEmpty()){
            Toast.makeText(context, "Insira o preço da vaga.", Toast.LENGTH_SHORT).show();
            preco.requestFocus();
            return false;
        } else if (tipoGroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(context, "Insira o tipo de vaga.", Toast.LENGTH_SHORT).show();
            tipoGroup.requestFocus();
            return false;
        } else {
            return true;
        }
    }
}