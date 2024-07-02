package com.batch.job.processors;

import com.batch.entity.AccountErm;
import com.batch.entity.CustomerErm;
import com.batch.mappers.AccountErmMapper;
import com.batch.model.AccountErmDTO;
import com.batch.utils.ApiExecutionLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AccountErmItemProcessor implements ItemProcessor<CustomerErm, AccountErm> {

    private final RestClient restClient;
    private final AccountErmMapper accountErmMapper;
    private final ApiExecutionLogger apiExecutionLogger;

    @Override
    public AccountErm process(CustomerErm customer) throws Exception {
        String url = "http://localhost:3001/accounts/" + customer.getArrangementId();
        LocalDateTime startExecDate = LocalDateTime.now();
        try {
            AccountErmDTO accountErmDTO = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(AccountErmDTO.class);

            if (accountErmDTO != null) {
                AccountErm accountErm = accountErmMapper.toEntity(accountErmDTO);
                accountErm.setCustomerErm(customer);

                apiExecutionLogger.logApiExecution(url, null, null, null,
                        "200", null, startExecDate, LocalDateTime.now());
                return accountErm;
            }
        } catch (Exception e) {
            apiExecutionLogger.logApiExecution(url, null, null, null,
                    null, e.getMessage(), startExecDate, LocalDateTime.now());
            throw e;
        }
        return null;
    }
}