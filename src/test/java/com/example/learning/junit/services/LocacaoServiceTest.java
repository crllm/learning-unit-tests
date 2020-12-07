package com.example.learning.junit.services;

import com.example.learning.junit.dao.LocacaoDAO;
import com.example.learning.junit.entidades.Filme;
import com.example.learning.junit.entidades.Locacao;
import com.example.learning.junit.entidades.Usuario;
import com.example.learning.junit.exceptions.FilmeSemEstoqueException;
import com.example.learning.junit.exceptions.LocadoraException;
import com.example.learning.junit.servicos.EmailService;
import com.example.learning.junit.servicos.LocacaoService;
import com.example.learning.junit.servicos.SPCService;
import com.example.learning.junit.utils.DataUtils;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.learning.junit.builders.FilmeBuilder.umFilme;
import static com.example.learning.junit.builders.LocacaoBuilder.umLocacao;
import static com.example.learning.junit.builders.UsuarioBuilder.umUsuario;
import static com.example.learning.junit.matchers.MatcherProprios.caiEm;
import static com.example.learning.junit.matchers.MatcherProprios.dataDiferenca;
import static com.example.learning.junit.utils.DataUtils.isMesmaData;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LocacaoServiceTest {

    private LocacaoService service;
    private LocacaoDAO locacaoDAO;
    private SPCService spcService;
    private EmailService emailService;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void executeAntes() {
        service = new LocacaoService();

        locacaoDAO = Mockito.mock(LocacaoDAO.class);
        service.setLocacaoDAO(locacaoDAO);

        spcService = Mockito.mock(SPCService.class);
        service.setSPCService(spcService);

        emailService = Mockito.mock(EmailService.class);
        service.setEmailService(emailService);
    }

    @Test
    public void alugaFilmes() throws FilmeSemEstoqueException, LocadoraException {
        Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = asList(umFilme().agora());

        Locacao locacao = service.alugarFilme(usuario, filmes);
        locacao.getFilmes().forEach(filme -> errorCollector.checkThat(filme.getNome(), is(equalTo("O Brilho eterno de uma mente sem lembraças"))));
        errorCollector.checkThat(locacao.getUsuario().getNome(), is(equalTo("Carol")));
        errorCollector.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
//        errorCollector.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
        errorCollector.checkThat(locacao.getValor(), is(equalTo(4.0)));
        errorCollector.checkThat(locacao.getDataRetorno(), dataDiferenca(1));
    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void alugarFilmesSemEstoque() throws Exception {
        Usuario usuario = umUsuario().agora();

        List<Filme> filmes = asList(umFilme().semEstoque().agora());
        service.alugarFilme(usuario, filmes);

    }

    @Test
    public void usuarioVazio() throws FilmeSemEstoqueException {
        LocacaoService service = new LocacaoService();
        List<Filme> filmes = asList(umFilme().agora());
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
        Usuario usuario = umUsuario().agora();

        exception.expect(LocadoraException.class);
        exception.expectMessage("Informações de filme estão vazios");
        service.alugarFilme(usuario, null);
    }

    @Test
    public void descontoNo3Filme() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = asList(new Filme("O Brilho eterno de uma mente sem lembraças", 5, 4.0),
                new Filme("Origem", 3, 4.0),
                new Filme("Toy Story", 2, 4.0));

        Locacao locacao = service.alugarFilme(usuario, filmes);
        assertThat(locacao.getValor(), is(equalTo(11.0)));
    }

    @Test
    public void dscontoNo4Filme() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = asList(new Filme("O Brilho eterno de uma mente sem lembraças", 5, 4.0),
                new Filme("Origem", 3, 4.0),
                new Filme("Toy Story", 2, 4.0),
                new Filme("A Era do Gelo", 5, 4.0));

        Locacao locacao = service.alugarFilme(usuario, filmes);
        assertThat(locacao.getValor(), is(equalTo(13.0)));
    }

    @Test
    public void descontoNo5Filme() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = asList(new Filme("O Brilho eterno de uma mente sem lembraças", 5, 4.0),
                new Filme("Origem", 3, 4.0),
                new Filme("Toy Story", 2, 4.0),
                new Filme("A Era do Gelo", 5, 4.0),
                new Filme("Entre Facas e Segredos", 2, 4.0));

        Locacao locacao = service.alugarFilme(usuario, filmes);
        assertThat(locacao.getValor(), is(equalTo(14.0)));
    }

    @Test
    public void dscontoNo6Filme() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = asList(new Filme("O Brilho eterno de uma mente sem lembraças", 5, 4.0),
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

        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = asList(new Filme("O Brilho eterno de uma mente sem lembraças", 5, 15.62));

        Locacao locacao = service.alugarFilme(usuario, filmes);
//        assertThat(locacao.getDataRetorno(), new DiaDaSemanaMatcher(Calendar.MONDAY));
        assertThat(locacao.getDataRetorno(), caiEm(Calendar.MONDAY));
    }

    @Test
    public void naoDeveAlugarFilmeParaNegativadoSPC() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = asList(umFilme().agora());

        when(spcService.possuiNegativacao(usuario)).thenReturn(true);

        exception.expect(LocadoraException.class);
        exception.expectMessage("Usuário negativado");

        service.alugarFilme(usuario, filmes);
    }

    @Test
    public void enviarEmailParaLocacoesAtradas() {
        Usuario usuario = umUsuario().agora();

        List<Locacao> locacaoList = asList(
                umLocacao()
                        .comUsuario(usuario)
                        .atrasado()
                        .agora());
        when(locacaoDAO.obterLocacoesPendentes()).thenReturn(locacaoList);

        service.notificarAtrados();
        verify(emailService).notificarAtraso(usuario);
    }
}
