package com.crio.stayease.services;

import com.crio.stayease.dtos.BookingResponseDTO;
import com.crio.stayease.exceptions.BookingNotFoundException;
import com.crio.stayease.exceptions.HotelNotFoundException;
import com.crio.stayease.exceptions.RoomsUnavailableException;
import com.crio.stayease.exceptions.UnAuthorisedException;
import com.crio.stayease.models.*;
import com.crio.stayease.repositories.IBookingRepository;
import com.crio.stayease.repositories.IHotelRepository;
import com.crio.stayease.repositories.IRoomRepository;
import com.crio.stayease.repositories.IUserRepository;
import com.crio.stayease.security.jwt.JWTService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private final IUserRepository userRepository;
    @Autowired
    private final IHotelRepository hotelRepository;
    @Autowired
    private final IBookingRepository bookingRepository;
    @Autowired
    private final IRoomRepository roomRepository;
    @Autowired
    private final JWTService jwtService;
    @Autowired
    private final ModelMapper modelMapper;

    public BookingService(IUserRepository userRepository, IHotelRepository hotelRepository, IBookingRepository bookingRepository, IRoomRepository roomRepository, JWTService jwtService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
    }

    public BookingResponseDTO createBooking(Long hotelId){
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        Long userId = jwtService.getUserIDFromJWT(token);
        User user = userRepository.findById(userId).get();
        if(!user.getRole().equals(UserRole.CUSTOMER)){
            throw new UnAuthorisedException("Only Customers can book rooms");
        }
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        if(hotel.isEmpty()){
            throw new HotelNotFoundException("No such hotel exists");
        }
        List<Room> rooms = roomRepository.findAll();
        Optional<Room> room = rooms.stream().filter(r -> r.getRoomStatus().equals(RoomStatus.AVAILABLE)).findFirst();
        if(room.isEmpty()){
            throw new RoomsUnavailableException("No rooms available");
        }
        room.get().setRoomStatus(RoomStatus.BOOKED);
        Booking booking = new Booking();
        booking.setRoom(room.get());
        booking.setUser(user);
        booking.setCreatedAt(LocalDate.now());
        booking.setCheckInDate(LocalDate.now());
        booking.setHotel(hotel.get());
        Booking savedBooking = bookingRepository.save(booking);
        return modelMapper.map(savedBooking, BookingResponseDTO.class);
    }

    public List<BookingResponseDTO> getAllBookings(){
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingResponseDTO> bookingResponseDTOs = new ArrayList<>();
        for (Booking booking : bookings) {
            bookingResponseDTOs.add(modelMapper.map(booking, BookingResponseDTO.class));
        }
        return bookingResponseDTOs;
    }

    public void deleteBooking(Long id){
        Optional<Booking> booking = bookingRepository.findById(id);
        if(booking.isEmpty()){
            throw new BookingNotFoundException("Booking not found");
        }
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        Long userId = jwtService.getUserIDFromJWT(token);
        User user = userRepository.findById(userId).get();
        if(!user.getRole().equals(UserRole.HOTEL_MANAGER)){
            throw new UnAuthorisedException("Only Hotel Managers can delete bookings");
        }
        bookingRepository.deleteById(id);
    }

    public BookingResponseDTO checkOut(Long bookingId){
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if(booking.isEmpty()){
            throw new BookingNotFoundException("Booking not found");
        }
        booking.get().setCheckOutDate(LocalDate.now());
        Room room = roomRepository.findById(booking.get().getRoom().getId()).get();
        room.setRoomStatus(RoomStatus.AVAILABLE);
        bookingRepository.save(booking.get());
        return modelMapper.map(booking.get(), BookingResponseDTO.class);
    }

}
