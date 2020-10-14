package com.example.learning.junit.services;

import com.example.learning.junit.entidades.Filme;
import com.example.learning.junit.entidades.Locacao;
import com.example.learning.junit.entidades.Usuario;
import com.example.learning.junit.exceptions.FilmeSemEstoqueException;
import com.example.learning.junit.exceptions.LocadoraException;
import com.example.learning.junit.servicos.LocacaoService;
import com.example.learning.junit.utils.DataUtils;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.learning.junit.utils.DataUtils.isMesmaData;
import static com.example.learning.junit.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LocacaoServiceTest {

    private LocacaoService service;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void executeAntes() {
        service = new LocacaoService();
    }

    @Test
    public void alugaFilmes() throws FilmeSemEstoqueException, LocadoraException {
        Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        Usuario usuario = new Usuario("Carol");
        List<Filme> filmes = Arrays.asList(new Filme("O Brilho eterno de uma mente sem lembraças", 5, 15.62));

        Locacao locacao = service.alugarFilme(usuario, filmes);
        locacao.getFilme().forEach(filme -> errorCollector.checkThat(filme.getNome(), is(equalTo("O Brilho eterno de uma mente sem lembraças"))));
        errorCollector.checkThat(locacao.getUsuario().getNome(), is(equalTo("Carol")));
        errorCollector.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        errorCollector.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
        errorCollector.checkThat(locacao.getValor(), is(equalTo(15.62)));
    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void alugarFilmesSemEstoque() throws Exception {
        Usuario usuario = new Usuario("Carol");
        List<Filme> filmes = Arrays.asList(new Filme("O Brilho eterno de uma mente sem lembraças", 0, 15.62));
        service.alugarFilme(usuario, filmes);

    }

    @Test
    public void usuarioVazio() throws FilmeSemEstoqueException {
        LocacaoService service = new LocacaoService();
        List<Filme> filmes = Arrays.asList(new Filme("O Brilho eterno de uma mente sem lembraças", 5, 15.62));
        try {
            service.alugarFilme(null, filmes);
            Assert.fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is("Informações de usuário está vazio"));
        }
    }

    @Test
    public void filmeVazio() throws FilmeSemEstoqueException, LocadoraException {
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Carol");

        exception.expect(LocadoraException.class);
        exception.expectMessage("Informações de filme estão vazios");
        service.alugarFilme(usuario, null);
    }

    @Test
    public void descontoNo3Filme() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Carol");
        List<Filme> filmes = Arrays.asList(new Filme("O Brilho eterno de uma mente sem lembraças", 5, 4.0),
                new Filme("Origem", 3, 4.0),
                new Filme("Toy Story", 2, 4.0));

        Locacao locacao = service.alugarFilme(usuario, filmes);
        assertThat(locacao.getValor(), is(equalTo(11.0)));
    }

    @Test
    public void dscontoNo4Filme() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Carol");
        List<Filme> filmes = Arrays.asList(new Filme("O Brilho eterno de uma mente sem lembraças", 5, 4.0),
                new Filme("Origem", 3, 4.0),
                new Filme("Toy Story", 2, 4.0),
                new Filme("A Era do Gelo", 5, 4.0));

        Locacao locacao = service.alugarFilme(usuario, filmes);
        assertThat(locacao.getValor(), is(equalTo(13.0)));
    }

    @Test
    public void descontoNo5Filme() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Carol");
        List<Filme> filmes = Arrays.asList(new Filme("O Brilho eterno de uma mente sem lembraças", 5, 4.0),
                new Filme("Origem", 3, 4.0),
                new Filme("Toy Story", 2, 4.0),
                new Filme("A Era do Gelo", 5, 4.0),
                new Filme("Entre Facas e Segredos", 2, 4.0));

        Locacao locacao = service.alugarFilme(usuario, filmes);
        assertThat(locacao.getValor(), is(equalTo(14.0)));
    }

    @Test
    public void dscontoNo6Filme() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Carol");
        List<Filme> filmes = Arrays.asList(new Filme("O Brilho eterno de uma mente sem lembraças", 5, 4.0),
                new Filme("Origem", 3, 4.0),
                new Filme("Toy Story", 2, 4.0),
                new Filme("A Era do Gelo", 5, 4.0),
                new Filme("Entre Facas e Segredos", 2, 4.0),
                new Filme("Orgulho e Preconceito", 4, 4.0));

        Locacao locacao = service.alugarFilme(usuario, filmes);
        assertThat(locacao.getValor(), is(equalTo(14.0)));
    }

    @Test
    public void testeEntregaNoDomingo() throws FilmeSemEstoqueException, LocadoraException {
        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        Usuario usuario = new Usuario("Carol");
        List<Filme> filmes = Arrays.asList(new Filme("O Brilho eterno de uma mente sem lembraças", 5, 15.62));

        Locacao locacao = service.alugarFilme(usuario, filmes);
        boolean segunda = DataUtils.verificarDiaSemana(locacao.getDataRetorno(), Calendar.MONDAY);
        Assert.assertTrue(segunda);
    }
}
