package com.psayol.bookingmanager.repository;

import com.psayol.bookingmanager.model.Guest;
import org.springframework.data.repository.CrudRepository;

public interface GuestRepository extends CrudRepository<Guest, Long> {
}
