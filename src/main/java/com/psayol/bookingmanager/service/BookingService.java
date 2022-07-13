package com.psayol.bookingmanager.service;

import com.psayol.bookingmanager.exception.UnableToProcessRequestException;
import com.psayol.bookingmanager.model.Booking;
import com.psayol.bookingmanager.model.BookingStatus;
import com.psayol.bookingmanager.model.builder.BookingBuilder;
import com.psayol.bookingmanager.repository.BlockRepository;
import com.psayol.bookingmanager.repository.BookingRepository;
import com.psayol.bookingmanager.repository.GuestRepository;
import com.psayol.bookingmanager.repository.PropertyRepository;
import com.psayol.bookingmanager.request.BookingRequest;
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

@Service
public class BookingService {
    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    private BookingBlockService bookingBlockService;
    private BookingRepository bookingRepository;
    private BlockRepository blockRepository;
    private GuestRepository guestRepository;
    private PropertyRepository propertyRepository;
    private Utils utils;

    public BookingService(BookingRepository bookingRepository, BlockRepository blockRepository, GuestRepository guestRepository,
                          BookingBlockService bookingBlockService, PropertyRepository propertyRepository,Utils utils){
        this.bookingRepository = bookingRepository;
        this.blockRepository = blockRepository;
        this.guestRepository = guestRepository;
        this.bookingBlockService = bookingBlockService;
        this.propertyRepository = propertyRepository;
        this.utils = utils;
    }

    /***
     * This operation finds in the database a book given the bookId.
     * It returns a response object with all its values
     *
     * @param bookingId
     * @return
     */
    public ResponseEntity<ResponseDataDTO> getBooking(Long bookingId) {
        logger.debug("Finding Booking with id: %s",bookingId);

        Booking responseObj = bookingRepository.findById(bookingId).orElse(null);

        if(responseObj != null) {
            logger.debug("Booking found");

            ResponseDataDTO response = ResponseDataBuilder.buildSuccessResponse();
            response.setPayload(new ResponsePayloadBuilder()
                    .setBookingId(responseObj.getId())
                    .setGuestId(responseObj.getGuest().getId())
                    .setPropertyId(responseObj.getProperty().getId())
                    .setDateFrom(responseObj.getDateFrom())
                    .setDateTo(responseObj.getDateFrom())
                    .build());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            throw new UnableToProcessRequestException(ResponseConstants.UNABLE_PROCESS_REQUEST_EXCEPTION.getMessage());
        }
    }

    /***
     * Updates the status of a booking given the bookingId and set it as "CANCELED"
     * Deletes from the database all the Blocks associated to the booking.
     *
     * @param bookingId
     * @return
     */
    @Transactional
    public ResponseEntity<ResponseDataDTO> deleteBooking(Long bookingId) {
        logger.debug("Deleting Booking with id: %s",bookingId);
        Booking responseObj = cancelBooking(bookingId);

        if(responseObj != null) {
            logger.debug("Booking successfully deleted");

            ResponseDataDTO response = ResponseDataBuilder.buildSuccessResponse();
            response.setPayload(new ResponsePayloadBuilder()
                    .setBookingId(responseObj.getId())
                    .build());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            throw new UnableToProcessRequestException(ResponseConstants.UNABLE_PROCESS_REQUEST_EXCEPTION.getMessage());
        }
    }

    public Booking cancelBooking(Long bookingId){
        int deletions = blockRepository.deleteBlocksByBookingId(bookingId);

        Booking booking = bookingRepository.findById(bookingId).orElse(null);

        if(booking == null)
            throw new UnableToProcessRequestException(ResponseConstants.UNABLE_PROCESS_REQUEST_EXCEPTION.getMessage());

        return updateBookingStatus(booking,BookingStatus.CANCELED);
    }

    public Booking updateBookingStatus(Booking booking,BookingStatus status) {

        booking.setStatus(status);

        return bookingRepository.save(booking);
    }


