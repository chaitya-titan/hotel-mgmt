package com.crio.stayease.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseModel{
    private String firstName;
    private String lastName;
    private String email;
    @Enumerated(EnumType.ORDINAL)
    private UserRole role;
}
