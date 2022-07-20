package com.psayol.bookingmanager.service;

import com.psayol.bookingmanager.model.Booking;
import com.psayol.bookingmanager.model.BookingBlock;
import com.psayol.bookingmanager.model.Property;
import com.psayol.bookingmanager.repository.BlockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingBlockService {
    private static final Logger logger = LoggerFactory.getLogger(BookingBlockService.class);

    private BlockRepository blockRepository;

    public BookingBlockService(BlockRepository blockRepository){
        this.blockRepository = blockRepository;
    }


    public List<BookingBlock> saveBookingBlocks(Property property, Booking booking, LocalDate dateFrom, LocalDate dateTo) {

        return createBookingBlocks(property, booking, dateFrom, dateTo);
    }


    public List<BookingBlock> createBookingBlocks(Property property, Booking booking, LocalDate dateFrom, LocalDate dateTo) {
        return dateFrom.datesUntil(dateTo.plusDays(1))
                .map(date -> saveBookingBlockFromDate(property,booking,date))
                .collect(Collectors.toList());
    }

    public BookingBlock saveBookingBlockFromDate(Property property, Booking booking, LocalDate date) {

        BookingBlock bookingBlock = new BookingBlock(date,property,booking);

        return blockRepository.save(bookingBlock);
    }



}
