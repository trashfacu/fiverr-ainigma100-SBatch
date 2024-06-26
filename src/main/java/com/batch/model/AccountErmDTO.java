package com.batch.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountErmDTO {
    private String accountNumber;
    private BigDecimal balance;
}
