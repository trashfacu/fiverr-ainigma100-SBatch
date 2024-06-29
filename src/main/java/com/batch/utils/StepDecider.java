package com.batch.utils;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

@Component
public class StepDecider implements JobExecutionDecider {
    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        String startStep = jobExecution.getJobParameters().getString("startStep");

        if (startStep != null && !startStep.isEmpty()) {
            return new FlowExecutionStatus(startStep);
        } else {
            return new FlowExecutionStatus("fetchAndSaveCustomerStep");
        }

    }
}