package com.psayol.bookingmanager.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@Entity
@NoArgsConstructor
@DiscriminatorValue(value="OWNER")
public class Owner extends User{
    @Enumerated(EnumType.STRING)
    private OwnerType ownerType;

    public Owner(Long id){
        super(id);
    }
}
