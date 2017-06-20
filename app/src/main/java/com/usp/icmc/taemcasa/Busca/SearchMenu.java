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
import com.usp.icmc.taemcasa.Structures.Republica;
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

        final View rootView = inflater.inflate(R.layout.search_menu2, container, false);
        final Button search = (Button) rootView.findViewById(R.id.buscar_button);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(v);
            }
        });

        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        terminaBusca(getView());
    }

    public void carregandoBusca(View view){
        final View carregandoBusca = view.findViewById(R.id.carregandoBusca);
        final View scrollMenuBusca = view.findViewById(R.id.scrollMenuBusca);

        carregandoBusca.setVisibility(View.VISIBLE);
        scrollMenuBusca.setVisibility(View.GONE);
    }

    public void terminaBusca(View view){
        final View carregandoBusca = view.findViewById(R.id.carregandoBusca);
        final View scrollMenuBusca = view.findViewById(R.id.scrollMenuBusca);

        carregandoBusca.setVisibility(View.GONE);
        scrollMenuBusca.setVisibility(View.VISIBLE);
    }

    public void search(final View view) {
        String animais = "0";
        String tipo_M_F = "2";
        String tipo_R_A = "2";
        String individual = "0";
        String perfil = "2";

        carregandoBusca(getView());

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

                try {
                    jsonResponse = new JSONObject(response);

                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {

                        Intent intent = new Intent(getActivity(), SearchListActivity.class);
                        JSONArray searchResponse = jsonResponse.getJSONArray("data");

                        ArrayList<Vaga> vagas = new ArrayList<>();

                        if(searchResponse.length() <= 0){
                            message.ToastMessage("Não há vagas com estes critérios", context);
                            terminaBusca(view);
                            return;
                        }

                        for(int i = 0; i < searchResponse.length(); i++) {
                            JSONObject atual = searchResponse.getJSONObject(i);

                            Endereco end = new Endereco();
                            Republica rep = new Republica();
                            Vaga vaga = new Vaga();

                            end.setRua(atual.getString("republica.rua"));
                            end.setNumero(atual.getString("republica.numero"));
                            end.setComplemento(atual.getString("republica.complemento"));
                            end.setBairro(atual.getString("republica.bairro"));
                            end.setCidade(atual.getString("republica.cidade"));
                            end.setEstado(atual.getString("republica.estado"));

                            rep.setEndereco(end);
                            rep.setNome(atual.getString("republica.nome"));
                            rep.setDescricao(atual.getString("republica.descricao"));
                            rep.setTelefone(atual.getString("republica.telefone"));
                            rep.setImagem(atual.getString("republica.imagem"));
                            rep.setTipo(atual.getString("republica.tipo_r"));
                            rep.setPerfil(atual.getString("republica.perfil"));
                            rep.setQtd_moradores(atual.getString("republica.qtd_moradores"));
                            rep.setAceita_animais(atual.getString("republica.aceita_animais"));

                            vaga.setRepublica(rep);
                            vaga.setPreco(atual.getString("vaga.preco"));
                            vaga.setTipo(atual.getString("vaga.tipo_v"));
                            vaga.setIndividual(atual.getString("vaga.individual"));
                            vaga.setId_vaga(atual.getString("vaga.id_vaga"));

                            vagas.add(vaga);
                        }
                        intent.putExtra("vagas",vagas);
                        startActivity(intent);
                    } else {
                        message.ToastMessage("Falha no request", context);
                        terminaBusca(getView());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    message.ToastMessage("Falha na conexão", context);
                    terminaBusca(getView());
                }
            }
        };
            /* ENTRA NA DATABASE ONLINE */
        SearchRequest searchRequest = new SearchRequest(minValue_camp.getText().toString(), maxValue_camp.getText().toString(), tipo_M_F, individual, animais, tipo_R_A, perfil, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(searchRequest); // Executa as tarefas requisitadas
    }
}
