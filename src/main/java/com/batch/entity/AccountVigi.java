package com.batch.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "ACCOUNT_VIGI")
public class AccountVigi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CustomerVigi customer;
    private String accountNumber;
    private BigDecimal balance;

    @Transient
    private LocalDateTime executionDate;
}
