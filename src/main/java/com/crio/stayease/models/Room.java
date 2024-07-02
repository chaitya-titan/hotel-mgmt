package com.crio.stayease.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "rooms")
@EqualsAndHashCode(callSuper = true, exclude = "parentHotel")
public class Room extends BaseModel{
    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonBackReference
    private Hotel parentHotel;
    @Enumerated(EnumType.ORDINAL)
    private RoomStatus roomStatus;
    private int price;
}
