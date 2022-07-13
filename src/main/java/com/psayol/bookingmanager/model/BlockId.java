package com.psayol.bookingmanager.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BlockId implements Serializable {
    @Column(name = "block_day", nullable = false)
    private LocalDate blockDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_property", referencedColumnName = "id")
    private Property property;
}
