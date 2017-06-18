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

import com.usp.icmc.taemcasa.R;
import com.usp.icmc.taemcasa.Estruturas.Moradia;
import com.usp.icmc.taemcasa.Estruturas.Vaga;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Integer.parseInt;

public class MinhasVagasActivity extends Fragment {

    @Override
    public void onAttach (Context context){
        super.onAttach(context);
    }

    // Dados obtidos pela database
    List<Moradia> moradias = new ArrayList<Moradia>();
    HashMap<Moradia, List<Vaga>> vagas = new HashMap<Moradia, List<Vaga>>();

    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_my_list, container, false);
        expandableListView = (ExpandableListView) rootView.findViewById(R.id.expandable_list);

        // Funcões quer obtem os dados da database
        // Acho que dá pra fazer uma só, visto que "moradias" e "vagas" sao
        // variaveis globais da propria classe.
        moradias = getMoradias();
        vagas = getVagas(moradias);

        // Adapta uma expandableListView para exibir os valores baixados
        adapter = new ExpandableListAdapter(getActivity(), moradias, vagas);
        expandableListView.setAdapter(adapter);

        // Botão de adicionar vaga
        Button adicionarVaga = (Button) rootView.findViewById(R.id.adicionarVaga);
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

    public List<Moradia> getMoradias(){
        // Retorna List<> com os valores de moradias
        // Deve obter os dados online
        // Se for colocar os dados diretamente nas variaveis globais

        final List<Moradia> list = new ArrayList<Moradia>();

        for (int i = 0; i < 4; ++i) {
            list.add(new Moradia("Republica do Bozó nº",
                    "Casa grande com 4 quartos (dois na casa e dois nos fundos), sala de estudos, sala de visitas, sala de jantar, 2 banheiros. Temos acesso à Internet (Speedy), rede de computadores, TV à Cabo(NET), telefone, empregada todos os dias (lava, passa e cozinha).",
                    "Alameda da Rosas", "45", null, "Cidade Jardim", "São Carlos" ,"SP"));
        }

        return list;
    }

    /* Gera valores teste de vagas */
    public HashMap<Moradia, List<Vaga>> getVagas(List<Moradia> lista){
        HashMap<Moradia, List<Vaga>> _listDataChild = new HashMap<Moradia, List<Vaga>>();

        // Falta obter as vagas de cada moradia baixada
        // Da pra fazer isso chamando outra request dentro do mysqli_stmt_fetch($statement)
        // Aí da pra retornar um JSONArray de vagas dentro de um JSONArray de moradias

        // Valores teste
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

        List<Vaga> header4 = new ArrayList<Vaga>();
        header4.add(new Vaga("-1"));

        _listDataChild.put(lista.get(0), header1);
        _listDataChild.put(lista.get(1), header2);
        _listDataChild.put(lista.get(2), header3);
        _listDataChild.put(lista.get(3), header4);

        return _listDataChild;
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
        private List<Moradia> _listDataHeader;
        private HashMap<Moradia, List<Vaga>> _listDataChild;

        public ExpandableListAdapter(Context context, List<Moradia> listDataHeader,
                                     HashMap<Moradia, List<Vaga>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        public void setNewItems(List<Moradia> listDataHeader,HashMap<Moradia, List<Vaga>> listChildData) {
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
            notifyDataSetChanged();
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
        public View getChildView(final int groupPosition, final int childPosition,
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
                holder.adicionarVaga.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), AdicionarVaga.class);

                        Moradia moradia = (Moradia) getGroup(groupPosition);

                        intent.putExtra("nome", moradia.getNome());
                        intent.putExtra("id_rep", moradia.getId());
                        intent.putExtra("tipo", moradia.getTipo());
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

            Moradia header = (Moradia) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.search_template_2, null);
            }

            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView description = (TextView) convertView.findViewById(R.id.description);
            TextView address = (TextView) convertView.findViewById(R.id.address);

            //Inserindo as informacoes
            title.setText(header.getNome());
            description.setText(header.getDescricao());
            address.setText((header.getEndereco()).enderecoCurto());

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
