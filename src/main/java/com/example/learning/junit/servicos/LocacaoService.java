package com.example.learning.junit.servicos;

import com.example.learning.junit.dao.LocacaoDAO;
import com.example.learning.junit.entidades.Filme;
import com.example.learning.junit.entidades.Locacao;
import com.example.learning.junit.entidades.Usuario;
import com.example.learning.junit.exceptions.FilmeSemEstoqueException;
import com.example.learning.junit.exceptions.LocadoraException;
import com.example.learning.junit.utils.DataUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.learning.junit.utils.DataUtils.adicionarDias;

public class LocacaoService {

    private LocacaoDAO locacaoDAO;

    private SPCService spcService;

    public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {
        checkEmptyInformations(usuario, filmes);

        Locacao locacao = new Locacao();
        locacao.setFilme(filmes);
        locacao.setUsuario(usuario);
        locacao.setDataLocacao(new Date());
        getValorLocacao(filmes, locacao);

        //Entrega no dia seguinte
        checkDataDeEntrega(locacao);

        //Salvando a locacao...
        locacaoDAO.salvar(locacao);
        return locacao;

    }

    private void checkDataDeEntrega(Locacao locacao) {
        Date dataEntrega = new Date();
        if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
            dataEntrega = adicionarDias(dataEntrega, 2);
        }
        dataEntrega = adicionarDias(dataEntrega, 1);

        locacao.setDataRetorno(dataEntrega);
    }

    private void checkEmptyInformations(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {
        if (usuario == null) {
            throw new LocadoraException("Informações de usuário está vazio");
        }

        if (filmes == null || filmes.isEmpty()) {
            throw new LocadoraException("Informações de filme estão vazios");
        }

        for (Filme filme : filmes) {
            if (filme.getEstoque() == 0) {
                throw new FilmeSemEstoqueException();
            }
        }
        if (spcService.possuiNegativacao(usuario)) {
            throw new LocadoraException("Usuário negativado");
        }
    }

    private void getValorLocacao(List<Filme> filmes, Locacao locacao) {
        Double valorLocacao = 0d;

        for (int i = 0; i < filmes.size(); i++) {
            Filme filme = filmes.get(i);
            Double valorDoFilme = filme.getPrecoLocacao();

            switch (i) {
                case 2:
                    valorDoFilme = valorDoFilme * 0.75;
                    break;
                case 3:
                    valorDoFilme = valorDoFilme * 0.5;
                    break;
                case 4:
                    valorDoFilme = valorDoFilme * 0.25;
                    break;
                case 5:
                    valorDoFilme = 0d;
                    break;
            }
            valorLocacao += valorDoFilme;
        }
        locacao.setValor(valorLocacao);
    }

    public void setLocacaoDAO(LocacaoDAO locacaoDAO) {
        this.locacaoDAO = locacaoDAO;
    }

    public void setSPCService(SPCService spc) {
        spcService = spc;
    }
}