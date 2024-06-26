package com.batch.mappers;

import com.batch.entity.AccountErm;
import com.batch.model.AccountErmDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AccountErmMapper {

    @Mapping(source = "dto.accountNumber", target = "accountNumber")
    @Mapping(source = "dto.balance", target = "balance")
    AccountErm toEntity(AccountErmDTO dto);

    @Mapping(source = "entity.accountNumber", target = "accountNumber")
    @Mapping(source = "entity.balance", target = "balance")
    AccountErmDTO toDto(AccountErm entity);
}
