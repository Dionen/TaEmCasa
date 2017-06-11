package com.usp.icmc.taemcasa.Autenticacao;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.usp.icmc.taemcasa.Menu.MenuActivity;
import com.usp.icmc.taemcasa.R;
import com.usp.icmc.taemcasa.Autenticacao.loginRequest.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Joao on 15/05/2017.
 * Operações da tela de login.
 */

public class LoginActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        permanecerConectado();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mostraCarregamento(false);
    }

    /**
     * Faz o login automaticamente caso a opção "permanecerConectado"
     * tenha sido escolhida pelo usuário.
     */
    protected void permanecerConectado(){
        SharedPreferences sharedPref = getSharedPreferences("logged_user", Context.MODE_PRIVATE);

        if (sharedPref.getBoolean("keep_connected", false)){
            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
            intent.putExtra("user_id",   sharedPref.getString("user_id", null));
            intent.putExtra("nome",      sharedPref.getString("nome", null));
            intent.putExtra("sobrenome", sharedPref.getString("sobrenome", null));
            intent.putExtra("email",     sharedPref.getString("email", null));

            LoginActivity.this.startActivity(intent);
        }
    }

    /**
     * Ação do botão "Entrar". Pega os dados de login e os compara com a database.
     * Faz login do usuário caso a comparação seja bem-sucedida.
     * @param view Botão "Entrar"
     */
    public void login(View view){
        final EditText email =  (EditText)  findViewById(R.id.username);
        final EditText senha =  (EditText)  findViewById(R.id.password);
        final CheckBox permanecerConectado = (CheckBox)  findViewById(R.id.checkBox);

        if (email.getText().toString().equals("") || senha.getText().toString().equals("")){
            ToastMessage("E-mail/Senha incorretos");
            senha.setText(null);    // Apaga o campo "Senha"

        } else {
            mostraCarregamento(true); // Começa o Loading
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

                            if(PasswordAuthentication.check_password_hash(senha.getText().toString(), salt, hash)){
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
                                intent.putExtra("email", email.getText().toString());

                                /* Guarda os dados do usuario para entrar automaticamente */
                                if (permanecerConectado.isChecked()){
                                    SharedPreferences sharedPref = getSharedPreferences("logged_user", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("nome", nome);
                                    editor.putString("sobrenome", sobrenome);
                                    editor.putString("email", email.getText().toString());
                                    editor.putString("user_id", id);
                                    editor.putBoolean("keep_connected", true);
                                    editor.commit();
                                }

                                senha.setText(null);
                                LoginActivity.this.startActivity(intent);

                            } else {
                                /* EMAIL CADASTRADO, SENHA INCORRETA */
                                mostraCarregamento(false);
                                ToastMessage("E-mail/Senha incorretos");
                                senha.setText(null);
                            }
                        } else {
                            /* EMAIL NAO CADASTRADO */
                            mostraCarregamento(false);
                            ToastMessage("E-mail/Senha incorretos");
                            senha.setText(null);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            /* ENTRA NA DATABASE ONLINE */
            LoginRequest loginRequest = new LoginRequest(email.getText().toString(), responseListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest); // Executa as tarefas requisitadas
        }
    }

    /**
     * Muda o layout activity_login para uma tela de carregamento
     * @param show On/Off
     */
    private void mostraCarregamento(boolean show) {
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

    /**
     * Inicia a activity de registro de usuário.
     * @param view Botão "registrar"
     */
    public void RegisterAccount(View view){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }


    private void ToastMessage(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
