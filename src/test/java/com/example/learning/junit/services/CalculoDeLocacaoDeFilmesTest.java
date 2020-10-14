package com.example.learning.junit.services;

import com.example.learning.junit.entidades.Filme;
import com.example.learning.junit.entidades.Locacao;
import com.example.learning.junit.entidades.Usuario;
import com.example.learning.junit.exceptions.FilmeSemEstoqueException;
import com.example.learning.junit.exceptions.LocadoraException;
import com.example.learning.junit.servicos.LocacaoService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalculoDeLocacaoDeFilmesTest {

    private LocacaoService service;

    private List<Filme> filmes;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void executeAntes() {
        service = new LocacaoService();
    }

    @Test
    public void calculoDeLocacaoDeFilmesComDesconto() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Carol");


        Locacao locacao = service.alugarFilme(usuario, filmes);
        assertThat(locacao.getValor(), is(equalTo(14.0)));
    }

}
