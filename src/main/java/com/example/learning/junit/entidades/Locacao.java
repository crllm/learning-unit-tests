package com.example.learning.junit.entidades;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Locacao {

    private Usuario usuario;

    private List<Filme> filmes;

    private Date dataLocacao;

    private Date dataRetorno;

    private Double valor;
}