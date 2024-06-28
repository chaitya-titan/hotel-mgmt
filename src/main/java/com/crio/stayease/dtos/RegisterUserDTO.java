package com.crio.stayease.dtos;

import com.crio.stayease.models.UserRole;
import lombok.Data;

@Data
public class RegisterUserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRole role;
}
