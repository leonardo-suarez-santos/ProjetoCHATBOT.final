package com.leonardo_soares_santos.chatbotlss;

import com.leonardo_soares_santos.chatbotlss.Activitys.LoginActivity;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginTest  {

    @Test
    public void testaCampoVazio() {

        LoginActivity l = new LoginActivity();
       boolean Resultado= l.isCampoVazio("");
        Assert.assertTrue(Resultado);

    }
    @Test
    public void testaCampoNaoVazio() {

        LoginActivity l = new LoginActivity();
        boolean Resultado= l.isCampoVazio("oi");
        Assert.assertFalse(Resultado);

    }
}
