package com.batch.mappers;

import com.batch.entity.CustomerErm;
import com.batch.model.CustomerErmDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerErmMapper {
    @Mapping(source = "dto.arrangementId", target = "arrangementId")
    @Mapping(source = "dto.productLineName", target = "productLineName")
    @Mapping(source = "dto.productGroupId", target = "productGroupId")
    @Mapping(source = "dto.productGroupName", target = "productGroupName")
    @Mapping(source = "dto.productId", target = "productId")
    @Mapping(source = "dto.productDescription", target = "productDescription")
    @Mapping(source = "dto.accountId", target = "accountId")
    @Mapping(source = "dto.shortTitle", target = "shortTitle")
    @Mapping(source = "dto.categoryId", target = "categoryId")
    @Mapping(source = "dto.companyCode", target = "companyCode")
    @Mapping(source = "dto.currencyId", target = "currencyId")
    @Mapping(source = "dto.sortCode", target = "sortCode")
    @Mapping(source = "dto.accountIBAN", target = "accountIBAN")
    @Mapping(source = "dto.workingBalance", target = "workingBalance")
    @Mapping(source = "dto.openingDate", target = "openingDate")
    @Mapping(source = "dto.companyName", target = "companyName")
    @Mapping(source = "dto.preferredProduct", target = "preferredProduct")
    @Mapping(source = "dto.preferredProductPosition", target = "preferredProductPosition")
    @Mapping(source = "dto.preferredProductLabel", target = "preferredProductLabel")
    @Mapping(source = "dto.permission", target = "permission")
    @Mapping(source = "dto.onlineActualBalance", target = "onlineActualBalance")
    @Mapping(source = "dto.availableBalance", target = "availableBalance")
    @Mapping(source = "dto.availableBalanceWithLimit", target = "availableBalanceWithLimit")
    @Mapping(source = "dto.outstandingAmount", target = "outstandingAmount")
    @Mapping(source = "dto.paidOutAmount", target = "paidOutAmount")
    @Mapping(source = "dto.arrangementStatus", target = "arrangementStatus")
    @Mapping(source = "dto.totalPrincipal", target = "totalPrincipal")
    @Mapping(source = "dto.customerReference", target = "customerReference")
    @Mapping(source = "dto.statement", target = "statement")
    @Mapping(source = "dto.portfolioId", target = "portfolioId")
    @Mapping(source = "dto.connectionId", target = "connectionId")
    @Mapping(source = "dto.balancesLastUpdated", target = "balancesLastUpdated")
    @Mapping(source = "dto.transactionsLastUpdated", target = "transactionsLastUpdated")
    @Mapping(source = "dto.bankLogo", target = "bankLogo")
    @Mapping(source = "dto.extSourceProvider", target = "extSourceProvider")
    @Mapping(source = "dto.customerId", target = "customerId")
    @Mapping(source = "dto.connectionStatus", target = "connectionStatus")
    @Mapping(source = "dto.connectionSubStatus", target = "connectionSubStatus")
    @Mapping(source = "dto.nextRefreshAvailableAt", target = "nextRefreshAvailableAt")
    @Mapping(source = "dto.acctSwiftRef", target = "acctSwiftRef")
    @Mapping(source = "dto.extAccountNumber", target = "extAccountNumber")
    @Mapping(source = "countryCode", target = "countryCode")
    CustomerErm toEntity(CustomerErmDTO dto, String countryCode);

}
