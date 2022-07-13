package com.psayol.bookingmanager.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@NoArgsConstructor
@DiscriminatorValue(value="GUEST")
public class Guest extends User{

    public Guest(Long id){
        super(id);
    }

}
