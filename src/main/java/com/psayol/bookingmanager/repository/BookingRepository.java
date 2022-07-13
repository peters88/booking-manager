package com.psayol.bookingmanager.repository;

import com.psayol.bookingmanager.model.Booking;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BookingRepository extends CrudRepository<Booking, Long> {
}
