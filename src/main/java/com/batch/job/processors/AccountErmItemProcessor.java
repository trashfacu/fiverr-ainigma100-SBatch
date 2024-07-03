package com.batch.job.processors;

import com.batch.entity.AccountErm;
import com.batch.entity.CustomerErm;
import com.batch.mappers.AccountErmMapper;
import com.batch.model.AccountErmDTO;
import com.batch.utils.ApiExecutionListener;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AccountErmItemProcessor implements ItemProcessor<CustomerErm, AccountErm> {

    private final RestClient restClient;
    private final AccountErmMapper accountErmMapper;
    private final ApiExecutionListener apiExecutionListener;

    @Override
    public AccountErm process(CustomerErm customer) throws Exception {
        String url = "http://localhost:3001/accounts";
        String pathVariables = "/" + customer.getArrangementId();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type","application/json");
        headers.set("Accept", "application/json");


        LocalDateTime startExecDate = LocalDateTime.now();
        try {
            AccountErmDTO accountErmDTO = restClient.get()
                    .uri(url+pathVariables)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .retrieve()
                    .body(AccountErmDTO.class);

            LocalDateTime endExecDate = LocalDateTime.now();

            apiExecutionListener.logExecutionDetails(url, null, pathVariables, headers.toString(),
                    "200", null, startExecDate, endExecDate);


            if (accountErmDTO != null) {
                AccountErm accountErm = accountErmMapper.toEntity(accountErmDTO);
                accountErm.setCustomerErm(customer);
                return accountErm;
            }
        } catch (Exception e) {
            LocalDateTime endExecDate = LocalDateTime.now();
            apiExecutionListener.logExecutionDetails(url, null, pathVariables, headers.toString(),
                    "4xx", e.getMessage(), startExecDate, endExecDate);
            throw e;
        }
        return null;
    }
}