package com.example.joao.taemcasa.Autenticacao;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.joao.taemcasa.MainActivity;
import com.example.joao.taemcasa.Menu.MenuActivity;
import com.example.joao.taemcasa.R;
import com.example.joao.taemcasa.Autenticacao.loginRequest.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Joao on 15/05/2017.
 * Operações da tela de login.
 */

public class LoginActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        keepConnected();

        CheckBox keep_connected = (CheckBox)  findViewById(R.id.checkBox);
        keep_connected.setChecked(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final EditText username_camp = (EditText)findViewById(R.id.username);
        final EditText password_camp = (EditText)findViewById(R.id.password);

        showProgress(false);
        username_camp.setText(null);
        password_camp.setText(null);

    }

    public void login(View view){
        final EditText username_camp =  (EditText)  findViewById(R.id.username);
        final EditText password_camp =  (EditText)  findViewById(R.id.password);
        final CheckBox keep_connected = (CheckBox)  findViewById(R.id.checkBox);

        //checkNetworkConnection();

        /*if (!mobileConnected && !wifiConnected){
            ToastMessage("É necessário ter conexão com a internet");
            password_camp.setText(null);

        } else */ if (username_camp.getText().toString().equals("") || password_camp.getText().toString().equals("")){
            ToastMessage("E-mail/Senha incorretos");
            password_camp.setText(null);    // Apaga o campo "Senha"

        } else {
            showProgress(true); // Começa o Loading
            Response.Listener<String> responseListener = new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    JSONObject jsonResponse = null;

                    try {
                        jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if(success){
                            /* EMAIL ENCONTRADO */
                            String salt = jsonResponse.getString("salt");
                            String hash = jsonResponse.getString("hash");

                            if(PasswordAuthentication.check_password_hash(password_camp.getText().toString(), salt, hash)){
                                /*==================*/
                                /* LOGIN SUCCESSFUL */
                                /*==================*/
                                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);

                                /* Passa dados do usuario para a proxima activity */
                                String id = jsonResponse.getString("user_id");
                                String nome = jsonResponse.getString("nome");
                                String sobrenome = jsonResponse.getString("sobrenome");
                                intent.putExtra("user_id", id);
                                intent.putExtra("nome", nome);
                                intent.putExtra("sobrenome", sobrenome);
                                intent.putExtra("email", username_camp.getText().toString());

                                /* Guarda os dados do usuario para entrar automaticamente */
                                if (keep_connected.isChecked()){
                                    SharedPreferences sharedPref = getSharedPreferences("logged_user", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("nome", nome);
                                    editor.putString("sobrenome", sobrenome);
                                    editor.putString("email", username_camp.getText().toString());
                                    editor.putString("user_id", id);
                                    editor.putBoolean("keep_connected", true);
                                    editor.commit();
                                }

                                password_camp.setText(null);
                                LoginActivity.this.startActivity(intent);

                            } else {
                                /* EMAIL CADASTRADO, SENHA INCORRETA */
                                showProgress(false);
                                ToastMessage("E-mail/Senha incorretos");
                                password_camp.setText(null);
                            }
                        } else {
                            /* EMAIL NAO CADASTRADO */
                            showProgress(false);
                            ToastMessage("E-mail/Senha incorretos");
                            password_camp.setText(null);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            /* ENTRA NA DATABASE ONLINE */
            LoginRequest loginRequest = new LoginRequest(username_camp.getText().toString(), responseListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest); // Executa as tarefas requisitadas
        }
    }



    private void showProgress(final boolean show) {
        final View ProgressView = findViewById(R.id.login_progress);
        final View LoginFormView = findViewById(R.id.my_login_form);
        final View BottomButton = findViewById(R.id.naoCadastrado);
        final View Connecting = findViewById(R.id.textConectando);
        final View CheckBox = findViewById(R.id.checkBox);

        ProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        Connecting.setVisibility(show ? View.VISIBLE : View.GONE);
        LoginFormView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        BottomButton.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        CheckBox.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
    }

    public void RegisterAccount(View view){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void ToastMessage(CharSequence text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }



}
