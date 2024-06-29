package com.batch.job.readers;

import com.batch.model.CustomerApiResponse;
import com.batch.model.CustomerErmDTO;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;

@Component
@StepScope
public class CustomerApiReader implements ItemReader<CustomerErmDTO> {

    private final RestTemplate restTemplate;
    private Iterator<CustomerErmDTO> currentBatchIterator;
    private int nextPageIndex;
    private int totalPages;
    public CustomerApiReader(RestTemplate webClient) {
        this.restTemplate = webClient;
        this.nextPageIndex = 1;
        this.totalPages = Integer.MAX_VALUE; // Initially set to a high value to ensure first API call
    }

    @Override
    public CustomerErmDTO read() throws Exception {
        if (currentBatchIterator == null || !currentBatchIterator.hasNext()) {
            if (nextPageIndex <= totalPages) {
                fetchCustomerDataFromAPI();
            } else {
                return null; //there is no more data to read
            }
        }
        return currentBatchIterator != null && currentBatchIterator.hasNext() ? currentBatchIterator.next() : null;
    }

    private void fetchCustomerDataFromAPI() {
        String url = "http://localhost:3000/products?_page=" + nextPageIndex;
        ResponseEntity<CustomerApiResponse> response = restTemplate.getForEntity(url, CustomerApiResponse.class);
        CustomerApiResponse apiResponse = response.getBody();

        if (apiResponse != null) {
            List<CustomerErmDTO> customerData = apiResponse.getData();
            totalPages = apiResponse.getLast();
            nextPageIndex++;
            currentBatchIterator = customerData.iterator();
        }
    }
}