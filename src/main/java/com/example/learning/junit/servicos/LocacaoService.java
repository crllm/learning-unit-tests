package com.example.learning.junit.servicos;

import com.example.learning.junit.entidades.Filme;
import com.example.learning.junit.entidades.Locacao;
import com.example.learning.junit.entidades.Usuario;
import com.example.learning.junit.exceptions.FilmeSemEstoqueException;
import com.example.learning.junit.exceptions.LocadoraException;

import java.util.Date;

import static com.example.learning.junit.utils.DataUtils.adicionarDias;

public class LocacaoService {

    public Locacao alugarFilme(Usuario usuario, Filme filme) throws FilmeSemEstoqueException, LocadoraException {
        if (usuario == null) {
            throw new LocadoraException("Informações de usuário está vazio");

        }
        if (filme == null) {
            throw new LocadoraException("Informações de filme estão vazios");
        }
        if (filme.getEstoque().equals(0)) {
            throw new FilmeSemEstoqueException("Filme não está disponivel em estoque");

        }
        Locacao locacao = new Locacao();
        locacao.setFilme(filme);
        locacao.setUsuario(usuario);
        locacao.setDataLocacao(new Date());
        locacao.setValor(filme.getPrecoLocacao());

        //Entrega no dia seguinte
        Date dataEntrega = new Date();
        dataEntrega = adicionarDias(dataEntrega, 1);
        locacao.setDataRetorno(dataEntrega);

        //Salvando a locacao...
        //TODO adicionar método para salvar
        return locacao;

    }
}