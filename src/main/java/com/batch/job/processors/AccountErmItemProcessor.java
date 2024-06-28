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
                .getForObject("http://localhost:3001/accounts/" + customer.getArrangementId()
                        , AccountErmDTO.class);

        if (accountErmDTO != null){
            AccountErm accountErm = accountErmMapper.toEntity(accountErmDTO);
            accountErm.setCustomerErm(customer);
            return accountErm;
        }
        return null;
    }
}