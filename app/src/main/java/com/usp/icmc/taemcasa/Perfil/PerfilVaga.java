package com.usp.icmc.taemcasa.Perfil;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.usp.icmc.taemcasa.R;
import com.usp.icmc.taemcasa.Perfil.VagaResponse.VagaRequest;

import org.json.JSONException;
import org.json.JSONObject;

import static android.R.drawable.btn_star_big_off;
import static android.R.drawable.btn_star_big_on;

/**
 * Created by pupo on 08/06/2017.
 */

public class PerfilVaga extends AppCompatActivity {
 //   private int autoriaVaga; // 1 = do usuário, 2 = do sistema

    // vaga
    private int idVaga;
    private String nomeVaga;
    private int qtdVaga;
    private String tipoVaga;
    private int qtdMoradores;
    private double precoVaga;
    private String descVaga;
    private String telefone;

    // endereço
    private String ruaEndereco;
    private int numEndereco;
    private String complEndereco;
    private String bairroEndereco;
    private String cidadeEndereco;

    private TextView nomeVagaTV;
    private TextView tipoVagaTV;
    private TextView telefoneTV;
    private TextView qtdVagaTV;
    private TextView precoVagaTV;
    private TextView descTV;
    private TextView enderecoTV;
    private TextView bairroTV;
    private TextView cidadeTV;
    private TextView qtdMoradoresTV;

    private ImageButton wishlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_vaga);

        wishlist = (ImageButton) findViewById(R.id.adicionarWishlist);
        wishlist.setOnClickListener(wishlistHandler);
    }

    public PerfilVaga(int id) {
        idVaga = id;
    }

    View.OnClickListener wishlistHandler = new View.OnClickListener() {
        public void onClick(View v) {
            Context context = getApplicationContext();
            String star_off = String.valueOf(btn_star_big_off);
            String star_on = String.valueOf(btn_star_big_on);

            if (context.getResources().getIdentifier(star_off, "drawable", context.getPackageName()) != 0) {
                wishlist.setBackgroundResource(btn_star_big_on);
                Intent intent = getIntent();
                int id_vaga = intent.getIntExtra("id", -1);

                if (id_vaga == -1) {
                    ToastMessage("Não pode adicionar à wishlist.");
                } else {

                }

                // adicionar à wishlist
            } else if (context.getResources().getIdentifier(star_on, "drawable", context.getPackageName()) != 0) {
                wishlist.setBackgroundResource(btn_star_big_off);
                // remover da wishlist
            }
        }
    };

    public void exibirVaga() {
        nomeVagaTV = (TextView) findViewById(R.id.nomeVaga);
        nomeVagaTV.setText(nomeVaga);

        tipoVagaTV = (TextView) findViewById(R.id.tipoVaga);
        tipoVagaTV.setText(tipoVaga);

        telefoneTV = (TextView) findViewById(R.id.telefone);
        telefoneTV.setText(telefone);

        qtdVagaTV = (TextView) findViewById(R.id.qtdVagas);
        qtdVagaTV.setText(getString(R.string.qtdvagas, qtdVaga));

        precoVagaTV = (TextView) findViewById(R.id.precoVaga);
        precoVagaTV.setText(getString(R.string.precovaga, precoVaga));

        qtdMoradoresTV = (TextView) findViewById(R.id.numMoradores);
        qtdMoradoresTV.setText(getString(R.string.qtdmoradores, qtdMoradores));

        descTV = (TextView) findViewById(R.id.descText);
        descTV.setText(descVaga);

        enderecoTV = (TextView) findViewById(R.id.enderecoText);
        enderecoTV.setText(getString(R.string.enderecotext, ruaEndereco, numEndereco, complEndereco));

        bairroTV = (TextView) findViewById(R.id.bairroText);
        bairroTV.setText(bairroEndereco);

        cidadeTV = (TextView) findViewById(R.id.cidadeText);
        cidadeTV.setText(cidadeEndereco);
    }

    public void getVaga(final String response) throws JSONException {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonResponse = null;
                try {
                    jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        nomeVaga = jsonResponse.getString("nome");
                        qtdVaga = jsonResponse.getInt("qtd_vagas");
                        tipoVaga = jsonResponse.getString("tipo");
                        qtdMoradores = jsonResponse.getInt("qtd_moradores");
                        precoVaga = jsonResponse.getDouble("preco");
                        descVaga = jsonResponse.getString("descricao");
                        telefone = jsonResponse.getString("telefone");

                        ruaEndereco = jsonResponse.getString("rua");
                        numEndereco = jsonResponse.getInt("numero");
                        complEndereco = jsonResponse.getString("complemento");
                        bairroEndereco = jsonResponse.getString("bairro");
                        cidadeEndereco = jsonResponse.getString("cidade");

                    } else {
                        ToastMessage("Vaga não encontrada");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        };

            /* ENTRA NA DATABASE ONLINE */
        VagaRequest vagaRequest = new VagaRequest(Integer.toString(idVaga), responseListener);
        RequestQueue queue = Volley.newRequestQueue(PerfilVaga.this);
        queue.add(vagaRequest); // Executa as tarefas requisitadas
        exibirVaga();
    }
    private void ToastMessage(CharSequence text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}