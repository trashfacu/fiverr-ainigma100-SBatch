package com.batch.job.processors;

import com.batch.entity.CustomerErm;
import com.batch.exceptions.InvalidRecordException;
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
    public CustomerErm process(CustomerErmDTO item) throws Exception {
        if (item.getName() == null || item.getName().isEmpty() || item.getName().toLowerCase().startsWith("c")) {
            throw new InvalidRecordException();
        }
        return customerErmMapper.toEntity(item, country);
    }
}
