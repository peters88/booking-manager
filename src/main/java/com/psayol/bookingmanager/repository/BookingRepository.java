package com.psayol.bookingmanager.repository;

import com.psayol.bookingmanager.model.Booking;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface BookingRepository extends CrudRepository<Booking, Long> {

    @Query(value = "select count(*) from Booking where (date_from <= :from and date_to >= :from) or (date_from <= :to and date_to >= :to)", nativeQuery = true)
    Integer checkAvailableDate(LocalDate from, LocalDate to);
}
