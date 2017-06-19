package com.usp.icmc.taemcasa.Wishlist;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.usp.icmc.taemcasa.MinhasVagas.AdicionarMoradia;
import com.usp.icmc.taemcasa.R;
import com.usp.icmc.taemcasa.Structures.Endereco;
import com.usp.icmc.taemcasa.Structures.Republica;
import com.usp.icmc.taemcasa.Structures.Vaga;
import com.usp.icmc.taemcasa.Utils.ConvertorBitmap;
import com.usp.icmc.taemcasa.Wishlist.WishlistResponse.WishlistRequest_GETALL;
import com.usp.icmc.taemcasa.Wishlist.WishlistResponse.WishlistRequest_REMOVE;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WishlistActivity extends Fragment {

    List<Vaga> vagas;
    WishlistActivity.AdapterWishList adapter;

    @Override
    public void onAttach (Context context){
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wishlist_layout, container, false);

        vagas = new ArrayList<Vaga>();
        ListView wishlistView = (ListView) rootView.findViewById(R.id.search_result_list);

        adapter = new WishlistActivity.AdapterWishList(vagas, (Activity) getActivity());
        wishlistView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        downloadWishlist();
        adapter.notifyDataSetChanged();
    }

    // Funções modificadoras de layout
    public void carregandoWishlist(View view){
        final View carregandoWishlist = view.findViewById(R.id.carregandoWishlist);
        final View listaWishlist = view.findViewById(R.id.listaWishlist);
        final View nadaWishlist = view.findViewById(R.id.nadaWishlist);

        carregandoWishlist.setVisibility(View.VISIBLE);
        listaWishlist.setVisibility(View.GONE);
        nadaWishlist.setVisibility(View.GONE);
    }

    public void vaziaWishlist(View view){
        final View carregandoWishlist = view.findViewById(R.id.carregandoWishlist);
        final View listaWishlist = view.findViewById(R.id.listaWishlist);
        final View nadaWishlist = view.findViewById(R.id.nadaWishlist);

        carregandoWishlist.setVisibility(View.GONE);
        listaWishlist.setVisibility(View.GONE);
        nadaWishlist.setVisibility(View.VISIBLE);
    }

    public void layoutWishlist(View view){
        final View carregandoWishlist = view.findViewById(R.id.carregandoWishlist);
        final View listaWishlist = view.findViewById(R.id.listaWishlist);
        final View nadaWishlist = view.findViewById(R.id.nadaWishlist);

        carregandoWishlist.setVisibility(View.GONE);
        listaWishlist.setVisibility(View.VISIBLE);
        nadaWishlist.setVisibility(View.GONE);
    }

    private void downloadWishlist() {
        final Context context = getContext();
        carregandoWishlist(getView());
        vagas.clear();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonResponse = null;
                layoutWishlist(getView());

                try {
                    jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray wishlistResponse = jsonResponse.getJSONArray("data");

                        if (wishlistResponse.length() == 0) vaziaWishlist(getView());

                        for (int i = 0; i < wishlistResponse.length(); i++) {
                            JSONObject atual = wishlistResponse.getJSONObject(i);

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
                        adapter.notifyDataSetChanged();
                    } else {
                        vaziaWishlist(getView());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        /* ENTRA NA DATABASE ONLINE */
        WishlistRequest_GETALL wishlistRequest = new WishlistRequest_GETALL(getActivity().getIntent().getExtras().getString("user_id"),  responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(wishlistRequest); // Executa as tarefas requisitadas
    }


    public void removeWishlist(int id_vaga){
        final Context context = getContext();
        String user_id = getActivity().getIntent().getExtras().getString("user_id");
        carregandoWishlist(getView());

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonResponse = null;
                layoutWishlist(getView());

                try {
                    jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        Toast.makeText(context, "Vaga removida com sucesso", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Erro ao remover vaga", Toast.LENGTH_SHORT).show();
                    }
                    onResume();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        /* ENTRA NA DATABASE ONLINE */
        WishlistRequest_REMOVE wishlistRequest = new WishlistRequest_REMOVE(user_id, id_vaga + "",  responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(wishlistRequest); // Executa as tarefas requisitadas
    }

    private class AdapterWishList extends BaseAdapter {

        private final List<Vaga> search_data;
        private Activity activity;

        public AdapterWishList(List<Vaga> search_data, Activity activity) {
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
        public View getView(int position, final View convertView, final ViewGroup parent) {
            final View view = activity.getLayoutInflater()
                    .inflate(R.layout.search_template_3, parent, false);

            final Vaga search = search_data.get(position);

            //pegando as referências das Views
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView address = (TextView) view.findViewById(R.id.address_3);
            TextView preco = (TextView) view.findViewById(R.id.precoWishlist);
            ImageView imagem = (ImageView) view.findViewById(R.id.icon);
            ImageView removeWishlist = (ImageView) view.findViewById(R.id.removeWishlist);

            //Inserindo as informacoes
            title.setText(search.getRepublica().getNome());
            address.setText(search.getRepublica().getEndereco().enderecoCurto());
            preco.setText("R$" + search.getPreco());

            Bitmap foto = ConvertorBitmap.StringToBitMap(search.getRepublica().getImagem());

            if (foto == null) imagem.setImageResource(R.drawable.ic_menu_gallery);
            else imagem.setImageBitmap(foto);

            removeWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    AlertDialog alerta;
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Deseja remover esta vaga de sua Wishlist?");
                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1){
                            removeWishlist(search.getId_vaga());
                        }
                    });
                    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //long length = BitMapToString(imagem).length();
                            //Toast.makeText(getApplicationContext(), length+"", Toast.LENGTH_LONG).show();
                        }
                    });
                    alerta = builder.create();
                    alerta.show();
                }
            });

            /*if (position == 2 || position == 4){
                description.setVisibility(View.GONE);
                address.setVisibility(View.GONE);
                preenchida.setVisibility(View.VISIBLE);
                imagem.setImageResource(R.drawable.ic_menu_share);
            }*/
            return view;
        }

    }
}
