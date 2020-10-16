package com.example.learning.junit.matchers;

import com.example.learning.junit.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DiaDaSemanaMatcher extends TypeSafeMatcher<Date> {


    private final Integer diaDaSemana;

    public DiaDaSemanaMatcher(Integer diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }

    @Override
    protected boolean matchesSafely(Date date) {
        return DataUtils.verificarDiaSemana(date, diaDaSemana);
    }

    @Override
    public void describeTo(Description description) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, diaDaSemana);
        String dataPt = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("pt", "BR"));
        description.appendText(dataPt);
    }
}
