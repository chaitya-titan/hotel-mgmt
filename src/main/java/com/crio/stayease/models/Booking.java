package com.crio.stayease.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "bookings")
@EqualsAndHashCode(callSuper = true)
public class Booking extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
