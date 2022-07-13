package com.psayol.bookingmanager.response.builder;

import com.psayol.bookingmanager.model.BookingStatus;
import com.psayol.bookingmanager.response.ResponsePayload;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResponsePayloadBuilder {
    private Long bookingId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private BookingStatus status;
    private Long guestId;
    private Long propertyId;

    private String ownerBlockId;

    public ResponsePayloadBuilder setBookingId(Long bookingId) {
        this.bookingId=bookingId;
        return this;
    }

    public ResponsePayloadBuilder setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
        return this;
    }

    public ResponsePayloadBuilder setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
        return this;
    }

    public ResponsePayloadBuilder setStatus(BookingStatus status) {
        this.status = status;
        return this;
    }

    public ResponsePayloadBuilder setGuestId(Long guestId) {
        this.guestId = guestId;
        return this;
    }

    public ResponsePayloadBuilder setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
        return this;
    }

    public ResponsePayloadBuilder setOwnerBlockId(String ownerBlockId) {
        this.ownerBlockId=ownerBlockId;
        return this;
    }

    public List<Object> build(){
        List<Object> responseList = new ArrayList<>();
        responseList.add(new ResponsePayload(bookingId,dateFrom,dateTo,status,guestId,propertyId,ownerBlockId));

        return responseList;
    }
}
