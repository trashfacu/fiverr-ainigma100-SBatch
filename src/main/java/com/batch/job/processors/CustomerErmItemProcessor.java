package com.batch.job.processors;

import com.batch.entity.CustomerErm;
import com.batch.job.exceptions.InvalidRecordException;
import com.batch.mappers.CustomerErmMapper;
import com.batch.model.CustomerErmDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
@RequiredArgsConstructor
public class CustomerErmItemProcessor implements ItemProcessor<CustomerErmDTO, CustomerErm> {

    @Value("#{jobParameters['country']}")
    private String country;
    private final CustomerErmMapper customerErmMapper;

    @Override
    public CustomerErm process(CustomerErmDTO dto) throws Exception {
        if (dto.getCustomerId() == null || dto.getCustomerId().isEmpty() || dto.getCompanyName().toLowerCase().startsWith("facu")) {
            throw new InvalidRecordException();
        }
        return customerErmMapper.toEntity(dto, country);
    }
}