package com.batch.mappers;

import com.batch.entity.CustomerErm;
import com.batch.model.CustomerErmDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CustomerErmMapper {

    @Mapping(source = "countryCode", target = "countryCode")
    @Mapping(source = "dto.name", target = "name")
    @Mapping(source = "dto.email", target = "email")
    CustomerErm toEntity(CustomerErmDTO dto, String countryCode);

    @Mapping(source = "countryCode", target = "countryCode")
    @Mapping(source = "entity.name", target = "name")
    @Mapping(source = "entity.email", target = "email")
    CustomerErmDTO toDto(CustomerErm entity, String countryCode);

}
