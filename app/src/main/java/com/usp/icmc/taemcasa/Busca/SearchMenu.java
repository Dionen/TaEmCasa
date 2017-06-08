package com.usp.icmc.taemcasa.Busca;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.usp.icmc.taemcasa.Busca.SearchRequest.SearchRequest;
import com.usp.icmc.taemcasa.R;
import com.usp.icmc.taemcasa.Utils.MessagesToUser;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchMenu extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.search_menu, container, false);
        final CheckBox actualLocation = (CheckBox) rootView.findViewById(R.id.actualLocation);
        final Button searchLocation = (Button) rootView.findViewById(R.id.searchLocation);
        final Button search = (Button) rootView.findViewById(R.id.buscar_button);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(v);
            }
        });

        actualLocation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (actualLocation.isChecked()){
                    searchLocation.setVisibility(View.GONE);
                } else {
                    searchLocation.setVisibility(View.VISIBLE);

                }
            }
        });

        return rootView;
    }

    public void search(final View view){
        String animais = "0";
        String tipo = "2";
        String individual = "0";

        Log.d("search", "Entrou na busca");
        final Context context = view.getContext();
        EditText maxValue_camp =  (EditText)  getView().findViewById(R.id.edtMaxPreco);
        EditText minValue_camp =  (EditText)  getView().findViewById(R.id.edtMinPreco);

        CheckBox animals_camp = (CheckBox)  getView().findViewById(R.id.checkboxAnimais);
        if (animals_camp.isChecked()) animais = "1";

        //ToggleButton apt_camp = (ToggleButton) getView().findViewById(R.id.toggleButtonApt);
        //ToggleButton rep_camp = (ToggleButton) getView().findViewById(R.id.toggleButtonRep);

        RadioButton masculino = (RadioButton) getView().findViewById(R.id.masculina);
        RadioButton feminina = (RadioButton) getView().findViewById(R.id.feminina);
        if (masculino.isChecked()) tipo = "0";
        if (feminina.isChecked()) tipo = "1";

        CheckBox quartoIndividual = (CheckBox)  getView().findViewById(R.id.quartoIndividual);
        if (quartoIndividual.isChecked()) individual = "1";

        final MessagesToUser message = new MessagesToUser();

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                JSONObject jsonResponse = null;

                try {
                    jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if(success){

                        Intent intent = new Intent(getActivity(), SearchListActivity.class);

                                /* Passa dados do usuario para a proxima activity */
                        //String id = jsonResponse.getString("user_id");
                        //String nome = jsonResponse.getString("nome");
                        //String sobrenome = jsonResponse.getString("sobrenome");
                        //intent.putExtra("user_id", id);
                        //intent.putExtra("nome", nome);
                        //intent.putExtra("sobrenome", sobrenome);
                        //intent.putExtra("email", email.getText().toString());


                        //String id = response.getString("user_id");
                        //intent.putExtra("vagas", vagas);
                            /*$response[]["success"] = true;
                            $response[]["vaga.preco"] = $preco;
                            $response[]["republica.nome"] = $nome;
                            $response[]["republica.rua"] = $rua;
                            $response[]["republica.numero"] = $numero;
                            $response[]["republica.complemento"] = $complemento;
                            $response[]["republica.cidade"] = $cidade;
                            $response[]["republica.estado"] = $estado;*/

                        startActivity(intent);
                    } else {
                        message.ToastMessage("Falha na request", context);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    message.ToastMessage("Falha na conexão", context);
                }
            }
        };
            /* ENTRA NA DATABASE ONLINE */
        SearchRequest searchRequest = new SearchRequest(maxValue_camp.getText().toString(), minValue_camp.getText().toString(), tipo, individual, animais, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(searchRequest); // Executa as tarefas requisitadas
    }

}
