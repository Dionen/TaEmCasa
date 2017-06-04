package com.usp.icmc.taemcasa.Busca;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.usp.icmc.taemcasa.R;

public class SearchMenu extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Esse layout ta cagado, ta hard-coded e as ids não foram definidas (vou refazer isso aqui -joao).
        Mas o buscar funciona :P */
        final View rootView = inflater.inflate(R.layout.search_menu, container, false);
        final CheckBox actualLocation = (CheckBox) rootView.findViewById(R.id.actualLocation);
        final Button searchLocation = (Button) rootView.findViewById(R.id.searchLocation);

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

        Button fab = (Button) rootView.findViewById(R.id.buscar_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchListActivity.class);
                getActivity().startActivity(intent);
            }
        });


        return rootView;
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();


        // Check which checkbox was clicked

    }

}
