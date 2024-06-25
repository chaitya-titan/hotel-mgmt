package com.crio.stayease.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@Entity
@Table(name = "hotels")
@EqualsAndHashCode(callSuper = true)
public class Hotel extends BaseModel{
    private String hotelName;
    private String Location;
    private String description;
    @OneToMany
    private Set<Room> rooms;
}
