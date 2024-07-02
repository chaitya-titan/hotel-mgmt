package com.crio.stayease.controllers;

import com.crio.stayease.dtos.BookingResponseDTO;
import com.crio.stayease.dtos.HotelRequestDTO;
import com.crio.stayease.dtos.HotelResponseDTO;
import com.crio.stayease.dtos.UpdateHotelDTO;
import com.crio.stayease.models.Hotel;
import com.crio.stayease.services.BookingService;
import com.crio.stayease.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private BookingService bookingService;

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long id) {
        Hotel hotel = hotelService.getHotelById(id);
        return ResponseEntity.ok(hotel);
    }

    @GetMapping("")
    public ResponseEntity<List<Hotel>> getAllHotels(){
        List<Hotel> hotels = hotelService.getAllHotels();
        return ResponseEntity.ok(hotels);
    }

    @PostMapping("/create")
    public ResponseEntity<HotelResponseDTO> createHotel(@RequestBody HotelRequestDTO hotelRequestDTO) {
        HotelResponseDTO hotelResponseDTO = hotelService.createHotel(hotelRequestDTO);
        return ResponseEntity.created(URI.create("/" + hotelResponseDTO.getId())).body(hotelResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelResponseDTO> updateHotel(@PathVariable Long id, @RequestBody UpdateHotelDTO updateHotelDTO) {
        HotelResponseDTO hotelResponseDTO = hotelService.updateHotel(id, updateHotelDTO);
        return ResponseEntity.ok(hotelResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{hotelId}/book")
    public ResponseEntity<BookingResponseDTO> addRoomToHotel(@PathVariable Long hotelId) {
        BookingResponseDTO bookingResponseDTO = bookingService.createBooking(hotelId);
        return ResponseEntity.created(URI.create("/" + bookingResponseDTO.getBookingId())).body(bookingResponseDTO);
    }
}
