package com.example.learning.junit.matchers;

import com.example.learning.junit.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Date;

public class DataDiferencaMatcher extends TypeSafeMatcher<Date> {

    private final Integer quantidadeDias;

    public DataDiferencaMatcher(Integer quantidadeDias) {
        this.quantidadeDias = quantidadeDias;
    }

    @Override
    protected boolean matchesSafely(Date date) {
        return DataUtils.isMesmaData(date, DataUtils.obterDataComDiferencaDias(quantidadeDias));
    }

    @Override
    public void describeTo(Description description) {

    }

}
