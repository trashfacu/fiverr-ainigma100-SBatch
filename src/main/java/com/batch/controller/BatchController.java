package com.batch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class BatchController {

    private final JobLauncher jobLauncher;
    private final Job processJob;

    @PostMapping("/start")
    public ResponseEntity<String> startBatch(@RequestParam String country,
                                             @RequestParam(required = false, defaultValue = "fetchAndSaveCustomerStep") String startStep,
                                             @RequestParam int pageSize) {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("country", country)
                    .addString("startStep", startStep)
                    .addString("run.id", LocalDateTime.now().toString())
                    .addString("pageSize", String.valueOf(pageSize))
                    .toJobParameters();
            jobLauncher.run(processJob, jobParameters);
            return new ResponseEntity<>("Job started successfully", HttpStatus.OK);
        } catch (JobInstanceAlreadyCompleteException | JobExecutionAlreadyRunningException |
                 JobParametersInvalidException | JobRestartException e) {
            return new ResponseEntity<>("Job failed to start",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
