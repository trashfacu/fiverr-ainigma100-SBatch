package com.batch.job.readers;

import com.batch.entity.CustomerErm;
import com.batch.repository.CustomerErmRepository;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomerErmItemReader {

    private final CustomerErmRepository customerErmRepository;

    public CustomerErmItemReader(CustomerErmRepository customerErmRepository) {
        this.customerErmRepository = customerErmRepository;
    }

    @Bean
    public RepositoryItemReader<CustomerErm> customerItemReader() {
        RepositoryItemReader<CustomerErm> reader = new RepositoryItemReader<>();
        reader.setRepository(customerErmRepository);
        reader.setMethodName("findAll");
        reader.setPageSize(10);
        reader.setSort(Collections.singletonMap("id", Sort.Direction.ASC));
        return reader;
    }
}
