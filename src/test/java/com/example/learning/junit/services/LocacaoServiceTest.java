package com.example.learning.junit.services;

import com.example.learning.junit.entidades.Filme;
import com.example.learning.junit.entidades.Locacao;
import com.example.learning.junit.entidades.Usuario;
import com.example.learning.junit.exceptions.FilmeSemEstoqueException;
import com.example.learning.junit.exceptions.LocadoraException;
import com.example.learning.junit.servicos.LocacaoService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static com.example.learning.junit.utils.DataUtils.isMesmaData;
import static com.example.learning.junit.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class LocacaoServiceTest {

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testeAlugaFilmes() throws FilmeSemEstoqueException, LocadoraException {
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Carol");
        Filme filme = new Filme("O Brilho eterno de uma mente sem lembraças", 5, 15.62);

        Locacao locacao = service.alugarFilme(usuario, filme);
        errorCollector.checkThat(locacao.getFilme().getNome(), is(equalTo("O Brilho eterno de uma mente sem lembraças")));
        errorCollector.checkThat(locacao.getUsuario().getNome(), is(equalTo("Carol")));
        errorCollector.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        errorCollector.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
        errorCollector.checkThat(locacao.getValor(), is(equalTo(15.62)));
    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void testeAlugarFilmesSemEstoque() throws Exception {
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Carol");
        Filme filme = new Filme("O Brilho eterno de uma mente sem lembraças", 0, 15.62);

        Locacao locacao = service.alugarFilme(usuario, filme);

    }

    @Test
    public void testeUsuarioVazio() throws FilmeSemEstoqueException {
        LocacaoService service = new LocacaoService();
        Filme filme = new Filme("O Brilho eterno de uma mente sem lembraças", 5, 15.62);
        try {
            service.alugarFilme(null, filme);
            Assert.fail();
        } catch (LocadoraException e) {
            Assert.assertThat(e.getMessage(), is("Usuário vazio"));
        }
    }

    @Test
    public void testeFilmeVazio() throws FilmeSemEstoqueException, LocadoraException {
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Carol");

        exception.expect(LocadoraException.class);
        exception.expectMessage("Informações de filme estão vazios");
        service.alugarFilme(usuario, null);
    }
}
