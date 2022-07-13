package com.psayol.bookingmanager.repository;

import com.psayol.bookingmanager.model.Block;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BlockRepository extends CrudRepository<Block, Long> {
    @Modifying
    @Query(value = "delete from Block where fk_booking = :bookingId", nativeQuery = true)
    int deleteBlocksByBookingId(@Param("bookingId") Long bookingId);

    @Modifying
    @Query(value = "delete from Block where owner_block_id = :ownerBlockId", nativeQuery = true)
    int deleteBlocksByOwnerBlockId(@Param("ownerBlockId") String ownerBlockId);
}
