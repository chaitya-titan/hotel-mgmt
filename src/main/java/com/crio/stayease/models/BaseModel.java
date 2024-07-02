package com.crio.stayease.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Data
@MappedSuperclass
public abstract class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;
}
