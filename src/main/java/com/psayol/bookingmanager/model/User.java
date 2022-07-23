package com.psayol.bookingmanager.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booking_user")
@DiscriminatorColumn(name="type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=2, max=100)
    @Column(name = "first_name")
    private String firstName;

    @Size(min=2, max=100)
    @Column(name = "last_name")
    private String lastName;

    @Size(min=5, max=14)
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull(message = "Email cannot be null.")
    @Email(message="Invalid email format")
    @Column(name = "email", nullable = false, unique=true)
    private String email;

    @NotNull(message = "Password cannot be null.")
    @Column(name = "password", nullable = false)
    private String password;

    public User(Long id) {
        this.id = id;
    }
}
