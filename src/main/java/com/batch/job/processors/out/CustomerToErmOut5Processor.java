package com.batch.job.processors.out;

import com.batch.entity.CustomerErm;
import com.batch.entity.out.CustomerErmOut;
import com.batch.mappers.CustomerErmMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerToErmOut5Processor implements ItemProcessor<CustomerErm, CustomerErmOut> {

    private final CustomerErmMapper mapper;

    @Override
    public CustomerErmOut process(CustomerErm item) throws Exception {
        return mapper.toCustomerErmOut(item);
    }

}
