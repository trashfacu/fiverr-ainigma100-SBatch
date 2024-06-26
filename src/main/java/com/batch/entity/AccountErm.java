package com.batch.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "ACCOUNT_ERM")
public class AccountErm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CustomerErm customer;
    private String accountNumber;
    private BigDecimal balance;

    @Transient
    private LocalDateTime executionDate;
}
