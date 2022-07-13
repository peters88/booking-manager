package com.psayol.bookingmanager.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@DiscriminatorValue(value="OWNER")
public class OwnerBlock extends Block {
    @Column(name = "owner_block_id")
    private String ownerBlockId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_owner", referencedColumnName = "id")
    private Owner owner;

    public OwnerBlock(LocalDate date, Property property, String ownerBlockId, Owner owner) {
        super(date,property);
        this.ownerBlockId = ownerBlockId;
        this.owner = owner;
    }

    public OwnerBlock(Long id,LocalDate date, Property property, String ownerBlockId, Owner owner) {
        super(id,date,property);
        this.ownerBlockId = ownerBlockId;
        this.owner = owner;
    }
}
