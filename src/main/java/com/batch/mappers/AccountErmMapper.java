package com.batch.mappers;

import com.batch.entity.AccountErm;
import com.batch.entity.CustomerErm;
import com.batch.entity.out.AccountErmOut;
import com.batch.model.AccountErmDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AccountErmMapper {

    @Mapping(source = "dto.interestProperty", target = "interestProperty")
    @Mapping(source = "dto.compoundType", target = "compoundType")
    @Mapping(source = "dto.interestType", target = "interestType")
    @Mapping(source = "dto.interestRateType", target = "interestRateType")
    AccountErm toEntity(AccountErmDTO dto);

    @Mapping(source = "entity.interestType", target = "interestType")
    @Mapping(source = "entity.customerErm", target = "customerErm", qualifiedByName = "customerErmToString")
    @Mapping(source = "entity.executionDate", target = "executionDate")
    AccountErmOut toAccountErmOut(AccountErm entity);

    @Named("customerErmToString")
    default String customerErmToString(CustomerErm customerErm) {
        return customerErm != null ? customerErm.getArrangementId() : null;
    }
}