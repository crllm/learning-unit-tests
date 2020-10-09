package com.example.learning.junit.services;

import com.example.learning.junit.entidades.Filme;
import com.example.learning.junit.entidades.Locacao;
import com.example.learning.junit.entidades.Usuario;
import com.example.learning.junit.servicos.LocacaoService;
import com.example.learning.junit.utils.DataUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class LocacaoServiceTest {

    @Test
    public void testeAlugaFilmes() {
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Carol");
        Filme filme = new Filme("O Brilho eterno de uma mente sem lembraças", 3, 15.62);

        Locacao locacao = service.alugarFilme(usuario, filme);
        Assert.assertTrue(locacao.getFilme().getNome().equals("O Brilho eterno de uma mente sem lembraças"));
        Assert.assertTrue(locacao.getUsuario().getNome().equals("Carol"));
        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
        Assert.assertTrue(locacao.getValor() == 15.62);

    }
}
