package com.psayol.bookingmanager.response;

public enum ResponseConstants {

    SUCCESS(200, "SUCCESS"),
    FAILURE(500, "FAILURE"),
    UNABLE_PROCESS_REQUEST_EXCEPTION(6001, "Not able to process request"),
    CONSTRAINT_VIOLATION_EXCEPTION(6002, "Dates Already Booked"),
    INVALID_DATE_EXCEPTION(6003, "Date is invalid");


    private final int code;
    private final String message;

    ResponseConstants(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + message;
    }
}
