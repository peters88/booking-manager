package com.psayol.bookingmanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@DiscriminatorValue(value="BOOKING")
@NoArgsConstructor
@AllArgsConstructor
public class BookingBlock extends Block{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_booking", referencedColumnName = "id")
    private Booking booking;

    public BookingBlock(LocalDate date, Property property, Booking booking) {
        super(date,property);
        this.booking = booking;
    }
}
