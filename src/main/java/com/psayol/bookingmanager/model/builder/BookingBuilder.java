package com.psayol.bookingmanager.model.builder;

import com.psayol.bookingmanager.model.Booking;
import com.psayol.bookingmanager.model.BookingStatus;
import com.psayol.bookingmanager.model.Guest;
import com.psayol.bookingmanager.model.Property;

import java.time.LocalDate;

public class BookingBuilder {
    private Long id;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private BookingStatus status = null;
    private Guest guest = null;
    private Property property = null;

    public BookingBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public BookingBuilder setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
        return this;
    }

    public BookingBuilder setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
        return this;
    }

    public BookingBuilder setStatus(BookingStatus status) {
        this.status = status;
        return this;
    }

    public BookingBuilder setGuest(Guest guest) {
        this.guest = guest;
        return this;
    }

    public BookingBuilder setProperty(Property property) {
        this.property = property;
        return this;
    }

    public Booking build(){
        return new Booking(id,dateFrom,dateTo,status,guest,property);
    }
}
