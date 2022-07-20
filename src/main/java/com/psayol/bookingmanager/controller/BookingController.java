package com.psayol.bookingmanager.controller;

import com.psayol.bookingmanager.request.BookingRequest;
import com.psayol.bookingmanager.request.OwnerBlockRequest;
import com.psayol.bookingmanager.response.ResponseDataDTO;
import com.psayol.bookingmanager.service.BookingBlockService;
import com.psayol.bookingmanager.service.BookingService;
import com.psayol.bookingmanager.service.OwnerBlockService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="api/v1")
public class BookingController {

    private OwnerBlockService ownerBlockService;
    private BookingService bookingService;

    public BookingController(BookingService bookingService, OwnerBlockService ownerBlockService){
        this.bookingService = bookingService;
        this.ownerBlockService = ownerBlockService;
    }

    @GetMapping(value="/booking",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDataDTO> getBooking(@RequestParam(value = "booking-id") Long bookingId) {
        return bookingService.getBooking(bookingId);
    }

    @RequestMapping(value="/booking", method= {RequestMethod.POST})
    public ResponseEntity<ResponseDataDTO> createBooking(@RequestBody BookingRequest request)
    {
        return bookingService.createBooking(request);
    }

    @RequestMapping(value="/owner-block", method= {RequestMethod.DELETE})
    public ResponseEntity<ResponseDataDTO> deleteOwnerBlock(@RequestParam("ownerBlockId") String ownerBlockId)
    {
        return ownerBlockService.deleteOwnerBlock(ownerBlockId);
    }

    @RequestMapping(value="/owner-block", method= {RequestMethod.POST})
    public ResponseEntity<ResponseDataDTO> createOwnerBlock(@RequestBody OwnerBlockRequest request)
    {
        return ownerBlockService.createOwnerBlock(request);
    }

    @RequestMapping(value="/booking", method= {RequestMethod.PUT})
    public ResponseEntity<ResponseDataDTO> updateBooking(@RequestBody BookingRequest request)
    {
        return bookingService.updateBooking(request);
    }

    @RequestMapping(value="/booking/cancel", method= {RequestMethod.PUT})
    public ResponseEntity<ResponseDataDTO> deleteBooking(@RequestParam("bookingId") Long bookingId)
    {
        return bookingService.deleteBooking(bookingId);
    }
}
