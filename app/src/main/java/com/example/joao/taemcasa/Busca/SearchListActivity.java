package com.example.joao.taemcasa.Busca;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.joao.taemcasa.R;
import com.example.joao.taemcasa.Structures.Vaga;

import java.util.ArrayList;
import java.util.List;

public class SearchListActivity extends AppCompatActivity {

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_list);

        List<Vaga> cursos = todasAsVagas();
        ListView listaDeCursos = (ListView) findViewById(R.id.search_result_list);

        AdapterCursosPersonalizado adapter = new AdapterCursosPersonalizado(cursos, this);
        listaDeCursos.setAdapter(adapter);

    }

    public List<Vaga> todasAsVagas(){
        final List<Vaga> list = new ArrayList<Vaga>();

        for (int i = 0; i < 20; ++i) {
            list.add(new Vaga("Republica do Bozó", "Casa grande com 4 quartos (dois na casa e dois nos fundos), sala de estudos, sala de visitas, sala de jantar, 2 banheiros. Temos acesso à Internet (Speedy), rede de computadores, TV à Cabo(NET), telefone, empregada todos os dias (lava, passa e cozinha).", "Cidade Jardim, São Carlos - SP", "$$$", "Vaga unissex"));
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
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = activity.getLayoutInflater()
                    .inflate(R.layout.search_template_1, parent, false);

            Vaga search = search_data.get(position);

            //pegando as referências das Views
            TextView title = (TextView)
                    view.findViewById(R.id.title);
            TextView description = (TextView)
                    view.findViewById(R.id.description);
            TextView address = (TextView)
                    view.findViewById(R.id.address);
            TextView price = (TextView)
                    view.findViewById(R.id.price);
            TextView type = (TextView)
                    view.findViewById(R.id.type);
            ImageView imagem = (ImageView)
                    view.findViewById(R.id.icon);

            //Inserindo as informacoes
            title.setText(search.getTitle());
            description.setText(search.getDescription());
            address.setText(search.getAddress());
            price.setText(search.getPrice());
            type.setText(search.getType());
            imagem.setImageResource(R.drawable.ic_menu_gallery);
            return view;
        }

    }

}
