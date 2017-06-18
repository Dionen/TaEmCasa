package com.usp.icmc.taemcasa.MinhasVagas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.usp.icmc.taemcasa.MinhasVagas.MoradiaResponse.MoradiaRequest_ADD;
import com.usp.icmc.taemcasa.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AdicionarMoradia extends AppCompatActivity {

    private static final int RC_PHOTO_PICKER = 1;
    private String user_email;
    private AlertDialog alerta;
    private Bitmap imagem;

    private ImageButton adicionarFoto;
    private ImageButton removerFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_moradia);

        // Dados do usuário ativo
        Intent intent = getIntent();
        user_email = intent.getStringExtra("email");

        /* Botão de cadastrar moradia */
        Button adicionarMoradia = (Button) findViewById(R.id.adicionarMoradia);
        adicionarMoradia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checarDados(v)){
                    cadastrarMoradia(v);
                }
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
                        imagem = null;
                        removerFoto.setVisibility(View.GONE);
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
    }

    /**
     * Escolhe uma imagem da galeria
     */
    public void pickImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        photoPickerIntent.putExtra("crop", "true");
        photoPickerIntent.putExtra("aspectX", 0);
        photoPickerIntent.putExtra("aspectY", 0);
        photoPickerIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, false);
        startActivityForResult(Intent.createChooser(photoPickerIntent,"Complete Action Using"), RC_PHOTO_PICKER);
    }

    public String BitMapToString(Bitmap bitmap){
        // Redimensiona a imagem
        int maxHeight = 800;
        int maxWidth = 800;
        float scale = Math.min(((float)maxHeight / bitmap.getWidth()), ((float)maxWidth / bitmap.getHeight()));

        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        // Converte a imagem para string
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK && data != null) {
            Uri pickedImage = data.getData();
            //set the selected image to ImageView
            //mImageView.setImageURI(pickedImage);
            try {
                imagem = MediaStore.Images.Media.getBitmap(this.getContentResolver(), pickedImage);
                adicionarFoto.setImageBitmap(imagem);
                adicionarFoto.setAdjustViewBounds(true);

                /* O botão de remover foto aparece quando uma foto é escolhida */
                removerFoto.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
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
        String perfil;            // 0 = Apartamento, 1 = República
        String tipo;              // 0 = Calma, 1 = Agitada

        EditText titulo = (EditText) findViewById(R.id.nomeMoradia);
        EditText logradouro = (EditText) findViewById(R.id.logradouro);
        EditText numero = (EditText) findViewById(R.id.numero);
        EditText bairro = (EditText) findViewById(R.id.bairro);
        EditText complemento = (EditText) findViewById(R.id.complemento);
        EditText cidade = (EditText) findViewById(R.id.cidade);
        EditText estado = (EditText) findViewById(R.id.estado);
        RadioButton apartamento = (RadioButton) findViewById(R.id.apartamento);
        RadioButton republica = (RadioButton) findViewById(R.id.republica);
        RadioButton calma = (RadioButton) findViewById(R.id.calma);
        RadioButton agitada = (RadioButton) findViewById(R.id.agitada);
        EditText nMoradores = (EditText) findViewById(R.id.nMoradores);
        CheckBox aceitamAnimais = (CheckBox) findViewById(R.id.aceitamAnimais);
        EditText telefone = (EditText) findViewById(R.id.telefone);
        EditText descricao = (EditText) findViewById(R.id.descricao);

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Adicionando nova moradia...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        if (calma.isChecked()){
            tipo = "0";
        } else {
            tipo = "1";
        }
        if (apartamento.isChecked()){
            perfil = "0";
        } else {
            perfil = "1";
        }
        if (aceitamAnimais.isChecked()) animais = "1";

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    progress.dismiss();

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
        MoradiaRequest_ADD moradiaRequest = new MoradiaRequest_ADD(user_email, titulo.getText().toString(), descricao.getText().toString(), logradouro.getText().toString(), numero.getText().toString(),
                complemento.getText().toString(), bairro.getText().toString(), cidade.getText().toString(), estado.getText().toString(), telefone.getText().toString(),
                BitMapToString(imagem), tipo, perfil, nMoradores.getText().toString(), animais, responseListener);
        RequestQueue queue = Volley.newRequestQueue(AdicionarMoradia.this);
        queue.add(moradiaRequest);
    }

    /**
     * Checa se os campos necessários para o cadastro de moradia foram devidamente preenchidos.
     * @param view
     * @return
     */
    public boolean checarDados(View view){
        Context context = getApplicationContext();

        EditText titulo = (EditText) findViewById(R.id.nomeMoradia);

        if (titulo.getText().toString().isEmpty()){
            titulo.requestFocus();
            Toast.makeText(context, "Insira um título para a moradia!", Toast.LENGTH_SHORT).show();
            return false;
        }

        EditText logradouro = (EditText) findViewById(R.id.logradouro);
        EditText numero = (EditText) findViewById(R.id.numero);
        EditText bairro = (EditText) findViewById(R.id.bairro);
        EditText cidade = (EditText) findViewById(R.id.cidade);
        EditText estado = (EditText) findViewById(R.id.estado);

        if (logradouro.getText().toString().isEmpty()){
            Toast.makeText(context, "Insira o logradouro da residência", Toast.LENGTH_SHORT).show();
            logradouro.requestFocus();
            return false;
        } else if (numero.getText().toString().isEmpty()){
            Toast.makeText(context, "Insira o número da residência", Toast.LENGTH_SHORT).show();
            numero.requestFocus();
            return false;
        } else if (bairro.getText().toString().isEmpty()){
            Toast.makeText(context, "Insira o bairro da residência", Toast.LENGTH_SHORT).show();
            bairro.requestFocus();
            return false;
        } else if (cidade.getText().toString().isEmpty()){
            Toast.makeText(context, "Insira a cidade da residência", Toast.LENGTH_SHORT).show();
            cidade.requestFocus();
            return false;
        } else if (estado.getText().toString().isEmpty()){
            Toast.makeText(context, "Insira a sigla do estado da residência", Toast.LENGTH_SHORT).show();
            estado.requestFocus();
            return false;
        }

        RadioButton apartamento = (RadioButton) findViewById(R.id.apartamento);
        RadioButton republica = (RadioButton) findViewById(R.id.republica);

        if (apartamento.isChecked() == republica.isChecked()){
            Toast.makeText(context, "Escolha uma das opções", Toast.LENGTH_SHORT).show();
            apartamento.requestFocus();
            return false;
        }

        RadioGroup perfil = (RadioGroup) findViewById(R.id.tipo);

        if (perfil.getCheckedRadioButtonId() == -1) {
            Toast.makeText(context, "Escolha uma das opções", Toast.LENGTH_SHORT).show();
            perfil.requestFocus();
            return false;
        }

        EditText nMoradores = (EditText) findViewById(R.id.nMoradores);

        if (nMoradores.getText().toString().isEmpty()) {
            Toast.makeText(context, "Insira o número de moradores", Toast.LENGTH_SHORT).show();
            nMoradores.requestFocus();
            return false;
        }

        EditText telefone = (EditText) findViewById(R.id.telefone);

        if (telefone.getText().toString().isEmpty()) {
            Toast.makeText(context, "Insira um telefone para contato", Toast.LENGTH_SHORT).show();
            telefone.requestFocus();
            return false;
        }

        return true;
    }
}
