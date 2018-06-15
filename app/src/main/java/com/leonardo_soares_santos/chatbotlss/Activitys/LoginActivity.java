package com.leonardo_soares_santos.chatbotlss.Activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.leonardo_soares_santos.chatbotlss.Model.Usuario;
import com.leonardo_soares_santos.chatbotlss.R;
/**
 * Created by Leonardo Soares on 02/05/18.
 * ra 816114026
 */
public class LoginActivity extends AppCompatActivity {
    public static final String NOME = "com.leonardo_soares_santos.projetochatbot8bits.NOME";
    public static final String SOBRENOME = "com.leonardo_soares_santos.projetochatbot8bits.SOBRENOME";
    public static final String EMAIL = "com.leonardo_soares_santos.projetochatbot8bits.EMAIL";
    public static final String INTE = "com.leonardo_soares_santos.projetochatbot8bits.INTE";
    int y=10;
    String email,nome,sobrenome,primeiroInte;
    EditText txtnome, txtemail,txtsobrenome,txtsearch;
    Usuario usuario;


    //INSTANCIANDO O FIREBASE E PASSANDO REFERENCIA
    private DatabaseReference databaseReferencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuarioReferencia = databaseReferencia.child("usuario");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isConnected(LoginActivity.this)) buildDialog(LoginActivity.this).show();
        else {
            // Toast.makeText(LoginActivity.this,"Welcome", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_login);
        }
        txtnome = (EditText) findViewById(R.id.txt_nome);
        txtsobrenome= (EditText) findViewById(R.id.txt_sobrenome);
        txtemail = (EditText)findViewById(R.id.txt_email);

        if (!isConnected(LoginActivity.this)){

            Toast.makeText(LoginActivity.this,"Sem conexão com a internet", Toast.LENGTH_LONG).show();

        }






    }

    //EXECUTADO QUANDO O USUARIO PRECIONAR O BOTAO  ENVIAR NA TELA "LOGIN"
    public void salvarUsuario(View view) {

        Intent intent = new Intent(this, ChatActivity.class);
        nome = txtnome.getText().toString();
        sobrenome = txtsobrenome.getText().toString();
        email = txtemail.getText().toString();
        primeiroInte = "EAE CARAI";



        intent.putExtra(NOME, nome);
        intent.putExtra(SOBRENOME, sobrenome);
        intent.putExtra(EMAIL, email);
        intent.putExtra(INTE, primeiroInte);



        // validaCampos();
        //  startActivity(intent);

        nome = txtnome.getText().toString().trim();
        sobrenome = txtsobrenome.getText().toString().trim();
        email = txtemail.getText().toString().trim();
        primeiroInte ="EAE CARAI";

        if (isCampoVazio(nome) ){
            txtnome.requestFocus();
            txtnome.setError("Preencha o campo NOME!");

        }else
        if ( isCampoVazio(sobrenome) ){
            txtsobrenome.requestFocus();
            txtsobrenome.setError("Preencha o campo SOBRENOME!");

        }else
        if ( !isEmailValido(email)){
            txtemail.requestFocus();
            txtemail.setError("Email Nulo ou Invalido!");

        }
        else { //CASO O CAMPO USUARIO ESTEJA PREENCHIDO E O EMAIL SEJA VALIDO,OS MESMOS SERÃO CADASTRADOS NO FIREBASE

            usuario = new Usuario(usuarioReferencia.push().getKey(), nome, sobrenome,email);
            usuarioReferencia.child(usuario.getKey()).setValue(usuario);
            startActivity(intent);

            //  txtnome.setText("");
            //   txtemail.setText("");

        }

    }
    private void validaCampos(){
        String nome= txtnome.getText().toString();
        String email= txtemail.getText().toString();
        // Boolean res = false;
        if (isCampoVazio(nome)){
            txtnome.requestFocus();
            txtnome.setError("Preencha o campo nome!!");

        }else if (isCampoVazio(sobrenome)){
            txtsobrenome.requestFocus();
            txtsobrenome.setError("Preencha o campo Sobrenome!!");

        } else if ( !isEmailValido(email)){

            txtemail.requestFocus();
            txtemail.setError("Email nulo ou invalido!!");

        }


    }

    //VERIFICANDO SE O CAMPO ESTA VAZIO USANDO METODO PADRAO DO ANDROID
    public   boolean isCampoVazio(String valor){

        boolean resultado = (TextUtils.isEmpty(valor)|| valor.trim().isEmpty() );


        return resultado;

    }


    //VERIFICANDO SE O EMAIL ESTA EM FORMATO VALIDO USANDO METODO PADRAO DO ANDROID
    private boolean isEmailValido(String email ){
        boolean resultado = (!isCampoVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());

        return resultado;


    }

    //VERIFICANDO SE EXISTE CONEXÃO COM A INTERNET
    public boolean isConnected(Context context) {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }




    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("      SEM CONEXÃO COM A INTERNET");
        builder.setMessage("Você precisa de conexão com wifi ou dados moveis para acessar este aplicativo. \n    Precione Ok para sair");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }
    @Override
    public void onBackPressed() {
        vibrar();
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("     Você esta saindo");
        //define a mensagem
        builder.setMessage("  Tem certeza que quer fazer isso?");
        //define um botão como positivo
        builder.setIcon(R.drawable.botservice);
        //define um botão como negativo.
        builder.setNegativeButton("SIM", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });
        builder.setPositiveButton("NÃO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        //cria o AlertDialog
        AlertDialog alerta = builder.create();
        //Exibe
        alerta.show();
    }
    public void vibrar(){
        Vibrator rr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long mili = 100;
        rr.vibrate(mili);


    }
}
