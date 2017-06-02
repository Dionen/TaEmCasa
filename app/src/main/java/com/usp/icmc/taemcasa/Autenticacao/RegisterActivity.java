package com.usp.icmc.taemcasa.Autenticacao;

import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.usp.icmc.taemcasa.MainActivity;
import com.usp.icmc.taemcasa.R;
import com.usp.icmc.taemcasa.Autenticacao.loginRequest.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by Joao on 15/05/2017.
 * Operações da tela de registro.
 */

public class RegisterActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void BotaoCadastrarUsuario(View view){
        final Context context = getApplicationContext();

        TextView name = (TextView) findViewById(R.id.name_register);
        TextView sobrenome = (TextView) findViewById(R.id.sobrenome_register);
        TextView email = (TextView) findViewById(R.id.email_register);
        TextView senha = (TextView) findViewById(R.id.password_register);
        String name_s = name.getText().toString();
        String sobrenome_s = sobrenome.getText().toString();
        String email_s = email.getText().toString();
        String password_s = senha.getText().toString();

        /* Se as credenciais estiverem de acordo */
        if(ValidarCredenciaisUsuario()){
            try {
                showProgress(true);

                /* Gera Salt/Hash */
                String[] SaltHash = PasswordAuthentication.create_salt_hash(password_s);
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success){
                                /* USUARIO CRIADO */
                                Toast toast = Toast.makeText(context, "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT);
                                toast.show();
                                finish();

                            } else {
                                Toast toast = Toast.makeText(context, "Cadastro sem sucesso", Toast.LENGTH_SHORT);
                                toast.show();
                                showProgress(false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast toast = Toast.makeText(context, "Erro ao acessar database", Toast.LENGTH_SHORT);
                            toast.show();
                            showProgress(false);
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(name_s, sobrenome_s, email_s, SaltHash[0], SaltHash[1], responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
                Toast toast = Toast.makeText(context, "Erro no cadastro", Toast.LENGTH_SHORT);
                toast.show();
                showProgress(false);
            }
        }
    }

    public boolean ValidarCredenciaisUsuario() {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        TextView name = (TextView) findViewById(R.id.name_register);
        TextView email = (TextView) findViewById(R.id.email_register);
        TextView senha = (TextView) findViewById(R.id.password_register);
        TextView csenha = (TextView) findViewById(R.id.cpassword_register);
        String name_s = name.getText().toString();
        String email_s = email.getText().toString();
        String password_s = senha.getText().toString();
        String cpassword_s = csenha.getText().toString();

        /* CHECAR EMAIL SE JÁ ESTA CADASTRADO */

        if (name_s.isEmpty()){
            Toast toast = Toast.makeText(context, "Nome inválido", duration);
            toast.show();
            return false;

        } else if (email_s.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email_s).matches()) {
            Toast toast = Toast.makeText(context, "Endereço de e-mail inválido", duration);
            toast.show();
            return false;

        } else if (password_s.isEmpty() || password_s.length() < 4 || password_s.length() > 16) {
            Toast toast = Toast.makeText(context, "Senha deve possuir entre de 4 e 16 digitos", duration);
            toast.show();
            senha.setText(null);
            csenha.setText(null);
            return false;

        } else if (!password_s.equals(cpassword_s)){
            Toast toast = Toast.makeText(context, "Confirmação de senha incorreta", duration);
            toast.show();
            senha.setText(null);
            csenha.setText(null);
            return false;

        } else {
            return true;
        }
    }

    private void showProgress(final boolean show) {
        final View mLoginFormView1 = findViewById(R.id.layout1);
        final View mLoginFormView2 = findViewById(R.id.layout2);
        final View mLoginFormView3 = findViewById(R.id.layout3);
        final View mLoginFormView5 = findViewById(R.id.layout5);
        final View mLoginFormView6 = findViewById(R.id.layout6);
        final View mTextCon = findViewById(R.id.textConectando);
        final View mProgressView = findViewById(R.id.progressBarRegister);
        final View mPassword = findViewById(R.id.cpassword_register);

        mLoginFormView1.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        mLoginFormView2.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        mLoginFormView3.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        mLoginFormView5.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        mLoginFormView6.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        mPassword.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mTextCon.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void CloseActivity(View view){
        finish();
    }
}
