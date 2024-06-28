package com.batch.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "ACCOUNT_ERM")
public class AccountErm {

    @Id
    private String interestType;
    private String interestProperty;
    private String compoundType;
    private String interestRateType;

    @ManyToOne
    private CustomerErm customerErm;

    @Transient
    private LocalDateTime executionDate;
}
