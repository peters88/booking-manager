package com.psayol.bookingmanager.service;

import com.psayol.bookingmanager.exception.InvalidDatesException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UtilsTest {
    private Utils utils;


    @Test
    public void checkIsValidDate_whenInvalidDate_thenReturnException(){
        utils = new Utils();

        LocalDate dateFrom = LocalDate.now().minusDays(5);
        LocalDate dateTo = LocalDate.now().plusDays(5);

        assertThrows(InvalidDatesException.class, () -> {
            utils.checkIsValidDate(dateFrom,dateTo);
        });
    }

    @Test
    public void checkIsValidDate_whenEqualValidDate_thenReturnException(){
        utils = new Utils();

        LocalDate dateFrom = LocalDate.now().plusDays(5);
        LocalDate dateTo = LocalDate.now().plusDays(5);

        assertThrows(InvalidDatesException.class, () -> {
            utils.checkIsValidDate(dateFrom,dateTo);
        });
    }
}
