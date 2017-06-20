package com.usp.icmc.taemcasa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.usp.icmc.taemcasa.Autenticacao.LoginActivity;
import com.usp.icmc.taemcasa.Menu.MenuActivity;

/**
 * Created by Joao on 15/05/2017.
 * Operações gerais, falta adicionar uma splash screen.
 * No momento não é usada.
 *
 * * DATABASE:
 * login: id1693452_poovagas
 */


public class MainActivity extends FragmentActivity {

    protected static boolean wifiConnected = false;
    protected static boolean mobileConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Modifica as variaveis que definem o status da conexão com internet.
     * Pode ser útil futuramente.
     */
    protected void checkNetworkConnection() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        }
    }


}
