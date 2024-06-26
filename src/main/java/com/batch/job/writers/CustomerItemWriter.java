package com.batch.job.writers;

import com.batch.entity.CustomerErm;
import com.batch.repository.CustomerErmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerItemWriter implements ItemWriter<CustomerErm> {

    private final CustomerErmRepository customerErmRepository;

    @Override
    public void write(Chunk<? extends CustomerErm> items) throws Exception {
        customerErmRepository.saveAll(items);
    }
}