    /***
     * Saves new booking given all the values in the db
     * returns all values including id
     * if Blocks in the same date exists, will throw constraintViolationException meaning another transaction comitted before this one taking the available days
     *
     * @param request
     * @return
     */
    @Transactional
    public ResponseEntity<ResponseDataDTO> createBooking(BookingRequest request) {
        logger.debug("Creating booking");

        Booking responseObj = saveNewBooking(request);

        if(responseObj != null) {
            logger.debug("Booking successfully created");

            ResponseDataDTO response = ResponseDataBuilder.buildSuccessResponse();
            response.setPayload(new ResponsePayloadBuilder()
                    .setBookingId(responseObj.getId())
                    .setGuestId(responseObj.getGuest().getId())
                    .setPropertyId(responseObj.getProperty().getId())
                    .build());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            throw new UnableToProcessRequestException(ResponseConstants.UNABLE_PROCESS_REQUEST_EXCEPTION.getMessage());
        }
    }


    public Booking saveNewBooking(BookingRequest request) {
        utils.checkIsValidDate(request.getDateFrom(),request.getDateTo());

        Booking booking = new BookingBuilder()
                .setDateFrom(request.getDateFrom())
                .setDateTo(request.getDateTo())
                .setProperty(propertyRepository.findById(request.getPropertyId()).orElse(null))
                .setStatus(BookingStatus.BOOKED)
                .setGuest(guestRepository.findById(request.getGuestId()).orElse(null))
                .build();

        if(booking.getGuest() == null || booking.getProperty() == null)
            throw new UnableToProcessRequestException(ResponseConstants.UNABLE_PROCESS_REQUEST_EXCEPTION.getMessage());

        booking = bookingRepository.save(booking);

        bookingBlockService.saveBookingBlocks(booking.getProperty(),booking,booking.getDateFrom(),booking.getDateTo());

        return booking;
    }

    /***
     * Updates the booking dates and updates the status to "REBOOKED"
     * Deletes the old Blocks and insert the new ones
     * if Blocks in the same date exists, will throw constraintViolationException meaning another transaction comitted before this one taking the available days
     *
     * @param request
     * @return
     */
    @Transactional
    public ResponseEntity<ResponseDataDTO> updateBooking(BookingRequest request) {
        logger.debug("Rebooking. BookingId: %s New Date From: %s New Date To: %s",request.getId(),request.getDateFrom(), request.getDateTo());

        Booking responseObj = rebookBooking(request);

        if(responseObj != null) {
            ResponseDataDTO response = ResponseDataBuilder.buildSuccessResponse();
            response.setPayload(new ResponsePayloadBuilder()
                    .setBookingId(responseObj.getId())
                    .setDateFrom(responseObj.getDateFrom())
                    .setDateTo(responseObj.getDateTo())
                    .setGuestId(responseObj.getGuest().getId())
                    .setPropertyId(responseObj.getProperty().getId())
                    .build());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            throw new UnableToProcessRequestException(ResponseConstants.UNABLE_PROCESS_REQUEST_EXCEPTION.getMessage());
        }
    }

    public Booking rebookBooking(BookingRequest request) {
        utils.checkIsValidDate(request.getDateFrom(),request.getDateTo());

        Booking booking = bookingRepository.findById(request.getId()).orElse(null);

        if(booking == null)
            throw new UnableToProcessRequestException(ResponseConstants.UNABLE_PROCESS_REQUEST_EXCEPTION.getMessage());

        booking.setDateFrom(request.getDateFrom());
        booking.setDateTo(request.getDateTo());

        int deletions = blockRepository.deleteBlocksByBookingId(booking.getId());

        bookingBlockService.saveBookingBlocks(booking.getProperty(),booking,booking.getDateFrom(),booking.getDateTo());

        return updateBookingStatus(booking,BookingStatus.REBOOKED);

    }
}
