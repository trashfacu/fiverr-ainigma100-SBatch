package com.batch.job.writers;

import com.batch.entity.AccountErm;
import com.batch.repository.AccountErmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountItemWriter implements ItemWriter<AccountErm> {

    private final AccountErmRepository accountErmRepository;

    @Override
    public void write(Chunk<? extends AccountErm> items) throws Exception {
        accountErmRepository.saveAll(items);
    }
}
