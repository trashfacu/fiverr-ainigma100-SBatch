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

    private String customerId;
    private String arrangementId;
    private String productLineName;
    private String productGroupId;
    private String productGroupName;
    private String productDescription;
    private String shortTitle;
    private String categoryId;
    private String companyCode;
    private String sortCode;
    private LocalDateTime executionDate;

}