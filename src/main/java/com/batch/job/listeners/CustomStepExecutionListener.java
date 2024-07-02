package com.batch.job.listeners;

import com.batch.entity.ExceptionEntity;
import com.batch.job.exceptions.ApiDataFetchException;
import com.batch.repository.ExceptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
@Slf4j
@RequiredArgsConstructor
public class CustomStepExecutionListener implements StepExecutionListener {

    private final ExceptionRepository exceptionRepository;


    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        if (!stepExecution.getFailureExceptions().isEmpty()) {
            stepExecution.getFailureExceptions().forEach(throwable -> {
                log.error("Exception occurred in step: {}", stepExecution.getStepName(), throwable);

                String exceptionMessage = throwable.getMessage();

                if (throwable instanceof ApiDataFetchException) {
                    exceptionMessage = "Error fetching customer data: " + exceptionMessage;
                }

                ExceptionEntity exception = new ExceptionEntity();
                exception.setStepName(stepExecution.getStepName());
                exception.setExceptionMessage(exceptionMessage);
                exception.setTimeStamp(LocalDateTime.now());
                exceptionRepository.save(exception);

            });
            return stepExecution.getExitStatus();
        }


        log.info("Step {} completed with the following statistics:", stepExecution.getStepName());
        log.info("  - Items read: {}", stepExecution.getReadCount());
        log.info("  - Items processed: {}", stepExecution.getWriteCount());
        log.info("  - Items written: {}", stepExecution.getWriteCount());
        return null;
    }
}
