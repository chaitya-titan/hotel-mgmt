package com.crio.stayease.services;

import com.crio.stayease.dtos.LoginRequestDTO;
import com.crio.stayease.dtos.LoginResponseDTO;
import com.crio.stayease.dtos.RegisterUserDTO;
import com.crio.stayease.dtos.UserResponseDTO;
import com.crio.stayease.exceptions.DifferentApiForRegistrationException;
import com.crio.stayease.exceptions.PasswordMismatchException;
import com.crio.stayease.exceptions.UserAlreadyExistsException;
import com.crio.stayease.exceptions.UserNotFoundException;
import com.crio.stayease.models.User;
import com.crio.stayease.models.UserRole;
import com.crio.stayease.repositories.IUserRepository;
import com.crio.stayease.security.jwt.JWTService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private final IUserRepository userRepository;
    @Autowired
    private final JWTService jwtService;
    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository, JWTService jwtService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
    }

    public UserResponseDTO registerUser(RegisterUserDTO registerUserDTO){
        String email = registerUserDTO.getEmail();
        Optional<User> user = userRepository.findByEmail(email);
        if(registerUserDTO.getRole().equals(UserRole.ADMIN)){
            throw new DifferentApiForRegistrationException("Admin registration not allowed");
        }
        if(user.isPresent()){
            throw new UserAlreadyExistsException("User already exists");
        }
        User newUser = modelMapper.map(registerUserDTO, User.class);
        newUser.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        newUser.setCreatedAt(LocalDate.now());
        newUser.setRole(registerUserDTO.getRole() == null ? UserRole.CUSTOMER : registerUserDTO.getRole());
        userRepository.save(newUser);
        return modelMapper.map(newUser, UserResponseDTO.class);
    }

    public UserResponseDTO registerAdmin(RegisterUserDTO registerUserDTO){
        String email = registerUserDTO.getEmail();
        Optional<User> user = userRepository.findByEmail(email);
        if(!registerUserDTO.getRole().equals(UserRole.ADMIN)){
            throw new DifferentApiForRegistrationException("User Registration not allowed");
        }
        if(user.isPresent() && user.get().getRole().equals(UserRole.ADMIN)){
            throw new UserAlreadyExistsException("User already exists");
        }
        User newUser = modelMapper.map(registerUserDTO, User.class);
        newUser.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        newUser.setCreatedAt(LocalDate.now());
        newUser.setRole(UserRole.ADMIN);
        userRepository.save(newUser);
        return modelMapper.map(newUser, UserResponseDTO.class);
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){
        String email = loginRequestDTO.getEmail();
        String password = loginRequestDTO.getPassword();
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        if(!passwordEncoder.matches(password, user.get().getPassword())){
            throw new PasswordMismatchException("Password Mismatch");
        }
        String token = jwtService.generateToken(user.get().getId());
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setToken(token);
        return loginResponseDTO;
    }

}
