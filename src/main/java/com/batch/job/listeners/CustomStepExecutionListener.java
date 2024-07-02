package com.batch.job.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class CustomStepExecutionListener implements StepExecutionListener {

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        log.info("Step {} completed with the following statistics:", stepExecution.getStepName());
        log.info("  - Items read: {}", stepExecution.getReadCount());
        log.info("  - Items processed: {}", stepExecution.getWriteCount());
        log.info("  - Items written: {}", stepExecution.getWriteCount());
        return null;
    }
}
