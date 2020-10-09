package com.example.learning.junit.services;

import com.example.learning.junit.entidades.Filme;
import com.example.learning.junit.entidades.Locacao;
import com.example.learning.junit.entidades.Usuario;
import com.example.learning.junit.servicos.LocacaoService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static com.example.learning.junit.utils.DataUtils.isMesmaData;
import static com.example.learning.junit.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.is;

public class LocacaoServiceTest {

    @Test
    public void testeAlugaFilmes() {
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Carol");
        Filme filme = new Filme("O Brilho eterno de uma mente sem lembraças", 3, 15.62);

        Locacao locacao = service.alugarFilme(usuario, filme);
        Assert.assertTrue(locacao.getFilme().getNome().equals("O Brilho eterno de uma mente sem lembraças"));
        Assert.assertEquals("Carol", locacao.getUsuario().getNome());
        Assert.assertThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        Assert.assertThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
        Assert.assertEquals(15.62, locacao.getValor(), 0.01);

    }
}
