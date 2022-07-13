package com.psayol.bookingmanager.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "block"
        ,uniqueConstraints = { @UniqueConstraint(name = "optimistic_lock",columnNames = { "block_day", "fk_property" }) }
        ,indexes = {
        @Index(name = "fn_index_ownerblockid", columnList = "owner_block_id"),
        @Index(name = "fn_index_bookingid", columnList = "fk_booking")})
@DiscriminatorColumn(name="type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "block_day", nullable = false)
    private LocalDate blockDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_property", referencedColumnName = "id")
    private Property property;


    public Block(LocalDate date, Property property) {
        this.blockDay = date;
        this.property = property;
    }
}
