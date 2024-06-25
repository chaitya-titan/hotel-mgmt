package com.crio.stayease.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "bookings")
@EqualsAndHashCode(callSuper = true)
public class Booking extends BaseModel {
    @OneToOne
    private User user;
    @OneToOne
    private Hotel hotel;
    @OneToOne
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
