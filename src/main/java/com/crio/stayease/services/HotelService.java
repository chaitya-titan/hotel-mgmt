package com.crio.stayease.services;

import com.crio.stayease.dtos.HotelRequestDTO;
import com.crio.stayease.dtos.HotelResponseDTO;
import com.crio.stayease.dtos.UpdateHotelDTO;
import com.crio.stayease.exceptions.HotelNotFoundException;
import com.crio.stayease.exceptions.UnAuthorisedException;
import com.crio.stayease.exceptions.UserNotFoundException;
import com.crio.stayease.models.*;
import com.crio.stayease.repositories.IHotelRepository;
import com.crio.stayease.repositories.IRoomRepository;
import com.crio.stayease.repositories.IUserRepository;
import com.crio.stayease.security.jwt.JWTService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class HotelService {
    @Autowired
    private final IUserRepository userRepository;
    @Autowired
    private final IHotelRepository hotelRepository;
    @Autowired
    private final IRoomRepository roomRepository;
    @Autowired
    private final JWTService jwtService;
    @Autowired
    private final ModelMapper modelMapper;

    public HotelService(IUserRepository userRepository, IHotelRepository hotelRepository,
                        IRoomRepository roomRepository, JWTService jwtService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
    }

    public HotelResponseDTO createHotel(HotelRequestDTO hotelRequestDTO){
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        Long userId = jwtService.getUserIDFromJWT(token);
        User user = userRepository.findById(userId).get();
        if(!user.getRole().equals(UserRole.ADMIN)){
            throw new UnAuthorisedException("Only Admins can create hotels");
        }
        Hotel hotel = modelMapper.map(hotelRequestDTO, Hotel.class);
        hotel.setCreatedAt(LocalDate.now());
        Hotel savedHotel = hotelRepository.save(hotel);
        int numberOfRooms = hotelRequestDTO.getNumberOfRooms();
        Set<Room> rooms = new HashSet<>();
        for (int i = 0; i < numberOfRooms; i++) {
            Room room = new Room();
            room.setParentHotel(savedHotel);
            room.setRoomStatus(RoomStatus.AVAILABLE);
            room.setPrice(2000);
            room.setCreatedAt(LocalDate.now());
            roomRepository.save(room);
            rooms.add(room);
        }
        savedHotel.getRooms().addAll(rooms);
        savedHotel.setCreatedAt(LocalDate.now());
        hotelRepository.save(savedHotel);
        return modelMapper.map(savedHotel, HotelResponseDTO.class);
    }

    public List<Hotel> getAllHotels(){
        return hotelRepository.findAll();
    }

    public Hotel getHotelById(Long id){
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if(hotel.isEmpty()){
            throw new HotelNotFoundException("Hotel not found");
        }
        return hotel.get();
    }

    public void deleteHotel(Long id){
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        Optional<User> user = userRepository.findById(jwtService.getUserIDFromJWT(token));
        if(user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        if(!user.get().getRole().equals(UserRole.ADMIN)) {
            throw new UnAuthorisedException("Only Admins can delete hotels");
        }
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if(hotel.isEmpty()) {
            throw new HotelNotFoundException("Hotel not found");
        }
        hotelRepository.delete(hotel.get());
    }

    public HotelResponseDTO updateHotel(Long id, UpdateHotelDTO updateHotelDTO){
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        Long userId = jwtService.getUserIDFromJWT(token);
        User user = userRepository.findById(userId).get();
        if(!user.getRole().equals(UserRole.HOTEL_MANAGER)){
            throw new UnAuthorisedException("Only Hotel Managers can update hotels");
        }
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if(hotel.isEmpty()){
            throw new HotelNotFoundException("Hotel not found");
        }
        if(updateHotelDTO.getHotelName() != null){
            hotel.get().setHotelName(updateHotelDTO.getHotelName());
        }
        if(updateHotelDTO.getLocation() != null){
            hotel.get().setLocation(updateHotelDTO.getLocation());
        }
        if(updateHotelDTO.getDescription() != null){
            hotel.get().setDescription(updateHotelDTO.getDescription());
        }
        Hotel updatedHotel = hotelRepository.save(hotel.get());
        return modelMapper.map(hotelRepository.save(updatedHotel), HotelResponseDTO.class);
    }
}
