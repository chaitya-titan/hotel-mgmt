package com.crio.stayease.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Data
@MappedSuperclass
public abstract class BaseModel {
    @Id
    @GeneratedValue()
    private Long id;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;
}
