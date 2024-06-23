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
        String stepName = jobExecution.getJobParameters().getString("startStep");

        return new FlowExecutionStatus(stepName != null ? stepName : "fetchAndSaveCustomerStep");
    }
}
