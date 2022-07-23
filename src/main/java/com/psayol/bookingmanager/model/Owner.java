package com.psayol.bookingmanager.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @Builder
    public Owner(Long id, @Size(min = 2, max = 100) String firstName, @Size(min = 2, max = 100) String lastName, @Size(min = 5, max = 14) String phoneNumber, @NotNull(message = "Email cannot be null.") @Email(message = "Invalid email format") String email, @NotNull(message = "Password cannot be null.") String password, OwnerType ownerType) {
        super(id, firstName, lastName, phoneNumber, email, password);
        this.ownerType = ownerType;
    }
}
