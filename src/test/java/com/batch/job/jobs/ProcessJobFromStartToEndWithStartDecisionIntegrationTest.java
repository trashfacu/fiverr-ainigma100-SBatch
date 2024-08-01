package com.batch.job.jobs;

import com.batch.Application;
import com.batch.ApplicationTest;
import com.batch.configuration.BatchConfig;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@SpringBatchTest
@ContextConfiguration(classes = {Application.class, BatchConfig.class})
class ProcessJobFromStartToEndWithStartDecisionIntegrationTest extends ApplicationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private Job processJobFromStartToEndWithStartDecision;

    @BeforeEach
    public void setUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    @SneakyThrows
    void testProcessJobWithStartDecisionFetchAndSaveCustomer() {
        // Arrange
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("country", "testCountry")
                .addString("startStep", "fetchAndSaveCustomerStep")
                .addString("run.id", LocalDateTime.now().toString())
                .toJobParameters();
        // Act
        JobExecution jobExecution = jobLauncherTestUtils.getJobLauncher().run(processJobFromStartToEndWithStartDecision, jobParameters);

        // Assert
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }

    @Test
    @SneakyThrows
    void testProcessJobWithStartDecisionFetchAndSaveAccounts() {
        // Arrange
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("country", "testCountry")
                .addString("startStep", "fetchAndSaveAccountsStep")
                .addString("run.id", LocalDateTime.now().toString())
                .toJobParameters();
        // Act
        JobExecution jobExecution = jobLauncherTestUtils.getJobLauncher().run(processJobFromStartToEndWithStartDecision, jobParameters);

        // Assert
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }

    @Test
    @SneakyThrows
    void testProcessJobWithStartDecisionWriteAccountsToCsv() {
        // Arrange
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("country", "testCountry")
                .addString("startStep", "writeAccountsToCsvStep")
                .addString("run.id", LocalDateTime.now().toString())
                .toJobParameters();
        // Act
        JobExecution jobExecution = jobLauncherTestUtils.getJobLauncher().run(processJobFromStartToEndWithStartDecision, jobParameters);

        // Assert
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }

    @Test
    @SneakyThrows
    void testProcessJobWithStartDecisionWriteCustomersToCsv() {
        // Arrange
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("country", "testCountry")
                .addString("startStep", "writeCustomersToCsvStep")
                .addString("run.id", LocalDateTime.now().toString())
                .toJobParameters();
        // Act
        JobExecution jobExecution = jobLauncherTestUtils.getJobLauncher().run(processJobFromStartToEndWithStartDecision, jobParameters);

        // Assert
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }

    @Test
    @SneakyThrows
    void testStartBatchStartToEnd() {
        // Arrange
        String country = "Greece";
        String startStep = "fetchAndSaveCustomerStep";
        // Act & Assert
        mockMvc.perform(post("/start")
                        .param("country", country)
                        .param("startStep", startStep))
                .andExpect(status().isOk())
                .andExpect(content().string("Job started startBatchStartToEnd successfully"));
    }

}
