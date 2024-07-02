package com.batch.job.writers.out;

import com.batch.entity.out.AccountErmOut;
import com.batch.repository.out.AccountErmOutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountErmOutWriter implements ItemWriter<AccountErmOut> {

    private final AccountErmOutRepository ermOutRepository;

    @Override
    public void write(Chunk<? extends AccountErmOut> chunk) throws Exception {
        ermOutRepository.saveAll(chunk);
    }
}