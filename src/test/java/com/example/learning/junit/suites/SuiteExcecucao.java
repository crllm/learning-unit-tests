package com.example.learning.junit.suites;

import com.example.learning.junit.services.CalculadoraTest;
import com.example.learning.junit.services.CalculoDeLocacaoDeFilmesTest;
import com.example.learning.junit.services.LocacaoServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CalculadoraTest.class,
        CalculoDeLocacaoDeFilmesTest.class,
        LocacaoServiceTest.class
})
public class SuiteExcecucao {

}
