package com.batch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Data
@Table(name = "CUSTOMER_ERM")
public class CustomerErm {

    @Id
    private String accountId;

    private String customerId;
    private String arrangementId;
    private String productLineName;
    private String productGroupId;
    private String productGroupName;
    private String productId;
    private String productDescription;
    private String shortTitle;
    private String categoryId;
    private String companyCode;
    private String currencyId;
    private String sortCode;
    private String accountIBAN;
    private double workingBalance;
    private LocalDate openingDate;
    private String companyName;
    private String preferredProduct;
    private String preferredProductPosition;
    private String preferredProductLabel;
    private String permission;
    private double onlineActualBalance;
    private double availableBalance;
    private double availableBalanceWithLimit;
    private double outstandingAmount;
    private double paidOutAmount;
    private String arrangementStatus;
    private double totalPrincipal;
    private String customerReference;
    private String statement;
    private String portfolioId;
    private String connectionId;
    private ZonedDateTime balancesLastUpdated;
    private ZonedDateTime transactionsLastUpdated;
    private String bankLogo;
    private String extSourceProvider;
    private String connectionStatus;
    private String connectionSubStatus;
    private ZonedDateTime nextRefreshAvailableAt;
    private String acctSwiftRef;
    private String extAccountNumber;

    private String countryCode;
    @Transient
    private LocalDateTime executionDate;
}

