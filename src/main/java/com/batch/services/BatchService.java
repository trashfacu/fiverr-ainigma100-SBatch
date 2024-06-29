package com.batch.services;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BatchService {

    private final JobLauncher jobLauncher;
    private final Job processJobFromStartToEndWithStartDecision;
    private final Job onlyFetchDataFromOutTablesAndGenerateCsv;
    private final Job processJobOnlyFetchApiToDb;

    @Async
    public void startBatchStartToEnd(String country, String startStep) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("country", country)
                .addString("startStep", startStep)
                .addString("run.id", LocalDateTime.now().toString())
                .toJobParameters();
        jobLauncher.run(processJobFromStartToEndWithStartDecision, jobParameters);
    }

    @Async
    public void startBatchOnlyFetchApiToDb(String country) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("country", country)
                .addString("run.id", LocalDateTime.now().toString())
                .toJobParameters();
        jobLauncher.run(processJobOnlyFetchApiToDb, jobParameters);
    }

    @Async
    public void startBatchCsvWriting(String country) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("country", country)
                .addString("run.id", LocalDateTime.now().toString())
                .toJobParameters();
        jobLauncher.run(onlyFetchDataFromOutTablesAndGenerateCsv, jobParameters);
    }


}
