package com.example.learning.junit.builders;

import com.example.learning.junit.entidades.Filme;
import com.example.learning.junit.entidades.Locacao;
import com.example.learning.junit.entidades.Usuario;

import java.util.Date;

import static com.example.learning.junit.builders.FilmeBuilder.umFilme;
import static com.example.learning.junit.builders.UsuarioBuilder.umUsuario;
import static com.example.learning.junit.utils.DataUtils.obterDataComDiferencaDias;
import static java.util.Arrays.asList;

public class LocacaoBuilder {

    private Locacao elemento;

    private LocacaoBuilder() {
    }

    public static LocacaoBuilder umLocacao() {
        LocacaoBuilder builder = new LocacaoBuilder();
        inicializarDadosPadroes(builder);
        return builder;
    }

    public static void inicializarDadosPadroes(LocacaoBuilder builder) {
        builder.elemento = new Locacao();
        Locacao elemento = builder.elemento;


        elemento.setUsuario(umUsuario().agora());
        elemento.setFilmes(asList(umFilme().agora()));
        elemento.setDataLocacao(new Date());
        elemento.setDataRetorno(obterDataComDiferencaDias(1));
        elemento.setValor(4.0);
    }

    public LocacaoBuilder comUsuario(Usuario param) {
        elemento.setUsuario(param);
        return this;
    }

    public LocacaoBuilder comListaFilmes(Filme... params) {
        elemento.setFilmes(asList(params));
        return this;
    }

    public LocacaoBuilder comDataLocacao(Date param) {
        elemento.setDataLocacao(param);
        return this;
    }

    public LocacaoBuilder comDataRetorno(Date param) {
        elemento.setDataRetorno(param);
        return this;
    }

    public LocacaoBuilder comValor(Double param) {
        elemento.setValor(param);
        return this;
    }

    public Locacao agora() {
        return elemento;
    }

    public LocacaoBuilder atrasado() {
        elemento.setDataLocacao(obterDataComDiferencaDias(-4));
        elemento.setDataRetorno(obterDataComDiferencaDias(-2));
        return this;
    }
}