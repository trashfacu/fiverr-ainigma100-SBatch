package com.batch.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
public class CustomerErmDTO {
    private String productLineName;
    private String arrangementId;
    private String productGroupId;
    private String productGroupName;
    private String productId;
    private String productDescription;
    private String accountId;
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
    private String customerId;
    private String connectionStatus;
    private String connectionSubStatus;
    private ZonedDateTime nextRefreshAvailableAt;
    private String acctSwiftRef;
    private String extAccountNumber;

    private String countryCode;
}
