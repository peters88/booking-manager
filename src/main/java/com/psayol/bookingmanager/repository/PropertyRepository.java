package com.psayol.bookingmanager.repository;

import com.psayol.bookingmanager.model.Property;
import org.springframework.data.repository.CrudRepository;


public interface PropertyRepository extends CrudRepository<Property, Long> {
}
