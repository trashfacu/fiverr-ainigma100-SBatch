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
class ProcessOnlyFetchDataFromOutTablesAndGenerateCsv extends ApplicationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private Job processOnlyFetchDataFromOutTablesAndGenerateCsv;

    @BeforeEach
    public void setUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    @SneakyThrows
    void testOnlyFetchDataFromOutTablesAndGenerateCsvJob() {
        // Arrange
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("country", "testCountry")
                .addString("run.id", LocalDateTime.now().toString())
                .toJobParameters();

        // Act
        JobExecution jobExecution = jobLauncherTestUtils.getJobLauncher().run(processOnlyFetchDataFromOutTablesAndGenerateCsv, jobParameters);

        // Assert
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }

    @Test
    @SneakyThrows
    void testStartOnlyFetchDataFromOutTablesAndGenerateCsvJob() {

        //Arrange
        String country = "Argentina";

        // Act & Assert
        mockMvc.perform(post("/csvGen")
                        .param("country",country))
                .andExpect(status().isOk())
                .andExpect(content().string("Job startBatchCsvWriting started successfully"));
    }
}