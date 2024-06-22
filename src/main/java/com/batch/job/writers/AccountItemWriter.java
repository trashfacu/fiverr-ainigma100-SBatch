package com.batch.job.writers;

import com.batch.entity.AccountVigi;
import com.batch.repository.AccountVigiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountItemWriter implements ItemWriter<AccountVigi> {

    private final AccountVigiRepository accountVigiRepository;

    @Override
    public void write(Chunk<? extends AccountVigi> items) throws Exception {
        accountVigiRepository.saveAll(items);
    }
}
