package com.batch.job.readers;

import com.batch.model.CustomerErmDTO;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomerApiReader implements ItemReader<CustomerErmDTO> {

    private final RestTemplate restTemplate;
    private int nextCustomerIndex;
    private CustomerErmDTO[] customerData;

    public CustomerApiReader(RestTemplate webClient) {
        this.restTemplate = webClient;
        this.nextCustomerIndex = 0;
    }

    @Override
    public CustomerErmDTO read() throws Exception {
        if (customerDataIsNotInitialized()) {
            customerData = fetchCustomerDataFromAPI();
        }

        CustomerErmDTO nextCustomer = null;

        if (nextCustomerIndex < customerData.length) {
            nextCustomer = customerData[nextCustomerIndex];
            nextCustomerIndex++;
        }

        return nextCustomer;
    }

    private boolean customerDataIsNotInitialized() {
        return this.customerData == null;
    }

    private CustomerErmDTO[] fetchCustomerDataFromAPI() {
        return restTemplate.getForObject("https://jsonplaceholder.typicode.com/users",
                CustomerErmDTO[].class);
    }
}
