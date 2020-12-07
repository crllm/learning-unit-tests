package com.example.learning.junit.entidades;

import lombok.Data;

@Data
public class Calculadora {

    public int somar(int a, int b) {
        return a + b;
    }

    public int subtracao(int a, int b) {
        return a - b;
    }

    public int divisao(int a, int b) {
        return a / b;
    }
}
