package com.batch.job.readers;

import com.batch.entity.CustomerVigi;
import com.batch.repository.CustomerVigiRepository;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomerVigiItemReader {

    private final CustomerVigiRepository customerVigiRepository;

    public CustomerVigiItemReader(CustomerVigiRepository customerVigiRepository) {
        this.customerVigiRepository = customerVigiRepository;
    }

    @Bean
    public RepositoryItemReader<CustomerVigi> customerItemReader() {
        RepositoryItemReader<CustomerVigi> reader = new RepositoryItemReader<>();
        reader.setRepository(customerVigiRepository);
        reader.setMethodName("findAll");
        reader.setPageSize(10);
        reader.setSort(Collections.singletonMap("id", Sort.Direction.ASC));
        return reader;
    }
}
