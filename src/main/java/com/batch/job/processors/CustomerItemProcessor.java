package com.batch.job.processors;

import com.batch.entity.CustomerVigi;
import com.batch.exceptions.InvalidRecordException;
import com.batch.model.CustomerVigiDTO;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class CustomerItemProcessor implements ItemProcessor<CustomerVigiDTO, CustomerVigi> {

    @Value("#{jobParameters['country']}")
    private String country;

    @Override
    public CustomerVigi process(CustomerVigiDTO item) throws Exception {
        if (item.getName() == null || item.getName().isEmpty() || item.getName().toLowerCase().startsWith("c")) {
            throw new InvalidRecordException();
        }

        CustomerVigi customer = new CustomerVigi();
        customer.setName(item.getName());
        customer.setEmail(item.getEmail());
        customer.setCountryCode(country);
        return customer;
    }
}
