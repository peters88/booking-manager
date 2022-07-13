package com.psayol.bookingmanager.service;

import com.psayol.bookingmanager.exception.InvalidDatesException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UtilsTest {
    private Utils utils;


    @Test
    public void checkIsValidDate_whenValidDate_thenReturnTrue(){
        utils = new Utils();

        LocalDate date = LocalDate.now().minusDays(5);

        assertThrows(InvalidDatesException.class, () -> {
            utils.checkIsValidDate(date);
        });
    }
}
