package com.batch.mappers;

import com.batch.entity.AccountErm;
import com.batch.model.AccountErmDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountErmMapper {

    @Mapping(source = "dto.interestProperty", target = "interestProperty")
    @Mapping(source = "dto.compoundType", target = "compoundType")
    @Mapping(source = "dto.interestType", target = "interestType")
    @Mapping(source = "dto.interestRateType", target = "interestRateType")
    AccountErm toEntity(AccountErmDTO dto);

}
