package com.example.learning.junit.builders;

import com.example.learning.junit.entidades.Filme;

public class FilmeBuilder {

    private Filme filme;

    private FilmeBuilder() {

    }

    public static FilmeBuilder umFilme() {
        FilmeBuilder filmeBuilder = new FilmeBuilder();
        filmeBuilder.filme = new Filme();
        filmeBuilder.filme.setNome("O Brilho eterno de uma mente sem lembraças");
        filmeBuilder.filme.setEstoque(5);
        filmeBuilder.filme.setPrecoLocacao(4.0);
        return filmeBuilder;
    }

    public Filme agora() {
        return filme;
    }

    public FilmeBuilder semEstoque() {
        filme.setEstoque(0);
        return this;
    }
}
