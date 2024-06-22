package com.batch.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountVigiDTO {
    private String accountNumber;
    private BigDecimal balance;
}
