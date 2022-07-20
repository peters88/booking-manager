package com.psayol.bookingmanager.service;

import com.psayol.bookingmanager.exception.InvalidDatesException;
import com.psayol.bookingmanager.response.ResponseConstants;

import java.time.LocalDate;
import java.util.UUID;

public class Utils {


    public static void checkIsValidDate(LocalDate dateFrom,LocalDate dateTo) {
        if(dateFrom == null || dateTo== null || dateFrom.isBefore(LocalDate.now()) || dateFrom.isEqual(dateTo))
            throw new InvalidDatesException(ResponseConstants.INVALID_DATE_EXCEPTION.getMessage());
    }

    public static String generateUUIDString() {
        return UUID.randomUUID().toString();
    }
}
