package com.crio.stayease.controllers;

import com.crio.stayease.dtos.BookingResponseDTO;
import com.crio.stayease.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping("")
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings() {
        List<BookingResponseDTO> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @PostMapping("/check-out/{bookingId}")
    public ResponseEntity<BookingResponseDTO> checkOut(@PathVariable Long bookingId) {
        BookingResponseDTO bookingResponseDTO = bookingService.checkOut(bookingId);
        return ResponseEntity.ok(bookingResponseDTO);
    }

    @DeleteMapping("{bookingId}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}
