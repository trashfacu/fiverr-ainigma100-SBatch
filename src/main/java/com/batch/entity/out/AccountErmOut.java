package com.batch.entity.out;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "ERM_OUT_4")
public class AccountErmOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountErmOutId;

    private String interestType;
    private String customerErm;
    private LocalDateTime executionDate;
}
