package com.example.learning.junit.matchers;

public class MatcherProprios {

    public static DiaDaSemanaMatcher caiEm(Integer diaSemana) {
        return new DiaDaSemanaMatcher(diaSemana);
    }

    public static DataDiferencaMatcher dataDiferenca(Integer quantidadeDias) {
        return new DataDiferencaMatcher(quantidadeDias);
    }

}
