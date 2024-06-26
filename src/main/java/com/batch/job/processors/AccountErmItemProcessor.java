package com.batch.job.processors;

import com.batch.entity.AccountErm;
import com.batch.entity.CustomerErm;
import com.batch.mappers.AccountErmMapper;
import com.batch.model.AccountErmDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AccountErmItemProcessor implements ItemProcessor<CustomerErm, AccountErm> {

    private final RestTemplate restTemplate;
    private final AccountErmMapper accountErmMapper;

    @Override
    public AccountErm process(CustomerErm customer) throws Exception {
        AccountErmDTO accountErmDTO = restTemplate
                .getForObject("https://jsonplaceholder.typicode.com/users" + customer.getId()
                        , AccountErmDTO.class);

        AccountErm account = accountErmMapper.toEntity(accountErmDTO);
        account.setCustomer(customer);
        return account;
    }
}