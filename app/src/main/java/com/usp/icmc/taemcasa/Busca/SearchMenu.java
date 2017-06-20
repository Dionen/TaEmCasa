package com.usp.icmc.taemcasa.Busca;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.usp.icmc.taemcasa.Busca.SearchRequest.SearchRequest;
import com.usp.icmc.taemcasa.Structures.Endereco;
import com.usp.icmc.taemcasa.Structures.Vaga;
import com.usp.icmc.taemcasa.R;
import com.usp.icmc.taemcasa.Utils.MessagesToUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchMenu extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.search_menu, container, false);
        final Button search = (Button) rootView.findViewById(R.id.buscar_button);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(v);
            }
        });

        return rootView;
    }

    public void search(final View view) {
        String animais = "0";
        String tipo_M_F = "2";
        String tipo_R_A = "2";
        String individual = "0";
        String perfil = "2";

        final Context context = view.getContext();
        EditText maxValue_camp = (EditText) getView().findViewById(R.id.edtMaxPreco);
        EditText minValue_camp = (EditText) getView().findViewById(R.id.edtMinPreco);

        CheckBox animals_camp = (CheckBox) getView().findViewById(R.id.checkboxAnimais);
        if (animals_camp.isChecked()) animais = "1";

        ToggleButton apt_camp = (ToggleButton) getView().findViewById(R.id.toggleButtonApt);
        ToggleButton rep_camp = (ToggleButton) getView().findViewById(R.id.toggleButtonRep);
        if (apt_camp.isChecked() && !rep_camp.isChecked()) tipo_R_A = "0";
        if (!apt_camp.isChecked() && rep_camp.isChecked()) tipo_R_A = "1";

        CheckBox calmo_camp = (CheckBox) getView().findViewById(R.id.checkBoxCalmo);
        CheckBox agitado_camp = (CheckBox) getView().findViewById(R.id.checkBoxAgitado);
        if (calmo_camp.isChecked() && !agitado_camp.isChecked()) perfil = "0";
        if (!calmo_camp.isChecked() && agitado_camp.isChecked()) perfil = "1";

        RadioButton masculino = (RadioButton) getView().findViewById(R.id.masculina);
        RadioButton feminina = (RadioButton) getView().findViewById(R.id.feminina);
        if (masculino.isChecked()) tipo_M_F = "0";
        if (feminina.isChecked()) tipo_M_F = "1";

        CheckBox quartoIndividual = (CheckBox) getView().findViewById(R.id.quartoIndividual);
        if (quartoIndividual.isChecked()) individual = "1";

        final MessagesToUser message = new MessagesToUser();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonResponse = null;
                //message.ToastMessage(response, context);
                try {
                    jsonResponse = new JSONObject(response);

                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {

                        Intent intent = new Intent(getActivity(), SearchListActivity.class);
                        JSONArray searchResponse = jsonResponse.getJSONArray("data");

                        ArrayList<Vaga> vagas = new ArrayList<>();

                        if(searchResponse.length() <= 0){
                            message.ToastMessage("Não há vagas com esses critérios", context);
                            //return;
                        }

                        for(int i = 0; i < searchResponse.length(); i++) {
                            JSONObject atual = searchResponse.getJSONObject(i);
                            Vaga vaga = new Vaga();
                            Endereco end = new Endereco();
                            vaga.setPrice(atual.getString("vaga.preco"));
                            vaga.setTitle(atual.getString("republica.nome"));
                            end.setRua(atual.getString("republica.rua"));
                            end.setNumero(atual.getString("republica.numero"));
                            end.setComplemento(atual.getString("republica.complemento"));
                            end.setCidade(atual.getString("republica.cidade"));
                            end.setEstado(atual.getString("republica.estado"));
                            vaga.setAddress(end);
                            vagas.add(vaga);
                        }
                        intent.putExtra("vagas",vagas);
                        startActivity(intent);
                    } else {
                        message.ToastMessage("Falha no request", context);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    message.ToastMessage("Falha na conexão", context);
                }
            }
        };
            /* ENTRA NA DATABASE ONLINE */
        SearchRequest searchRequest = new SearchRequest(minValue_camp.getText().toString(), maxValue_camp.getText().toString(), tipo_M_F, individual, animais, tipo_R_A, perfil, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(searchRequest); // Executa as tarefas requisitadas
    }
}
