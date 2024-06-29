package com.batch.job.processors.out;

import com.batch.entity.AccountErm;
import com.batch.entity.out.AccountErmOut;
import com.batch.mappers.AccountErmMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountToErmOut4Processor implements ItemProcessor<AccountErm, AccountErmOut> {

    private final AccountErmMapper mapper;

    @Override
    public AccountErmOut process(AccountErm item) throws Exception {
        return mapper.toAccountErmOut(item);
    }
}
