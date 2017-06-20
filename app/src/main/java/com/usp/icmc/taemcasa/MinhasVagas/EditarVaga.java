package com.usp.icmc.taemcasa.MinhasVagas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.usp.icmc.taemcasa.Perfil.VagaResponse.VagaRequest_EDIT;
import com.usp.icmc.taemcasa.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fabio on 19/06/17.
 */

public class EditarVaga extends AppCompatActivity {

    String nomeMoradia;
    int id_vaga;
    RadioGroup tipoGroup;
    RadioButton masc;
    RadioButton fem;
    RadioButton mista;

    EditText preco;
    CheckBox compartilhado;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editar_vaga);

        Intent intent = getIntent();
        nomeMoradia = intent.getStringExtra("nome");
        id_vaga = intent.getIntExtra("id_vaga", -1);

        // Muda o titulo da activity para identificar a moradia selecionada.
        Resources res = getResources();
        String text = res.getString(R.string.editar_vaga, nomeMoradia);
        setTitle(text);

        Button editarVaga = (Button) findViewById(R.id.editarVaga);
        editarVaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checa_dados(v)){
                    edita_request();
                }
            }
        });

        tipoGroup = (RadioGroup) findViewById(R.id.tipoEdicao);
        int tipo = Integer.parseInt( intent.getStringExtra("tipo") );
        switch (tipo) {
            case 0:
                tipoGroup.check(R.id.masculinaEdicao);
                break;
            case 1:
                tipoGroup.check(R.id.femininaEdicao);
                break;
            case 2:
                tipoGroup.check(R.id.mistaEdicao);
                break;
        }

        preco = (EditText) findViewById(R.id.precoVagaEdicao);
        preco.setText(intent.getStringExtra("preco"));

        compartilhado = (CheckBox) findViewById(R.id.compartilhadoEdicao);
        compartilhado.setChecked(intent.getBooleanExtra("compartilhado", false));
    }

    private boolean checa_dados(View v){
        EditText preco;

        tipoGroup = (RadioGroup) findViewById(R.id.tipoEdicao);
        preco = (EditText) findViewById(R.id.precoVagaEdicao);
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

    private void edita_request() {
        String tipoString = "2";

        // Inicia tela de carregamento
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Editando vaga...");
        progress.setCancelable(false);
        progress.show();

        // Tipo da vaga
        masc = (RadioButton) findViewById(R.id.masculinaEdicao);
        fem = (RadioButton) findViewById(R.id.femininaEdicao);
        if (masc.isChecked()){
            tipoString = "0";
        } else if (fem.isChecked())
            tipoString = "1";

        // Quarto individual
        String individualString = "0";
        if (compartilhado.isChecked()){
            individualString = "1";
        }

        String precoS = preco.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Context context = getApplicationContext();
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    progress.dismiss();

                    if (success){
                        Toast toast = Toast.makeText(context, "Vaga editada com sucesso", Toast.LENGTH_SHORT);
                        toast.show();
                        finish();
                    } else {
                        Toast toast = Toast.makeText(context, "Edicao de vaga não autorizado", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(context, "Erro ao acessar database", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        };

        VagaRequest_EDIT vagaRequest = new VagaRequest_EDIT(id_vaga, tipoString, individualString, precoS, responseListener);
        RequestQueue queue = Volley.newRequestQueue(EditarVaga.this);
        queue.add(vagaRequest);
    }
}