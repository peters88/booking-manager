package com.psayol.bookingmanager.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerBlockRequest {
    private Long ownerId;
    private Long propertyId;
    private LocalDate blockFrom;
    private LocalDate blockTo;
    private String ownerBlockId;
}
