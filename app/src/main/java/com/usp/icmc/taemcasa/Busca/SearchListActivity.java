package com.usp.icmc.taemcasa.Busca;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.usp.icmc.taemcasa.R;
import com.usp.icmc.taemcasa.Structures.Endereco;
import com.usp.icmc.taemcasa.Structures.Vaga;

import java.util.ArrayList;
import java.util.List;

public class SearchListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_list);

        ArrayList<Vaga> vagas = (ArrayList<Vaga>) getIntent().getSerializableExtra("vagas");

        ListView listaVagas = (ListView) findViewById(R.id.search_result_list);

        listaVagas.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               setContentView(R.layout.activity_perfil_vaga);
            }

        });

        /* Liga os valores ao layout */
        AdapterVaga adapter = new AdapterVaga(vagas, this);
        listaVagas.setAdapter(adapter);
    }

    private class AdapterVaga extends BaseAdapter {

        private final ArrayList<Vaga> search_data;
        private Activity activity;

        public AdapterVaga(ArrayList<Vaga> search_data, Activity activity) {
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
            address.setText(search.getAddress().enderecoCurto());
            price.setText(search.getPrice());
            type.setText(search.getTipoMoradia());
            imagem.setImageResource(R.drawable.ic_menu_gallery);
            return view;
        }

    }

}
