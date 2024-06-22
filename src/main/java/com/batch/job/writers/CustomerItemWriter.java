package com.batch.job.writers;

import com.batch.entity.CustomerVigi;
import com.batch.repository.CustomerVigiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerItemWriter implements ItemWriter<CustomerVigi> {

    private final CustomerVigiRepository customerVigiRepository;

    @Override
    public void write(Chunk<? extends CustomerVigi> items) throws Exception {
        customerVigiRepository.saveAll(items);
    }
}
