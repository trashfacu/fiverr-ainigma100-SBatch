package com.batch.job.readers;

import com.batch.model.CustomerApiResponse;
import com.batch.model.CustomerErmDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;
@Slf4j
@Component
@StepScope
public class CustomerApiReader implements ItemReader<CustomerErmDTO> {

    private final RestTemplate restTemplate;
    private Iterator<CustomerErmDTO> currentBatchIterator;
    private int nextPageIndex;
    private int totalPages;

    public CustomerApiReader(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.nextPageIndex = 1;
        this.totalPages = Integer.MAX_VALUE; // Initially set to a high value to ensure first API call
    }

    @Override
    public CustomerErmDTO read() throws Exception {
        if (currentBatchIterator == null || !currentBatchIterator.hasNext()) {
            if (nextPageIndex <= totalPages) {
                fetchCustomerDataFromAPI();
            } else {
                return null; // There is no more data to read
            }
        }
        return currentBatchIterator != null && currentBatchIterator.hasNext() ? currentBatchIterator.next() : null;
    }

    private void fetchCustomerDataFromAPI() {
        String url = "http://localhost:3000/products?_page=" + nextPageIndex;
        try {
            ResponseEntity<CustomerApiResponse> response = restTemplate.getForEntity(url, CustomerApiResponse.class);
            CustomerApiResponse apiResponse = response.getBody();

            if (apiResponse != null) {
                List<CustomerErmDTO> customerData = apiResponse.getData();
                totalPages = apiResponse.getLast();
                nextPageIndex++;
                currentBatchIterator = customerData.iterator();
            }
        } catch (HttpClientErrorException e) {
            // Handle HTTP 4xx errors
            log.error("Client error while fetching customer data: {}", e.getMessage());
            throw e;
        } catch (HttpServerErrorException e) {
            // Handle HTTP 5xx errors
            log.error("Server error while fetching customer data: {}", e.getMessage());
            throw e;
        } catch (ResourceAccessException e) {
            log.error("Network error while fetching customer data: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while fetching customer data: {}", e.getMessage());
            throw e;
        }
    }
}
