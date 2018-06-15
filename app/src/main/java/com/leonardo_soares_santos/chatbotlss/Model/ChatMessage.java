package com.leonardo_soares_santos.chatbotlss.Model;

/**
 * Created by Leonardo Soares on 02/05/18.
 * ra 816114026
 */
public class ChatMessage {

    private String msgText;
    private String msgUser;


    //CLASSE ONDE PASSA OS ATRIBUTOS PARA ARMAZENAR OS DADOS DO DIALOGFLOW
    public ChatMessage(String msgText, String msgUser){
        this.msgText = msgText;
        this.msgUser = msgUser;

    }


    public ChatMessage(){

    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    public String getMsgUser() {
        return msgUser;
    }

    public void setMsgUser(String msgUser) {
        this.msgUser = msgUser;
    }
}