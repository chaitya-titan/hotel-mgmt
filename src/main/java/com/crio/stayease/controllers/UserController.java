package com.crio.stayease.controllers;

import com.crio.stayease.dtos.LoginRequestDTO;
import com.crio.stayease.dtos.LoginResponseDTO;
import com.crio.stayease.dtos.RegisterUserDTO;
import com.crio.stayease.dtos.UserResponseDTO;
import com.crio.stayease.services.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody RegisterUserDTO registerUserDTO) {
        UserResponseDTO userResponseDTO = userService.registerUser(registerUserDTO);
        return ResponseEntity
                .created(URI.create("/" + userResponseDTO.getId()))
                .body(userResponseDTO);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<UserResponseDTO> registerAdmin(@RequestBody RegisterUserDTO registerUserDTO) {
        UserResponseDTO userResponseDTO = userService.registerAdmin(registerUserDTO);
        return ResponseEntity
                .created(URI.create("/" + userResponseDTO.getId()))
                .body(userResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO loginResponseDTO = userService.login(loginRequestDTO);
        return ResponseEntity.ok(loginResponseDTO);
    }
}
