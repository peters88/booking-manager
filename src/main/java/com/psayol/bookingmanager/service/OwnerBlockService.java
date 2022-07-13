package com.psayol.bookingmanager.service;

import com.psayol.bookingmanager.exception.UnableToProcessRequestException;
import com.psayol.bookingmanager.model.Booking;
import com.psayol.bookingmanager.model.BookingBlock;
import com.psayol.bookingmanager.model.Owner;
import com.psayol.bookingmanager.model.OwnerBlock;
import com.psayol.bookingmanager.model.Property;
import com.psayol.bookingmanager.repository.BlockRepository;
import com.psayol.bookingmanager.repository.OwnerRepository;
import com.psayol.bookingmanager.repository.PropertyRepository;
import com.psayol.bookingmanager.request.OwnerBlockRequest;
import com.psayol.bookingmanager.response.ResponseConstants;
import com.psayol.bookingmanager.response.ResponseDataDTO;
import com.psayol.bookingmanager.response.builder.ResponseDataBuilder;
import com.psayol.bookingmanager.response.builder.ResponsePayloadBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OwnerBlockService {
    private static final Logger logger = LoggerFactory.getLogger(OwnerBlockService.class);

    private BlockRepository blockRepository;
    private PropertyRepository propertyRepository;
    private OwnerRepository ownerRepository;
    private Utils utils;

    public OwnerBlockService(BlockRepository blockRepository, PropertyRepository propertyRepository,Utils utils,OwnerRepository ownerRepository){
        this.blockRepository = blockRepository;
        this.propertyRepository = propertyRepository;
        this.utils = utils;
        this.ownerRepository = ownerRepository;
    }

    /***
     * Create a BLock of type OwnerBlock in the database to each date bewtween the range given
     * Generates a UUID to identify the the group of Blocks to a particular OwnerBlock range
     *
     * Returns the UUID ownerBlockId as String
     *
     * if Blocks in the same date exists, will throw constraintViolationException meaning another transaction comitted before this one taking the available days
     *
     * @param request
     * @return
     */
    @Transactional
    public ResponseEntity<ResponseDataDTO> createOwnerBlock(OwnerBlockRequest request) {

        List<OwnerBlock> responseObj = saveOwnerBlock(request);

        if(responseObj != null) {
            ResponseDataDTO response = ResponseDataBuilder.buildSuccessResponse();
            response.setPayload(new ResponsePayloadBuilder()
                    .setOwnerBlockId(getOwnerBlockIdFromResponse(responseObj))
                    .build());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            throw new UnableToProcessRequestException(ResponseConstants.UNABLE_PROCESS_REQUEST_EXCEPTION.getMessage());
        }
    }

    private String getOwnerBlockIdFromResponse(List<OwnerBlock> responseObj) {
        return responseObj.get(0).getOwnerBlockId();
    }

    public List<OwnerBlock> saveOwnerBlock(OwnerBlockRequest request) {
        utils.checkIsValidDate(request.getBlockFrom());

        List<OwnerBlock> ownerBlocks = convertOwnerBlockRequestToOwnerBlock(request);

        return (List<OwnerBlock>) blockRepository.saveAll(ownerBlocks);
    }

    public List<OwnerBlock> convertOwnerBlockRequestToOwnerBlock(OwnerBlockRequest request){
        String ownerBlockId = utils.generateUUIDString();

        return request.getBlockFrom().datesUntil(request.getBlockTo().plusDays(1))
                                        .map(date -> createOwnerBlockFromDate(request.getPropertyId(),request.getOwnerId(),date,ownerBlockId))
                                        .collect(Collectors.toList());
    }

    private OwnerBlock createOwnerBlockFromDate(Long propertyId, Long ownerId, LocalDate date, String ownerBlockId) {
        Property property = propertyRepository.findById(propertyId).orElse(null);
        Owner owner = ownerRepository.findById(ownerId).orElse(null);

        if(property == null || owner == null)
            throw new UnableToProcessRequestException(ResponseConstants.UNABLE_PROCESS_REQUEST_EXCEPTION.getMessage());

        return new OwnerBlock(date,property,ownerBlockId,owner);
    }

    /***
     * Deletes Blocks given a ownerBlockId
     *
     * @param ownerBlockId
     * @return
     */
    @Transactional
    public ResponseEntity<ResponseDataDTO> deleteOwnerBlock(String ownerBlockId) {
        Integer blocksDeleted = deleteOwnerBlockById(ownerBlockId);

        if(blocksDeleted != null) {
            ResponseDataDTO response = ResponseDataBuilder.buildSuccessResponse();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            throw new UnableToProcessRequestException(ResponseConstants.UNABLE_PROCESS_REQUEST_EXCEPTION.getMessage());
        }
    }

    public Integer deleteOwnerBlockById(String ownerBlockId) {

        return blockRepository.deleteBlocksByOwnerBlockId(ownerBlockId);

    }



}
