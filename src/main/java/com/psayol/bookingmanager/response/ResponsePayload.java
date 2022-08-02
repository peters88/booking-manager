package com.psayol.bookingmanager.response;

import com.psayol.bookingmanager.model.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePayload {
    private Long bookingId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private BookingStatus status;
    private Long guestId;
    private Long propertyId;

    private String ownerBlockId;


}
