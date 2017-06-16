package com.usp.icmc.taemcasa.MinhasVagas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.usp.icmc.taemcasa.MinhasVagas.MoradiaResponse.MoradiaRequest_INSERT;
import com.usp.icmc.taemcasa.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AdicionarMoradia extends AppCompatActivity {

    private String user_id;
    private String user_nome;
    private String user_sobrenome;
    private String user_email;
    private AlertDialog alerta;
    private Bitmap newProfilePic;
    private int porcentagemProgresso;

    private ImageButton adicionarFoto;
    private ImageButton removerFoto;

    public void atualizarLayout(View view){
        Context context = getApplicationContext();
        TextView title = (TextView) findViewById(R.id.statusProgresso);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Button adicionarMoradia = (Button)  findViewById(R.id.adicionarMoradia);
        Toast toast;

        if (porcentagemProgresso == 0){
            EditText titulo = (EditText) findViewById(R.id.nomeMoradia);
            if (titulo.getText().toString().isEmpty()){
                titulo.requestFocus();
                toast = Toast.makeText(context, "Insira um título para a moradia!", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                findViewById(R.id.endereco).setVisibility(View.VISIBLE);
                findViewById(R.id.endereco).requestFocus();
                findViewById(R.id.enderecoTitulo).setVisibility(View.VISIBLE);

                title.setText("10% COMPLETO");
                progressBar.setProgress(10);

                porcentagemProgresso++;
            }
        } else if (porcentagemProgresso == 1){
            EditText logradouro = (EditText) findViewById(R.id.logradouro);
            EditText numero = (EditText) findViewById(R.id.numero);
            EditText bairro = (EditText) findViewById(R.id.bairro);
            EditText cidade = (EditText) findViewById(R.id.cidade);
            EditText estado = (EditText) findViewById(R.id.estado);

            if (logradouro.getText().toString().isEmpty()){
                toast = Toast.makeText(context, "Insira o logradouro da residência", Toast.LENGTH_SHORT);
                toast.show();
                logradouro.requestFocus();
            } else if (numero.getText().toString().isEmpty()){
                toast = Toast.makeText(context, "Insira o número da residência", Toast.LENGTH_SHORT);
                toast.show();
                numero.requestFocus();
            } else if (bairro.getText().toString().isEmpty()){
                toast = Toast.makeText(context, "Insira o bairro da residência", Toast.LENGTH_SHORT);
                toast.show();
                bairro.requestFocus();
            } else if (cidade.getText().toString().isEmpty()){
                toast = Toast.makeText(context, "Insira a cidade da residência", Toast.LENGTH_SHORT);
                toast.show();
                cidade.requestFocus();
            } else if (estado.getText().toString().isEmpty()){
                toast = Toast.makeText(context, "Insira a sigla do estado da residência", Toast.LENGTH_SHORT);
                toast.show();
                estado.requestFocus();
            } else {
                findViewById(R.id.perfilTitulo).setVisibility(View.VISIBLE);
                findViewById(R.id.perfil).setVisibility(View.VISIBLE);
                findViewById(R.id.perfil).requestFocus();

                title.setText("40% COMPLETO");
                progressBar.setProgress(40);

                porcentagemProgresso++;
            }
        } else if (porcentagemProgresso == 2){
            RadioButton apartamento = (RadioButton) findViewById(R.id.apartamento);
            RadioButton republica = (RadioButton) findViewById(R.id.republica);

            if (apartamento.isChecked() == republica.isChecked()){
                toast = Toast.makeText(context, "Escolha uma das opções", Toast.LENGTH_SHORT);
                toast.show();
                apartamento.requestFocus();
            } else {
                findViewById(R.id.tipoTitulo).setVisibility(View.VISIBLE);
                findViewById(R.id.tipo).setVisibility(View.VISIBLE);
                findViewById(R.id.tipo).requestFocus();

                title.setText("60% COMPLETO");
                progressBar.setProgress(60);

                porcentagemProgresso++;
            }
        } else if (porcentagemProgresso == 3) {
            RadioButton masculina = (RadioButton) findViewById(R.id.masculina);
            RadioButton feminina = (RadioButton) findViewById(R.id.feminina);
            RadioButton mista = (RadioButton) findViewById(R.id.mista);

            if (!masculina.isChecked() && !feminina.isChecked() && !mista.isChecked()) {
                toast = Toast.makeText(context, "Escolha uma das opções", Toast.LENGTH_SHORT);
                toast.show();
                masculina.requestFocus();
            } else {
                findViewById(R.id.dadosMoradores).setVisibility(View.VISIBLE);
                findViewById(R.id.dadosMoradoresTitulo).setVisibility(View.VISIBLE);
                findViewById(R.id.dadosMoradores).requestFocus();

                title.setText("75% COMPLETO");
                progressBar.setProgress(75);

                porcentagemProgresso++;
            }
        } else if (porcentagemProgresso == 4) {
            EditText nMoradores = (EditText) findViewById(R.id.nMoradores);

            if (nMoradores.getText().toString().isEmpty()) {
                toast = Toast.makeText(context, "Insira o número de moradores", Toast.LENGTH_SHORT);
                toast.show();
                nMoradores.requestFocus();
            } else {
                findViewById(R.id.telefoneTitulo).setVisibility(View.VISIBLE);
                findViewById(R.id.telefoneLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.telefoneLayout).requestFocus();

                title.setText("90% COMPLETO");
                progressBar.setProgress(90);

                porcentagemProgresso++;
            }
        } else if (porcentagemProgresso == 5) {
            EditText telefone = (EditText) findViewById(R.id.telefone);

            if (telefone.getText().toString().isEmpty()) {
                telefone.requestFocus();
                toast = Toast.makeText(context, "Insira um telefone para contato", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                findViewById(R.id.descricaoLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.descricaoTitulo).setVisibility(View.VISIBLE);
                findViewById(R.id.descricaoLayout).requestFocus();

                title.setText("100% COMPLETO");
                progressBar.setProgress(100);

                adicionarMoradia.setText("ADICIONAR MORADIA");
                porcentagemProgresso++;
            }
        } else {
            cadastrarMoradia(view);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_moradia);

        porcentagemProgresso = 0;

        // Dados do usuário ativo
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        user_nome = intent.getStringExtra("nome");
        user_sobrenome = intent.getStringExtra("sobrenome");
        user_email = intent.getStringExtra("email");

        /* Botão de cadastrar moradia */
        Button adicionarMoradia = (Button) findViewById(R.id.adicionarMoradia);
        adicionarMoradia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizarLayout(v);
            }
        });

        /* Botão de adicionar foto */
        adicionarFoto = (ImageButton) findViewById(R.id.adicionarFoto);
        adicionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });

        /* Botão de remover a foto escolhida */
        removerFoto = (ImageButton) findViewById(R.id.removerFoto);
        removerFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdicionarMoradia.this);
                builder.setTitle("Remover foto da Moradia");
                builder.setMessage("Deseja não usar esta foto?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1){
                        adicionarFoto.setAdjustViewBounds(false);
                        adicionarFoto.setImageResource(R.drawable.ic_menu_camera);
                        newProfilePic = null;
                        removerFoto.setVisibility(View.GONE);
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alerta = builder.create();
                alerta.show();
            }
        });
    }

    /**
     * Escolhe uma imagem da galeria
     */
    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 1);
    }

    /**
     * Recebe a imagem da galeria
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 1) {
            final Bundle extras = data.getExtras();
            if (extras != null) {
                //Get image
                newProfilePic = extras.getParcelable("data");
                if (newProfilePic != null) {
                    adicionarFoto.setImageBitmap(newProfilePic);
                    adicionarFoto.setAdjustViewBounds(true);

                    /* O botão de remover foto aparece quando uma foto é escolhida */
                    removerFoto.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * Coleta os dados do formulário de registro de uma moradia e os insere na database.
     * Ainda não checa se todos os dados foram inseridos
     * @param view
     */
    private void cadastrarMoradia(View view){
        final Context context = getApplicationContext();
        String animais = "0";     // 0 = Não aceita animais, 1 = aceita animais
        String perfil = "-1";      // 0 = Apartamento, 1 = República
        String tipo = "-1";        // 0 = Masculina, 1 = Feminina, 2 = Unissex

        EditText titulo = (EditText) findViewById(R.id.nomeMoradia);
        EditText logradouro = (EditText) findViewById(R.id.logradouro);
        EditText numero = (EditText) findViewById(R.id.numero);
        EditText bairro = (EditText) findViewById(R.id.bairro);
        EditText complemento = (EditText) findViewById(R.id.complemento);
        EditText cidade = (EditText) findViewById(R.id.cidade);
        EditText estado = (EditText) findViewById(R.id.estado);
        RadioButton apartamento = (RadioButton) findViewById(R.id.apartamento);
        RadioButton republica = (RadioButton) findViewById(R.id.republica);
        RadioButton masculina = (RadioButton) findViewById(R.id.masculina);
        RadioButton feminina = (RadioButton) findViewById(R.id.feminina);
        RadioButton mista = (RadioButton) findViewById(R.id.mista);
        EditText nMoradores = (EditText) findViewById(R.id.nMoradores);
        CheckBox aceitamAnimais = (CheckBox) findViewById(R.id.aceitamAnimais);
        EditText telefone = (EditText) findViewById(R.id.telefone);
        EditText descricao = (EditText) findViewById(R.id.descricao);

        if (masculina.isChecked()){
            tipo = "0";
        } else if (feminina.isChecked()){
            tipo = "1";
        } else if (mista.isChecked()){
            tipo = "2";
        }
        if (apartamento.isChecked()){
            perfil = "0";
        } else if (republica.isChecked()){
            perfil = "1";
        }
        if (aceitamAnimais.isChecked()) animais = "1";

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success){
                                // MORADIA CRIADA
                        Toast toast = Toast.makeText(context, "Moradia cadastrada com sucesso", Toast.LENGTH_SHORT);
                        toast.show();
                        finish();
                    } else {
                        Toast toast = Toast.makeText(context, "Cadastro não autorizado", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } catch (JSONException e) {
                            // PROBLEMAS
                    e.printStackTrace();
                    Toast toast = Toast.makeText(context, "Erro ao acessar database", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        };
        MoradiaRequest_INSERT moradiaRequest = new MoradiaRequest_INSERT(user_email, titulo.getText().toString(), descricao.getText().toString(), logradouro.getText().toString(), numero.getText().toString(),
                complemento.getText().toString(), bairro.getText().toString(), cidade.getText().toString(), estado.getText().toString(), telefone.getText().toString(),
                tipo, perfil, nMoradores.getText().toString(), animais, responseListener);
        RequestQueue queue = Volley.newRequestQueue(AdicionarMoradia.this);
        queue.add(moradiaRequest);

    }
}
