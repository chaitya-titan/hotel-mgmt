package com.crio.stayease.dtos;

import com.crio.stayease.models.Room;
import lombok.Data;

import java.util.List;

@Data
public class HotelRequestDTO {
    private String hotelName;
    private String location;
    private String description;
    private int numberOfRooms;
}
