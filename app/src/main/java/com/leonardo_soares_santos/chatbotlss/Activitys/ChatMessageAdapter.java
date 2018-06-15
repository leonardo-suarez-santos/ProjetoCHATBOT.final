package com.leonardo_soares_santos.chatbotlss.Activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.leonardo_soares_santos.chatbotlss.Model.Chat;
import com.leonardo_soares_santos.chatbotlss.R;

import java.util.List;

/**
 * Created by Leonardo Soares on 02/05/18.
 * ra 816114026
 */


//CLASSE PARA ORDENAR AS MSGS EM POSIÇÃO DE CHAT
public class ChatMessageAdapter extends ArrayAdapter<Chat> {
    boolean clicado =false;
    private AlertDialog alerta;
    static int contador = 0;
    private static final int MY_MESSAGE = 0, OTHER_MESSAGE = 1,MY_IMAGE = 2, OTHER_IMAGE = 3;;
    public ChatMessageAdapter(Context context, List<Chat> data) {
        super(context, R.layout.item_mine_message, data);

    }

    @Override
    public int getItemViewType(int position) {
        Chat item = getItem(position);
        if (item.isMine() && !item.isImage()) return MY_MESSAGE;
        else if (!item.isMine() && !item.isImage()) return OTHER_MESSAGE;
        else if (item.isMine() && item.isImage()) return MY_IMAGE;
        else return OTHER_IMAGE;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        if (viewType == MY_MESSAGE) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_mine_message, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.text);
            textView.setText(getItem(position).getContent());
        } else if (viewType == OTHER_MESSAGE) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_other_message, parent, false);
            final TextView textView = (TextView) convertView.findViewById(R.id.text);
            final TextView textView2 = (TextView) convertView.findViewById(R.id.text2);
            Button textView3 = (Button) convertView.findViewById(R.id.text3);
           // final TextView textView8 = (TextView) convertView.findViewById(R.id.text8);
            final Button textView4 = (Button) convertView.findViewById(R.id.text4);


            final String t ="A informação apresentada foi útil?";
            String y ="SIM";
            String n ="NÃO";
            final String resolvido ="Fico feliz de ajudar,volte quando precisar";
            final String nResolvido ="Reformule a sua pergunta por favor";
            final String contato ="Por favor \nPara mais informações\nEntre em contato pelo numero 40028922 \nOu email simplanteste@teste.com";
            textView.setText(getItem(position).getContent());
            textView2.setText(t);
            //textView3.setText(y);

            // textView4.setText(n);

            textView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Muito Obrigado pela sua visita.", Toast.LENGTH_LONG).show();
                  //  textView8.setText(resolvido);
                    resolvido();


                }
            });

            textView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(getContext(), "Reformule a sua pergunta por favor", Toast.LENGTH_LONG).show();
                   // textView8.setText(nResolvido);
                    Toast.makeText(getContext(), "Reformule a sua pergunta por favor", Toast.LENGTH_LONG).show();


                    clicado = true;


                    if (clicado) {

                        contador =contador + 1;
                        //  Toast.makeText(getContext(), "clicado." + contador + "vezez", Toast.LENGTH_LONG).show();
                    }

                    if (contador >= 3){

                        // Toast.makeText(getContext(), "clicado." + contador + "FODASE", Toast.LENGTH_LONG).show();
                       // textView8.setText(contato);

                        contador =0;

                        naoResolvido();



                    }



                    // System.out.println("Clicado "+cont+" vezes");



                }




            });


            // String t = textView.toString();



        } else if (viewType == MY_IMAGE) {
            //convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_mine_image, parent, false);
        } else {
            // convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_other_image, parent, false);
        }
        convertView.findViewById(R.id.chatMessageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "onClick", Toast.LENGTH_LONG).show();

            }
        });
        return convertView;

    }

    private  boolean isCampoVazio(String valor){

        boolean resultado = (TextUtils.isEmpty(valor)|| valor.trim().isEmpty() );


        return resultado;



    }


    private void naoResolvido() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //define o titulo
        builder.setTitle("Desculpe pelo transtorno");
        //define a mensagem
        builder.setMessage("Por favor, \nPara mais informações,\nEntre em contato pelo nosso numero: (11)4002-8922. \nOu se preferir,pelo nosso email: \nsimplanteste@teste.com");
        //define um botão como positivo
        builder.setIcon(R.drawable.botservice);
        builder.setNeutralButton("                              OBRIGADO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //define um botão como negativo.

        //Exibe
        alerta = builder.create();
        alerta.show();
    }

    private void resolvido() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //define o titulo
        builder.setTitle("        Que bom!");
        //define a mensagem
        builder.setMessage("           Fico feliz de poder ajudar,\n           Volte sempre que precisar.");
        //define um botão como positivo
        builder.setIcon(R.drawable.botservice);
        builder.setNeutralButton("           Obrigado pela sua visita", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //define um botão como negativo.

        //Exibe
        alerta = builder.create();
        alerta.show();
    }



}