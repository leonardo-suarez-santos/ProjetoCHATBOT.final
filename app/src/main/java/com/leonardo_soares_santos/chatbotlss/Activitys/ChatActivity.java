package com.leonardo_soares_santos.chatbotlss.Activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.leonardo_soares_santos.chatbotlss.Model.Chat;
import com.leonardo_soares_santos.chatbotlss.Model.ChatMessage;
import com.leonardo_soares_santos.chatbotlss.R;

import java.util.ArrayList;

import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;
/**
 * Created by Leonardo Soares on 02/05/18.
 * ra 816114026
 */
public class ChatActivity extends AppCompatActivity {
    public ListView listView;
    private ListView mListView;
    //RecyclerView recyclerView;
    EditText editText;
    ImageView addBtn;
    DatabaseReference ref;
    //FirebaseRecyclerAdapter<ChatMessage,chat_rec> adapter;
    Boolean flagFab = true;
    private ChatMessageAdapter mAdapter;


    private AIService aiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //PEGANDO OS DADOS DA INTENT DO LOGIN
        Intent intent = getIntent();
        String chaveNome = intent.getStringExtra(LoginActivity.NOME);
        String chaveSobrenome = intent.getStringExtra(LoginActivity.SOBRENOME);
        // Toast toast = Toast.makeText( this,chaveNome,Toast.LENGTH_LONG);
        //  toast.show();
        String chaveEmail = intent.getStringExtra(LoginActivity.EMAIL);
        String firstInte = intent.getStringExtra(LoginActivity.INTE);
        ///  EditText editText=(EditText) findViewById(R.id.editText2);
        TextView textView1=(TextView) findViewById(R.id.text);
        //TextView textView2=(TextView) findViewById(R.id.textView3);
        // TextView textView3=(TextView) findViewById(R.id.textView4);


        //  editText.setText("Nome :"+chaveNome+"   Email :"+chaveEmail);
        textView1.setText("Olá "+chaveNome+" "+chaveSobrenome+",use uma frase simples e objetiva para tirar suas dúvidas.");
        //textView2.setText("Use uma frase simples e objetiva para tirar suas dúvidas. Vamos lá!");
        // textView3.setText("Email: "+chaveEmail);


        if (!isConnected(ChatActivity.this)){

            Toast.makeText(ChatActivity.this,"Sem conexão com a internet", Toast.LENGTH_LONG).show();

        }


        // listView = (ListView) findViewById(R.id.listView);




        // ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},1);


        //recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        editText = (EditText)findViewById(R.id.et_message);
        addBtn = (ImageView) findViewById(R.id.iv_image2);
        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new ChatMessageAdapter(this, new ArrayList<Chat>());
        mListView.setAdapter(mAdapter);

        //  recyclerView.setHasFixedSize(true);
        //  final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        // linearLayoutManager.setStackFromEnd(true);
        // recyclerView.setLayoutManager(linearLayoutManager);

        //PEGANDO INSTACIA DO BD
        ref = FirebaseDatabase.getInstance().getReference();
        ref.keepSynced(true);


        //PASSANDO A CHAVE DO DIALOGFLOW (OBS:GERADA POR PROJETO)
        final AIConfiguration config = new AIConfiguration("c1d572f3bf624b03923a1ad2fbd9dfa3",
                AIConfiguration.SupportedLanguages.PortugueseBrazil,
                AIConfiguration.RecognitionEngine.System);


        final AIDataService aiDataService = new AIDataService(config);

        final AIRequest aiRequest = new AIRequest();


//METODO PARA QUANDO O BOTAO DA MSG FOR CLICADO
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //PEGANDO VALOR DO CAMPO QUE RECEBERA A MSG DO USUARIO E PREENCHENDO A LISTVIEW
                String message = editText.getText().toString().trim();
                Chat chat = new Chat(message, true, false);
                mAdapter.add(chat);

//PEGANDO OS DADOS DO DIALOGFLOW E JOGANDO NO BD
                if (!message.equals("")) {

                    ChatMessage chatMessage = new ChatMessage(message, "Usuario");
                    ref.child("chatBot").push().setValue(chatMessage);

                    aiRequest.setQuery(message);
                    new AsyncTask<AIRequest,Void,AIResponse>(){

                        @Override
                        protected AIResponse doInBackground(AIRequest... aiRequests) {
                            final AIRequest request = aiRequests[0];
                            try {
                                final AIResponse response = aiDataService.request(aiRequest);
                                return response;
                            } catch (AIServiceException e) {
                            }
                            return null;
                        }
                        @Override
                        protected void onPostExecute(AIResponse response) {
                            if (response != null) {

                                Result result = response.getResult();

                                String reply = result.getFulfillment().getSpeech();

                                //PASSANDO A RESPOSTA QUEM VEM DIALOGFLOW PARA O BD E ARMAZENADO NA VARIAVEL REPLY E PASSANDO PARA O METODO MIMIC
                                mimicOtherMessage(reply);
                                ChatMessage chatMessage = new ChatMessage(reply, "ChatBot");
                                ref.child("chatBot").push().setValue(chatMessage);
                                editText.requestFocus();
                            }
                        }
                    }.execute(aiRequest);
                }
                else {
                    aiService.startListening();
                }

                editText.setText("");

            }
        });

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


    //METODOS PARA POPULAR O LISTVIEW
    private void mimicOtherMessage(String message) {
        Chat chat = new Chat(message, false, false);
        mAdapter.add(chat);
    }
    private void sendMessage() {
        Chat chat = new Chat(null, true, true);
        mAdapter.add(chat);

        mimicOtherMessage();
    }
    private void mimicOtherMessage() {
        Chat chat = new Chat(null, false, true);
        mAdapter.add(chat);
    }
    public boolean isConnected(Context context) {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }
    public void vibrar(){
        Vibrator rr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long mili = 100;
        rr.vibrate(mili);


    }
}