package com.batch.job.readers;

import com.batch.model.CustomerVigiDTO;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CustomerApiReader implements ItemReader<CustomerVigiDTO> {

    private final WebClient webClient;
    private int nextCustomerIndex;
    private CustomerVigiDTO[] customerData;

    public CustomerApiReader(WebClient webClient) {
        this.webClient = webClient;
        this.nextCustomerIndex = 0;
    }

    @Override
    public CustomerVigiDTO read() throws Exception {
        if (customerDataIsNotInitialized()) {
            customerData = fetchCustomerDataFromAPI();
        }

        CustomerVigiDTO nextCustomer = null;

        if (nextCustomerIndex < customerData.length) {
            nextCustomer = customerData[nextCustomerIndex];
            nextCustomerIndex++;
        }

        return nextCustomer;
    }

    private boolean customerDataIsNotInitialized() {
        return this.customerData == null;
    }

    private CustomerVigiDTO[] fetchCustomerDataFromAPI() {
        return webClient.get()
                .uri("/users")
                .retrieve()
                .bodyToMono(CustomerVigiDTO[].class)
                .block();
    }
}
