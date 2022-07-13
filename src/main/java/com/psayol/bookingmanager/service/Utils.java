package com.psayol.bookingmanager.service;

import com.psayol.bookingmanager.exception.InvalidDatesException;
import com.psayol.bookingmanager.response.ResponseConstants;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class Utils {


    public void checkIsValidDate(LocalDate dateFrom,LocalDate dateTo) {
        if(dateFrom.isBefore(LocalDate.now()) || dateFrom.isEqual(dateTo))
            throw new InvalidDatesException(ResponseConstants.INVALID_DATE_EXCEPTION.getMessage());
    }

    public String generateUUIDString() {
        return UUID.randomUUID().toString();
    }
}
