package com.leonardo_soares_santos.chatbotlss.Model;

/**
 * Created by Leonardo Soares on 02/05/18.
 * ra 816114026
 */
public class Chat {
    private boolean isImage, isMine;
    private String content;
    //CLASSE ONDE PASSA AS POSIÇOES DO CHAT
    public Chat(String message, boolean mine, boolean image) {
        content = message;
        isMine = mine;
        isImage = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setIsMine(boolean isMine) {
        this.isMine = isMine;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setIsImage(boolean isImage) {
        this.isImage = isImage;
    }
}