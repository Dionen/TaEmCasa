package com.example.joao.taemcasa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.joao.taemcasa.Menu.MenuActivity;

/**
 * Created by Joao on 15/05/2017.
 * Operações gerais. Adicionar splash screen.
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

    protected void checkNetworkConnection() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        }
    }

    protected void keepConnected(){
        SharedPreferences sharedPref = getSharedPreferences("logged_user", Context.MODE_PRIVATE);

        if (sharedPref.getBoolean("keep_connected", false)){
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            intent.putExtra("user_id",   sharedPref.getString("user_id", null));
            intent.putExtra("nome",      sharedPref.getString("nome", null));
            intent.putExtra("sobrenome", sharedPref.getString("sobrenome", null));
            intent.putExtra("email",     sharedPref.getString("email", null));

            MainActivity.this.startActivity(intent);
        }
    }


}
