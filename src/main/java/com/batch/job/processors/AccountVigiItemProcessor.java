package com.batch.job.processors;

import com.batch.entity.AccountVigi;
import com.batch.entity.CustomerVigi;
import com.batch.model.AccountVigiDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class AccountVigiItemProcessor implements ItemProcessor<CustomerVigi, AccountVigi> {

    private final WebClient webClient;

    @Override
    public AccountVigi process(CustomerVigi customer) throws Exception {
        AccountVigiDTO accountVigiDTO = webClient.get()
                .uri("/users/" + customer.getId())
                .retrieve()
                .bodyToMono(AccountVigiDTO.class)
                .block();

        AccountVigi account = new AccountVigi();
        account.setCustomer(customer);
        account.setAccountNumber(accountVigiDTO.getAccountNumber());
        account.setBalance(accountVigiDTO.getBalance());
        return account;
    }
}
