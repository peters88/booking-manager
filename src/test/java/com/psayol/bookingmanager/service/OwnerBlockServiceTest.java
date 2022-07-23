package com.psayol.bookingmanager.service;

import com.psayol.bookingmanager.model.Owner;
import com.psayol.bookingmanager.model.OwnerBlock;
import com.psayol.bookingmanager.model.Property;
import com.psayol.bookingmanager.repository.BlockRepository;
import com.psayol.bookingmanager.repository.OwnerRepository;
import com.psayol.bookingmanager.repository.PropertyRepository;
import com.psayol.bookingmanager.request.OwnerBlockRequest;
import com.psayol.bookingmanager.response.ResponseConstants;
import com.psayol.bookingmanager.response.ResponseDataDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OwnerBlockServiceTest {
    @Mock
    private BlockRepository blockRepository;
    @Mock
    private PropertyRepository propertyRepository;
    @Mock
    private OwnerRepository ownerRepository;

    private Utils utils;

    @InjectMocks
    private OwnerBlockService ownerBlockService = new OwnerBlockService(blockRepository, propertyRepository,ownerRepository);

    private static final String UUID = "fc00d4a0-b50f-436f-86eb-9f32f3395239";

    @BeforeEach
    void setMockOutput() {
        utils = new Utils();

        Property property = new Property(1L);
        Owner owner = new Owner(1L);

        OwnerBlock ownerBlock = new OwnerBlock(LocalDate.now().plusDays(1), property, UUID,owner);
        List<OwnerBlock> ownerBlocks = new ArrayList<>();
        ownerBlocks.add(ownerBlock);

        OwnerBlock ownerBlockResponse = new OwnerBlock(1L,LocalDate.now().plusDays(1), property, UUID,owner);
        List<OwnerBlock> ownerBlocksResponse = new ArrayList<>();
        ownerBlocksResponse.add(ownerBlockResponse);

        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(blockRepository.save(ownerBlock)).thenReturn(ownerBlockResponse);
        when(blockRepository.saveAll(Mockito.any(List.class))).thenReturn(ownerBlocksResponse);
    }

    @Test
    public void createOwnerBlock_whenRequestOk_thenReturnSuccess(){

        OwnerBlockRequest request = new OwnerBlockRequest(1L,1L
                ,LocalDate.now().plusDays(1),LocalDate.now().plusDays(3),UUID);

        ResponseEntity<ResponseDataDTO> response = ownerBlockService.createOwnerBlock(request);

        assertEquals(response.getBody().getStatusCode(), ResponseConstants.SUCCESS.getCode());
        assertEquals(response.getBody().getStatusMessage(), ResponseConstants.SUCCESS.getMessage());
    }

}
