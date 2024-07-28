package com.batch.job.readers;

import com.batch.exceptions.ApiDataFetchException;
import com.batch.model.CustomerApiResponse;
import com.batch.model.CustomerErmDTO;
import com.batch.utils.ApiExecutionListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
@StepScope
public class CustomerApiReader implements ItemReader<CustomerErmDTO> {

    private final RestClient restClient;
    private final ApiExecutionListener apiExecutionListener;
    private Iterator<CustomerErmDTO> currentBatchIterator;
    int nextPageIndex;
    private int totalPages;

    public CustomerApiReader(RestClient restClient, ApiExecutionListener apiExecutionListener) {
        this.restClient = restClient;
        this.apiExecutionListener = apiExecutionListener;
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

    private void fetchCustomerDataFromAPI() throws ApiDataFetchException {
        String url = "http://localhost:3000/products";
        String queryParameters = "_page=" + nextPageIndex;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        headers.set("X-Pagination-Page", String.valueOf(nextPageIndex));
        LocalDateTime startExecDate = LocalDateTime.now();

        try {
            ResponseEntity<CustomerApiResponse> response = restClient.get()
                    .uri(url + "?" + queryParameters)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .retrieve()
                    .toEntity(CustomerApiResponse.class);

            CustomerApiResponse apiResponse = response.getBody();
            LocalDateTime endExecDate = LocalDateTime.now();

            apiExecutionListener.logExecutionDetails(url, queryParameters, null, headers.toString(), String.valueOf(response.getStatusCode().value()), null, startExecDate, endExecDate);

            if (apiResponse != null) {
                List<CustomerErmDTO> customerData = apiResponse.getData();
                totalPages = apiResponse.getLast();
                nextPageIndex++;
                currentBatchIterator = customerData.iterator();
            }
        } catch (Exception e) {
            LocalDateTime endExecDate = LocalDateTime.now();
            apiExecutionListener.logExecutionDetails(url, queryParameters, null, headers.toString(), "400", e.getMessage(), startExecDate, endExecDate);
            log.error("Error while fetching customer data: {}", e.getMessage());
            throw new ApiDataFetchException("Error while fetching customer data", e.getCause());
        }
    }
}
