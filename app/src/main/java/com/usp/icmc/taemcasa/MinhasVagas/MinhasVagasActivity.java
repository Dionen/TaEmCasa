package com.usp.icmc.taemcasa.MinhasVagas;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.usp.icmc.taemcasa.MinhasVagas.MoradiaResponse.MoradiaRequest_DELETE;
import com.usp.icmc.taemcasa.MinhasVagas.MoradiaResponse.MoradiaRequest_GET;
import com.usp.icmc.taemcasa.Perfil.VagaResponse.VagaRequest_DELETE;
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
        minhasVagasRefresh();
        adapter.notifyDataSetChanged();
    }

    private void minhasVagasRefresh() {
        listaMoradias.clear();
        listaVagas.clear();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonResponse = null;

                try {
                    jsonResponse = new JSONObject(response);

                    boolean success = jsonResponse.getBoolean("success");
                    if(success) {
                        HashMap<Integer, Republica> aux = new HashMap<Integer, Republica>();

                        JSONArray moradiasResponse = jsonResponse.getJSONArray("moradias");

                        for (int i = 0; i < moradiasResponse.length(); i++) {
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
                            String imagem = atual.getString("imagem");

                            String sTipo = atual.getString("tipo");
                            int tipo = 0;
                            if(!sTipo.equals("null")) tipo = Integer.parseInt(sTipo);

                            int perfil = atual.getInt("perfil");
                            int qtd_moradores = atual.getInt("qtd_moradores");

//                          O bd armazena true como 1, então é preciso fazer essa
//                          validação para conseguir utilizar o boolean do java
                            boolean aceita_animais = false;
                            int bool = atual.getInt("aceita_animais");
                            if (bool == 1) aceita_animais = true;

                            Republica dados = new Republica(id, username, nome, descricao, rua, numero, complemento,
                                    bairro, cidade, estado, latitude, longitude, telefone, imagem, tipo, perfil, qtd_moradores, aceita_animais);
                            listaMoradias.add(dados);
                            listaVagas.put(dados, new ArrayList<Vaga>());
                            aux.put(id, dados);
                        }

                        JSONArray vagasResponse = jsonResponse.getJSONArray("vagas");

                        for (int i = 0; i < vagasResponse.length(); i++) {
                            JSONObject atual = vagasResponse.getJSONObject(i);

                            int id = atual.getInt("id");
                            int id_rep = atual.getInt("id_rep");
                            String preco = atual.getString("preco");
                            String tipo = Integer.toString(atual.getInt("tipo"));

                            boolean individual = false;
                            int bool = atual.getInt("individual");
                            if (bool == 1) individual = true;

                            Vaga dados = new Vaga(id, id_rep, preco, tipo, individual);

                            Republica auxiliar = aux.get(id_rep);
                            System.out.println(id_rep + " é o id_rep da vaga " + id);
                            listaVagas.get(auxiliar).add(dados);
                        }

                        java.util.Iterator iter = listaVagas.keySet().iterator();
                        while(iter.hasNext()) {
                            Republica auxR = (Republica) iter.next();
                            List<Vaga> l = listaVagas.get(auxR);
                            l.add(new Vaga("-1", auxR.getId()));
                        }
                        adapter.notifyDataSetChanged();
                    }

                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        /* ENTRA NA DATABASE ONLINE */
        MoradiaRequest_GET minhasMoradiasRequest = new MoradiaRequest_GET(getActivity().getIntent().getExtras().getString("email"),  responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(minhasMoradiasRequest); // Executa as tarefas requisitadas
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
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
            ImageView editarVaga;
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
            holder.editarVaga = (ImageView) view.findViewById(R.id.editarVaga);

            if (conteudoVaga.getPrice().equals("-1")){
                /* Caso seja o botão de adicionar vaga */
                holder.tipoMorador.setVisibility(View.GONE);
                holder.preco.setVisibility(View.GONE);
                holder.individualTexto.setVisibility(View.GONE);
                holder.removerVaga.setVisibility(View.GONE);
                holder.adicionarVaga.setVisibility(View.VISIBLE);

                holder.adicionarVaga.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), AdicionarVaga.class);
                        intent.putExtra("nome", getActivity().getIntent().getExtras().getString("nome"));
                        intent.putExtra("id_rep", conteudoVaga.getId_rep());
                        startActivity(intent);
                    }
                });

            } else {
                /*Inserindo as informacoes das vagas */
                holder.tipoMorador.setText(conteudoVaga.gettipoMorador());
                holder.preco.setText(conteudoVaga.getPrice());

                if (conteudoVaga.getIndividual()) {
                    holder.individualTexto.setText("Quarto Individual");
                } else {
                    holder.individualTexto.setText("Quarto Compartilhado");
                }

                holder.removerVaga.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject jsonResponse = null;

                                try {
                                    jsonResponse = new JSONObject(response);

                                    if(!jsonResponse.getBoolean("success"))
                                        Toast.makeText(getActivity(), "A vaga não pode ser removida. Por favor, tente novamente!", Toast.LENGTH_LONG).show();

                                } catch(JSONException e) {
                                    e.printStackTrace();
                                }

                                minhasVagasRefresh();
                                adapter.notifyDataSetChanged();
                            }
                        };

                        /* ENTRA NA DATABASE ONLINE */
                        VagaRequest_DELETE apagarRequest = new VagaRequest_DELETE(conteudoVaga.getId(),  responseListener);
                        RequestQueue queue = Volley.newRequestQueue(getContext());
                        queue.add(apagarRequest); // Executa as tarefas requisitadas
                    }
                });

                holder.editarVaga.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), EditarVaga.class);
                        intent.putExtra("nome", getActivity().getIntent().getExtras().getString("nome"));
                        intent.putExtra("id_vaga", conteudoVaga.getId());
                        intent.putExtra("tipo", conteudoVaga.gettipoMorador());
                        intent.putExtra("preco", conteudoVaga.getPrice());
                        intent.putExtra("compartilhado", conteudoVaga.getIndividual() );
                        startActivity(intent);
                    }
                });

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
        public View getGroupView(final int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {

            final Republica header = (Republica) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.search_template_2, null);
            }

            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView description = (TextView) convertView.findViewById(R.id.description);
            TextView address = (TextView) convertView.findViewById(R.id.address);
            ImageView fotoMoradia = (ImageView) convertView.findViewById(R.id.fotoMoradia);
            ImageView editar = (ImageView) convertView.findViewById(R.id.opcoesMoradia);
            ImageView remover = (ImageView) convertView.findViewById(R.id.deleteMoradia);

            //Inserindo as informacoes
            title.setText(header.getNome());
            description.setText(header.getDescricao());
            address.setText(header.getEndereco().enderecoCurto());

            remover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jsonResponse = null;
                            try {
                                jsonResponse = new JSONObject(response);

                                if (jsonResponse.getBoolean("success") == false)
                                    Toast.makeText(getActivity(), "A moradia não pode ser removida. Por favor, tente novamente!", Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            minhasVagasRefresh();
                            adapter.notifyDataSetChanged();
                        }

                    };

                    /* ENTRA NA DATABASE ONLINE */
                    MoradiaRequest_DELETE apagarRequest = new MoradiaRequest_DELETE(header.getId(),  responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    queue.add(apagarRequest); // Executa as tarefas requisitadas
                }

            });

            Bitmap thumbnail = StringToBitMap(header.getImagem());
            if (thumbnail != null) fotoMoradia.setImageBitmap(thumbnail);
            else fotoMoradia.setVisibility(View.GONE);

            ImageView config = (ImageView) convertView.findViewById(R.id.opcoesMoradia);
            config.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(),  EditarMoradia.class);
                    intent.putExtra("user_id",   getActivity().getIntent().getExtras().getString("user_id"));
                    intent.putExtra("email",     getActivity().getIntent().getExtras().getString("email"));
                    intent.putExtra("titulo", listaMoradias.get(groupPosition).getNome());
                    intent.putExtra("logradouro", listaMoradias.get(groupPosition).getEndereco().getRua());
                    intent.putExtra("numero", listaMoradias.get(groupPosition).getEndereco().getNumero());
                    intent.putExtra("bairro", listaMoradias.get(groupPosition).getEndereco().getBairro());
                    intent.putExtra("complemento", listaMoradias.get(groupPosition).getEndereco().getComplemento());
                    intent.putExtra("cidade", listaMoradias.get(groupPosition).getEndereco().getCidade());
                    intent.putExtra("estado", listaMoradias.get(groupPosition).getEndereco().getEstado());
                    intent.putExtra("tipo", listaMoradias.get(groupPosition).getTipo());
                    intent.putExtra("perfil", listaMoradias.get(groupPosition).getPerfil());
                    intent.putExtra("nmoradores", listaMoradias.get(groupPosition).getQtd_moradores());
                    intent.putExtra("animais", listaMoradias.get(groupPosition).isAceita_animais());
                    intent.putExtra("telefone", listaMoradias.get(groupPosition).getTelefone());
                    intent.putExtra("descricao", listaMoradias.get(groupPosition).getDescricao());
                    intent.putExtra("id_rep", listaMoradias.get(groupPosition).getId_rep());
                    startActivity(intent);
                }
            });

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