package com.usp.icmc.taemcasa.MinhasVagas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.usp.icmc.taemcasa.Autenticacao.LoginActivity;
import com.usp.icmc.taemcasa.Autenticacao.PasswordAuthentication;
import com.usp.icmc.taemcasa.Autenticacao.loginRequest.LoginRequest;
import com.usp.icmc.taemcasa.Menu.MenuActivity;
import com.usp.icmc.taemcasa.MinhasVagas.MoradiaResponse.MinhasMoradias;
import com.usp.icmc.taemcasa.R;
import com.usp.icmc.taemcasa.Structures.Republica;
import com.usp.icmc.taemcasa.Structures.Vaga;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MinhasVagasActivity extends Fragment {

    @Override
    public void onAttach (Context context){
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_my_list, container, false);

        List<Republica> cursos = todasAsMoradias(); // GERA MORADIAS TESTE
        // DEVE-SE OBTER AS MORADIAS DO USUARIO
        ListView listaDeCursos = (ListView) rootView.findViewById(R.id.search_result_list);
        Button adicionarVaga = (Button) rootView.findViewById(R.id.adicionarVaga);

        /* Liga os valores ao layout */
        AdapterCursosPersonalizado adapter = new AdapterCursosPersonalizado(cursos, (Activity) getActivity());
        listaDeCursos.setAdapter(adapter);

        /* Botão de iniciar cadastro de moradia */
        adicionarVaga.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),  AdicionarMoradia.class);

                intent.putExtra("user_id",   getActivity().getIntent().getExtras().getString("user_id"));
                intent.putExtra("nome",      getActivity().getIntent().getExtras().getString("nome"));
                intent.putExtra("sobrenome", getActivity().getIntent().getExtras().getString("sobrenome"));
                intent.putExtra("email",     getActivity().getIntent().getExtras().getString("email"));
                startActivity(intent);
            }
        });

        return rootView;
    }

    private List<Republica> todasAsMoradias() {
        final List<Republica> lista = new ArrayList<Republica>();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonResponse = null;

                try {
                    jsonResponse = new JSONObject(response);

                    boolean success = jsonResponse.getBoolean("success");
                    if(success) {
                        String size = jsonResponse.getString("size");
                        Toast.makeText(getContext(), size, Toast.LENGTH_LONG).show();
//                        for (int i = 0; i < size; i++) {
//                            JSONObject atual = jsonResponse.getJSONObject(Integer.toString(i));

//                            int id = atual.getInt("id");
//                            String username = atual.getString("username");
//                            String nome = atual.getString("nome");
//                            String descricao = atual.getString("descricao");
//                            String rua = atual.getString("rua");
//                            String numero = atual.getString("numero");
//                            String complemento = atual.getString("complemento");
//                            String bairro = atual.getString("bairro");
//                            String cidade = atual.getString("cidade");
//                            String estado = atual.getString("estado");
//                            double latitude = atual.getDouble("latitude");
//                            double longitude = atual.getDouble("longitude");
//                            String telefone = atual.getString("telefone");
//                            String link = atual.getString("link");
//                            int tipo = atual.getInt("tipo");
//                            int perfil = atual.getInt("perfil");
//                            int qtd_moradores = atual.getInt("qtd_moradores");
//                            boolean aceita_animais = atual.getBoolean("aceita_animais");
//
//                            Republica dados = new Republica(id, username, nome, descricao, rua, numero, complemento,
//                                    bairro, cidade, estado, latitude, longitude, telefone, link, tipo, perfil, qtd_moradores, aceita_animais);
//                            lista.add(dados);
//                        }
                    }

                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        };
            /* ENTRA NA DATABASE ONLINE */
        MinhasMoradias minhasMoradiasRequest = new MinhasMoradias(getActivity().getIntent().getExtras().getString("email"),  responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(minhasMoradiasRequest); // Executa as tarefas requisitadas

        return lista;
    }

    /**
     * Classe responsável por associar as moradias do usuário com elementos
     * da lista no layout.
     */
    private class AdapterCursosPersonalizado extends BaseAdapter {

        private final List<Republica> search_data;
        private Activity activity;

        public AdapterCursosPersonalizado(List<Republica> search_data, Activity activity) {
            this.search_data = search_data;
            this.activity = activity;
        }

        @Override
        public int getCount() {
            return search_data.size();
        }

        @Override
        public Object getItem(int position) {
            return search_data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = activity.getLayoutInflater()
                    .inflate(R.layout.search_template_2, parent, false);

            Republica search = search_data.get(position);

            //pegando as referências das Views
            TextView title = (TextView)
                    view.findViewById(R.id.title);
            TextView description = (TextView)
                    view.findViewById(R.id.description);
            TextView people_interested = (TextView)
                    view.findViewById(R.id.people_interested);
            TextView price = (TextView)
                    view.findViewById(R.id.price);
            ImageView imagem = (ImageView)
                    view.findViewById(R.id.icon);

            //Inserindo as informacoes
            title.setText(search.getNome());
            description.setText(search.getDescricao());
            price.setText(search.getQtd_moradores());

            if (position > 1){
                people_interested.setText(position + " pessoas interessadas!");
            } else if (position == 0) {
                people_interested.setVisibility(View.GONE);
                description.setMaxLines(3);
            } else if (position == 1){
                people_interested.setText(position + " pessoa interessada!");
            }

            imagem.setImageResource(R.drawable.ic_menu_gallery);
            return view;
        }

    }

}