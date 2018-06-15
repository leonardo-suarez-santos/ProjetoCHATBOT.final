package com.leonardo_soares_santos.chatbotlss.Model;
/**
 * Created by Leonardo Soares on 02/05/18.
 * ra 816114026
 */
public class Usuario {

    String key;

    String nome;
    String sobrenome;
    String email;

    //CLASSE DO USUARIO ,QUE SERA ARMAZENADO NO FIREBASE
    public Usuario(String key, String name,String sobrenome ,String email) {
        this.key = key;
        this.nome = name;
        this.sobrenome = sobrenome;
        this.email = email;
    }

    public Usuario() {
    }



    public String getKey() {
        return key;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }


    public String getEmail() {
        return email;
    }
}