package com.batch.entity.out;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "ERM_OUT_5")
public class CustomerErmOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerErmOutId;

    private String productLineName;
    private String arrangementId;
    private String productGroupName;
    private String productDescription;
    private String accountId;
    private String currencyId;
    private String accountIBAN;
    private LocalDateTime openingDate;
    private String companyName;
    private String customerReference;
    private LocalDateTime executionDate;

}

