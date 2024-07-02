package com.batch.job.writers.out;

import com.batch.entity.out.CustomerErmOut;
import com.batch.repository.out.CustomerErmOutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerErmOutWriter implements ItemWriter<CustomerErmOut> {

    private final CustomerErmOutRepository ermOutRepository;

    @Override
    public void write(Chunk<? extends CustomerErmOut> chunk) throws Exception {
        ermOutRepository.saveAll(chunk);
    }
}
