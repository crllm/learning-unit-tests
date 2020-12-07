package com.example.learning.junit.dao;

import com.example.learning.junit.entidades.Locacao;

import java.util.List;

public interface LocacaoDAO {

    void salvar(Locacao locacao);

    List<Locacao> obterLocacoesPendentes();

}
