package com.usp.icmc.taemcasa.MinhasVagas;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.usp.icmc.taemcasa.R;
import com.usp.icmc.taemcasa.Structures.Vaga;

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

        List<Vaga> cursos = todasAsVagas();
        ListView listaDeCursos = (ListView) rootView.findViewById(R.id.search_result_list);

        
        AdapterCursosPersonalizado adapter = new AdapterCursosPersonalizado(cursos, (Activity) getActivity());
        listaDeCursos.setAdapter(adapter);
        return rootView;
    }

    public List<Vaga> todasAsVagas(){
        final List<Vaga> list = new ArrayList<Vaga>();

        for (int i = 0; i < 10; ++i) {
            list.add(new Vaga("Republica do Bozó",
                    "Casa grande com 4 quartos (dois na casa e dois nos fundos), sala de estudos, sala de visitas, sala de jantar, 2 banheiros. Temos acesso à Internet (Speedy), rede de computadores, TV à Cabo(NET), telefone, empregada todos os dias (lava, passa e cozinha).",
                    "Cidade Jardim, São Carlos - SP",
                    "$$$",
                    "Vaga unissex"));
        }

        return list;
    }

    private class AdapterCursosPersonalizado extends BaseAdapter {

        private final List<Vaga> search_data;
        private Activity activity;

        public AdapterCursosPersonalizado(List<Vaga> search_data, Activity activity) {
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

            Vaga search = search_data.get(position);

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
            title.setText(search.getTitle());
            description.setText(search.getDescription());
            price.setText(search.getPrice());

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
