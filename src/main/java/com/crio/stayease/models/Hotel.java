package com.crio.stayease.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "hotels")
@EqualsAndHashCode(callSuper = true, exclude = "rooms")
public class Hotel extends BaseModel{
    private String hotelName;
    private String location;
    private String description;
    @OneToMany(mappedBy = "parentHotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Room> rooms = new HashSet<>();
}
