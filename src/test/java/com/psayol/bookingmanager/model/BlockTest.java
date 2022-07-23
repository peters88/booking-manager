package com.psayol.bookingmanager.model;

import com.psayol.bookingmanager.repository.BlockRepository;
import com.psayol.bookingmanager.repository.OwnerRepository;
import com.psayol.bookingmanager.repository.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BlockTest {

    private static final String UUID = "fc00d4a0-b50f-436f-86eb-9f32f3395239";

    private OwnerBlock ownerBlock;
    private OwnerBlock ownerBlock2;
    private OwnerBlock ownerBlockDuplicated;

    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private PropertyRepository propertyRepository;

    @BeforeEach
    void setMockOutput() {
        Owner owner = Owner.builder().firstName("John").lastName("Doe").email("john@doe.com").password("abc").id(1L).build();
        Property property = Property.builder().id(1L).owner(owner).maxPax(8).type("HOUSE").build();
        ownerRepository.save(owner);
        propertyRepository.save(property);


        ownerBlock = new OwnerBlock(LocalDate.now().plusDays(1), property, UUID,owner);
        ownerBlockDuplicated = new OwnerBlock(LocalDate.now().plusDays(1), property, UUID,owner);
        ownerBlock2 = new OwnerBlock(LocalDate.now().plusDays(5), property, UUID,owner);
    }

    @Test
    @Order(1)
    public void block_whenBlockExists_thenReturnException() {

        assertEquals(1L, blockRepository.save(ownerBlock).getId());

        assertThrows(DataIntegrityViolationException.class, () -> {
            blockRepository.save(ownerBlockDuplicated);
        });

    }

    @Test
    @Order(2)
    public void block_whenBlockNotExists_thenReturnSuccessful() {
        OwnerBlock block2 = blockRepository.save(ownerBlock2);


        assertEquals(3L, block2.getId());


    }

}
