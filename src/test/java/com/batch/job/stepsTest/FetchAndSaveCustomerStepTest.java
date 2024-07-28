package com.batch.job.stepsTest;

import com.batch.entity.CustomerErm;
import com.batch.exceptions.InvalidRecordException;
import com.batch.job.processors.CustomerErmItemProcessor;
import com.batch.job.readers.CustomerApiReader;
import com.batch.job.writers.CustomerItemWriter;
import com.batch.model.CustomerApiResponse;
import com.batch.model.CustomerErmDTO;
import com.batch.repository.CustomerErmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBatchTest
@SpringBootTest
class FetchAndSaveCustomerStepTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private Job processJobOnlyFetchApiToDb;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private CustomerErmRepository customerErmRepository;

    @Autowired
    private CustomerApiReader customerApiReader;

    @Autowired
    private CustomerErmItemProcessor customerProcessor;

    @Autowired
    private CustomerItemWriter customerWriter;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jobLauncherTestUtils.setJob(processJobOnlyFetchApiToDb);
    }

    @Test
    void testCustomerApiReader() throws Exception {
        // Prepare test data
        CustomerErmDTO dto = new CustomerErmDTO();
        dto.setCustomerId("CUST1001");
        dto.setCompanyName("ABC Bank");

        CustomerApiResponse apiResponse = new CustomerApiResponse();
        apiResponse.setData(List.of(dto));
        apiResponse.setLast(1);

        // Mock the RestTemplate behavior
        when(restTemplate.getForEntity(anyString(), eq(CustomerApiResponse.class)))
                .thenReturn(ResponseEntity.ok(apiResponse));

        // Test the reader
        CustomerErmDTO result = customerApiReader.read();
        assertNotNull(result);
        assertEquals("CUST1001", result.getCustomerId());
        assertEquals("ABC Bank", result.getCompanyName());
    }

    @Test
    void testCustomerErmItemProcessor() throws Exception {
        CustomerErmDTO dto = new CustomerErmDTO();
        dto.setCustomerId("1");
        dto.setCompanyName("Test Company");

        CustomerErm result = customerProcessor.process(dto);
        assertNotNull(result);
        assertEquals("1", result.getCustomerId());
        assertEquals("Test Company", result.getCompanyName());
    }

    @Test
    void testCustomerErmItemProcessorInvalidRecord() {
        CustomerErmDTO dto = new CustomerErmDTO();
        dto.setCustomerId("");
        dto.setCompanyName("Test Company");

        assertThrows(InvalidRecordException.class, () -> customerProcessor.process(dto));
    }

    @Test
    void testCustomerItemWriter() throws Exception {
        CustomerErm customer1 = new CustomerErm();
        customer1.setCustomerId("1");
        CustomerErm customer2 = new CustomerErm();
        customer2.setCustomerId("2");

        Chunk<CustomerErm> chunk = new Chunk<>(Arrays.asList(customer1, customer2));
        customerWriter.write(chunk);

        verify(customerErmRepository, times(1)).saveAll(chunk);
    }

    @Test
    void testSkipInvalidRecord() throws Exception {
        // Prepare test data
        CustomerErmDTO validDto = new CustomerErmDTO();
        validDto.setCustomerId("1");
        validDto.setCompanyName("Valid Company");

        CustomerErmDTO invalidDto = new CustomerErmDTO();
        invalidDto.setCustomerId("");
        invalidDto.setCompanyName("Invalid Company");

        CustomerApiResponse apiResponse = new CustomerApiResponse();
        apiResponse.setData(Arrays.asList(validDto, invalidDto));

    }

    @Test
    void testFetchAndSaveCustomerStep() throws Exception {
        // Prepare test data
        CustomerApiResponse apiResponse = getCustomerApiResponse();

        // Mock the RestTemplate behavior
        when(restTemplate.getForEntity(anyString(), eq(CustomerApiResponse.class)))
                .thenReturn(ResponseEntity.ok(apiResponse));

        // Execute the job
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        // Verify the job execution
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());

        // Capture the argument being saved
        ArgumentCaptor<Chunk<CustomerErm>> captor = ArgumentCaptor.forClass(Chunk.class);
        verify(customerErmRepository, times(1)).saveAll(captor.capture());

        // Get the captured Chunk
        Chunk<CustomerErm> capturedChunk = captor.getValue();

        // Get the list of CustomerErm objects
        List<CustomerErm> savedCustomers = capturedChunk.getItems();

        // Verify the contents
        assertEquals(3, savedCustomers.size());

        // Verify each CustomerErm object
        CustomerErm savedCustomer1 = savedCustomers.get(0);
        assertEquals("CUST1001", savedCustomer1.getCustomerId());
        assertEquals("ABC Bank", savedCustomer1.getCompanyName());

        CustomerErm savedCustomer2 = savedCustomers.get(1);
        assertEquals("CUST1002", savedCustomer2.getCustomerId());
        assertEquals("XYZ Bank", savedCustomer2.getCompanyName());

        CustomerErm savedCustomer3 = savedCustomers.get(2);
        assertEquals("CUST1003", savedCustomer3.getCustomerId());
        assertEquals("LMN Bank", savedCustomer3.getCompanyName());
    }

    private static CustomerApiResponse getCustomerApiResponse() {
        CustomerErmDTO dto1 = new CustomerErmDTO();
        dto1.setCustomerId("CUST1001");
        dto1.setCompanyName("ABC Bank");

        CustomerErmDTO dto2 = new CustomerErmDTO();
        dto2.setCustomerId("CUST1002");
        dto2.setCompanyName("XYZ Bank");

        CustomerErmDTO dto3 = new CustomerErmDTO();
        dto3.setCustomerId("CUST1003");
        dto3.setCompanyName("LMN Bank");

        CustomerApiResponse apiResponse = new CustomerApiResponse();
        apiResponse.setData(Arrays.asList(dto1, dto2, dto3));
        apiResponse.setLast(1);
        return apiResponse;
    }
}