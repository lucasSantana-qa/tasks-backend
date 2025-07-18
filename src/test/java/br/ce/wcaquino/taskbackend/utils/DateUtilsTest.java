package br.ce.wcaquino.taskbackend.utils;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class DateUtilsTest {

    @Test
    public void deveRetornarTrueParaDatasFuturas() {
        LocalDate date = LocalDate.now().plusDays(1);

        boolean equalOrFutureDate = DateUtils.isEqualOrFutureDate(date);

        Assert.assertTrue(equalOrFutureDate);
    }

    @Test
    public void deveRetornarTrueParaDatasPresentes() {
        LocalDate date = LocalDate.now();

        boolean equalOrFutureDate = DateUtils.isEqualOrFutureDate(date);

        Assert.assertTrue(equalOrFutureDate);
    }

    @Test
    public void deveRetornarFalseParaDatasPassadas() {
        LocalDate date = LocalDate.now().minusDays(1);

        boolean equalOrFutureDate = DateUtils.isEqualOrFutureDate(date);

        Assert.assertFalse(equalOrFutureDate);
    }
}
