package com.crio.stayease.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "rooms")
@EqualsAndHashCode(callSuper = true)
public class Room extends BaseModel{
    private String roomNumber;
    @OneToOne
    private Hotel parentHotel;
    @Enumerated(EnumType.ORDINAL)
    private RoomStatus roomStatus;
    private int price;
}
