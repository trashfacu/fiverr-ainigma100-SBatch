package com.batch.job.processors.out;

import com.batch.entity.AccountErm;
import com.batch.entity.out.AccountErmOut;
import com.batch.mappers.AccountErmMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountToErmOut4Processor implements ItemProcessor<AccountErm, AccountErmOut> {

    private final AccountErmMapper mapper;

    @Override
    public AccountErmOut process(AccountErm item) throws Exception {
        AccountErmOut accountErmOut = mapper.toAccountErmOut(item);
        accountErmOut.setExecutionDate(LocalDateTime.now());
        return accountErmOut;
    }
}
