package com.crio.stayease.dtos;

import lombok.Data;

@Data
public class UpdateHotelDTO {
    private String hotelName;
    private String location;
    private String description;
}
