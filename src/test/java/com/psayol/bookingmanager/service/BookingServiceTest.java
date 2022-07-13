package com.psayol.bookingmanager.service;

import com.psayol.bookingmanager.exception.UnableToProcessRequestException;
import com.psayol.bookingmanager.model.Booking;
import com.psayol.bookingmanager.model.BookingStatus;
import com.psayol.bookingmanager.model.Guest;
import com.psayol.bookingmanager.model.Property;
import com.psayol.bookingmanager.model.builder.BookingBuilder;
import com.psayol.bookingmanager.repository.BlockRepository;
import com.psayol.bookingmanager.repository.BookingRepository;
import com.psayol.bookingmanager.repository.GuestRepository;
import com.psayol.bookingmanager.repository.PropertyRepository;
import com.psayol.bookingmanager.request.BookingRequest;
import com.psayol.bookingmanager.response.ResponseConstants;
import com.psayol.bookingmanager.response.ResponseDataDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class BookingServiceTest {

    @Mock
    private BookingBlockService bookingBlockService;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private BlockRepository blockRepository;
    @Mock
    private GuestRepository guestRepository;
    @Mock
    private PropertyRepository propertyRepository;
    @Mock
    private Utils utils;

    Booking booking;
    Booking booking2;
    Guest guest;
    Property property;

    @InjectMocks
    private BookingService bookingService = new BookingService(bookingRepository, blockRepository, guestRepository,
            bookingBlockService, propertyRepository, utils);

    @BeforeEach
    void setMockOutput() {

        guest = new Guest(1L);
        property = new Property(1L);

        booking = new BookingBuilder()
                .setId(1L)
                .setDateFrom(LocalDate.parse("2022-02-20"))
                .setDateTo(LocalDate.parse("2022-02-23"))
                .setStatus(BookingStatus.BOOKED)
                .setGuest(guest)
                .setProperty(property)
                .build();

        booking2 = new BookingBuilder()
                .setDateFrom(LocalDate.parse("2022-02-20"))
                .setDateTo(LocalDate.parse("2022-02-23"))
                .setStatus(BookingStatus.BOOKED)
                .setGuest(guest)
                .setProperty(property)
                .build();

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingRepository.findById(9L)).thenReturn(Optional.empty());

        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(guestRepository.findById(1L)).thenReturn(Optional.of(guest));
        when(bookingRepository.save(booking2)).thenReturn(booking);
    }

    @Test
    public void getBooking_whenIdExists_thenReturnBooking(){
        ResponseEntity<ResponseDataDTO> response = bookingService.getBooking(1L);

        assertEquals(response.getBody().getStatusCode(), ResponseConstants.SUCCESS.getCode());
        assertEquals(response.getBody().getStatusMessage(), ResponseConstants.SUCCESS.getMessage());

    }

    @Test
    public void getBooking_whenIdNotExists_thenReturnErrorResult(){

        assertThrows(UnableToProcessRequestException.class, () -> {
            bookingService.getBooking(9L);
        });
    }

    @Test
    public void createBooking_whenCorrectBooking_thenReturnBookingSaved(){
        BookingRequest request = new BookingRequest(null,LocalDate.parse("2022-02-20"),LocalDate.parse("2022-02-23"),1L,1L);

        ResponseEntity<ResponseDataDTO> response = bookingService.createBooking(request);

        assertEquals(response.getBody().getStatusCode(), ResponseConstants.SUCCESS.getCode());
        assertEquals(response.getBody().getStatusMessage(), ResponseConstants.SUCCESS.getMessage());
    }

    @Test
    public void createBooking_whenWrongGuestBooking_thenReturnFailure(){
        BookingRequest request = new BookingRequest(null,LocalDate.parse("2022-02-20"),LocalDate.parse("2022-02-23"),9L,1L);

        assertThrows(UnableToProcessRequestException.class, () -> {
            bookingService.createBooking(request);
        });
    }

}
