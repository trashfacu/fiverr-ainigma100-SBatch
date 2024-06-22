package com.batch.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "CUSTOMER_VIGI")
public class CustomerVigi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String countryCode;

    @Transient
    private LocalDateTime executionDate;
}
