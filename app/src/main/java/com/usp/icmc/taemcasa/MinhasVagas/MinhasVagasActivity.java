package com.usp.icmc.taemcasa.MinhasVagas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.usp.icmc.taemcasa.MinhasVagas.MoradiaResponse.MinhasMoradias;
import com.usp.icmc.taemcasa.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.usp.icmc.taemcasa.Structures.Vaga;
import com.usp.icmc.taemcasa.Structures.Republica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MinhasVagasActivity extends Fragment {

    List<Republica> listaMoradias;
    HashMap<Republica, List<Vaga>> listaVagas;
    ExpandableListAdapter adapter;

    @Override
    public void onAttach (Context context){
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_my_list, container, false);
//      Incializa lista de moradias do usuário
        listaMoradias = new ArrayList<Republica>();
        listaVagas = new HashMap<Republica, List<Vaga>>();     // DEVE-SE OBTER AS MORADIAS E VAGAS DO USUARIO

        ExpandableListView minhasMoradias = (ExpandableListView) rootView.findViewById(R.id.expandable_list);
        Button adicionarVaga = (Button) rootView.findViewById(R.id.adicionarVaga);

        /* Liga os valores ao layout */
        adapter = new ExpandableListAdapter(getActivity(), listaMoradias, listaVagas);
        minhasMoradias.setAdapter(adapter);

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

    @Override
    public void onResume() {
        super.onResume();
        listaMoradiasRefresh();
        listaVagasRefresh();
        adapter.notifyDataSetChanged();
    }

    private void listaMoradiasRefresh() {
        listaMoradias.clear();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonResponse = null;

                try {
                    jsonResponse = new JSONObject(response);

                    boolean success = jsonResponse.getBoolean("success");
                    if(success) {
                        JSONArray moradiasResponse = jsonResponse.getJSONArray("data");

                        for(int i = 0; i < moradiasResponse.length(); i++) {
                            JSONObject atual = moradiasResponse.getJSONObject(i);

                            int id = atual.getInt("id");
                            String username = atual.getString("username");
                            String nome = atual.getString("nome");
                            String descricao = atual.getString("descricao");
                            String rua = atual.getString("rua");
                            String numero = atual.getString("numero");
                            String complemento = atual.getString("complemento");
                            String bairro = atual.getString("bairro");
                            String cidade = atual.getString("cidade");
                            String estado = atual.getString("estado");

//                          O JSON vem com floats em formato de string, então é preciso
//                          fazer essa validação antes do parse para float
                            String s = atual.getString("latitude");
                            float latitude = 0;
                            if (s != "null")
                                latitude = Float.parseFloat(s);

                            float longitude = 0;
                            s = atual.getString("longitude");
                            if (s != "null")
                                longitude = Float.parseFloat(s);

                            String telefone = atual.getString("telefone");
                            String link = atual.getString("link");
                            int tipo = atual.getInt("tipo");
                            int perfil = atual.getInt("perfil");
                            int qtd_moradores = atual.getInt("qtd_moradores");

//                          O bd armazena true como 1, então é preciso fazer essa
//                          validação para conseguir utilizar o boolean do java
                            boolean aceita_animais = false;
                            int bool = atual.getInt("aceita_animais");
                            if (bool == 1) aceita_animais = true;

                            Republica dados = new Republica(id, username, nome, descricao, rua, numero, complemento,
                                    bairro, cidade, estado, latitude, longitude, telefone, link, tipo, perfil, qtd_moradores, aceita_animais);
                            listaMoradias.add(dados);
                        }
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
    }

    /* Gera valores teste de vagas */
    public void listaVagasRefresh(){
        HashMap<Republica, List<Vaga>> _listDataChild = new HashMap<>();

        List<Vaga> header1 = new ArrayList<Vaga>();
        header1.add(new Vaga("-1"));    // Inicializar uma vaga com -1 coloca o botão de "adicionar vaga" no fim da lista filha.
                                        // Não tem jeito muito bonito de fazer isso.

        List<Vaga> header2 = new ArrayList<Vaga>();
        header2.add(new Vaga("$$", true, "Vaga Masculina"));
        header2.add(new Vaga("$$$$", false, "Vaga Feminina"));
        header2.add(new Vaga("-1"));

        List<Vaga> header3 = new ArrayList<Vaga>();
        header3.add(new Vaga("$$$", true, "Vaga Feminina"));
        header3.add(new Vaga("$$", false, "Vaga Feminina"));
        header3.add(new Vaga("$$", false, "Vaga Feminina"));
        header3.add(new Vaga("-1"));

        _listDataChild.put(listaMoradias.get(0), header1);
        _listDataChild.put(listaMoradias.get(1), header2);

        listaVagas =  _listDataChild;
    }

    /**
     * Classe responsável por associar as moradias do usuário com elementos
     * da lista no layout.
     *
     * É necessária uma lista de Moradias e um Hashmap<Moradia, List<Vaga>>
     * No momento só utiliza "Vaga".
     */
    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private List<Republica> _listDataHeader;
        private HashMap<Republica, List<Vaga>> _listDataChild;

        public ExpandableListAdapter(Context context, List<Republica> listDataHeader,
                                     HashMap<Republica, List<Vaga>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        /* Sub-classe necessária para podermos adicionar o botão "ADICIONAR VAGA" no final das sub-listas */
        class ViewHolder {
            TextView tipoMorador;
            TextView preco;
            TextView individualTexto;
            ImageView removerVaga;
            Button adicionarVaga;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            View view = convertView;
            final Vaga conteudoVaga = (Vaga) getChild(groupPosition, childPosition);

            LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.search_template_2_item, null);

            ViewHolder holder = new ViewHolder();
            holder.tipoMorador = (TextView) view.findViewById(R.id.tipoMorador);
            holder.preco = (TextView) view.findViewById(R.id.preco);
            holder.individualTexto = (TextView) view.findViewById(R.id.individualTexto);
            holder.removerVaga = (ImageView) view.findViewById(R.id.removerVaga);
            holder.adicionarVaga = (Button) view.findViewById(R.id.adicionarVaga);

            if (conteudoVaga.getPrice().equals("-1")){
                /* Caso seja o botão de adicionar vaga */
                holder.tipoMorador.setVisibility(View.GONE);
                holder.preco.setVisibility(View.GONE);
                holder.individualTexto.setVisibility(View.GONE);
                holder.removerVaga.setVisibility(View.GONE);
                holder.adicionarVaga.setVisibility(View.VISIBLE);
            } else {
                /*Inserindo as informacoes das vagas */
                holder.tipoMorador.setText(conteudoVaga.gettipoMorador());
                holder.preco.setText(conteudoVaga.getPrice());

                if (conteudoVaga.getIndividual()) {
                    holder.individualTexto.setText("Quarto Individual");
                } else {
                    holder.individualTexto.setText("Quarto Compartilhado");
                }

                /* REMOVER VAGA VEM AQUI */
            }

            view.setTag(holder);
            return view;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {

            Vaga header = (Vaga) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.search_template_2, null);
            }

            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView description = (TextView) convertView.findViewById(R.id.description);
            TextView address = (TextView) convertView.findViewById(R.id.address);

            //Inserindo as informacoes
            title.setText(header.getTitle());
            description.setText(header.getDescription());
            //address.setText(header.getAddress());

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

}