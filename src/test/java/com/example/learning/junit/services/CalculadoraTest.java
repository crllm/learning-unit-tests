package com.example.learning.junit.services;

import com.example.learning.junit.entidades.Calculadora;
import org.junit.Assert;
import org.junit.Test;

public class CalculadoraTest {

    public Calculadora calculadora = new Calculadora();

    @Test
    public void somaDeDoisValores() {
        int a = 5;
        int b = 3;
        int resultado = 0;

        resultado += calculadora.somar(a, b);

        Assert.assertEquals(8, resultado);

    }

    @Test
    public void subtracaoDeDoisValores() {
        int a = 12;
        int b = 8;
        int resultado = 0;

        resultado += calculadora.subtracao(a, b);

        Assert.assertEquals(4, resultado);
    }

    @Test
    public void divisaoDeDoisValores() {
        int a = 8;
        int b = 2;
        int resultado = 0;

        resultado += calculadora.divisao(a, b);

        Assert.assertEquals(4, resultado);
    }
}
