package com.batch.job.listeners;

import com.batch.entity.InvalidRecord;
import com.batch.repository.InvalidRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.scope.context.StepSynchronizationManager;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomSkipListener implements SkipListener<Object, Object> {

    private final InvalidRecordRepository invalidRecordRepository;

    @Override
    public void onSkipInRead(Throwable t) {
        log.error("Skipped item during read due to: {}", t.getMessage(), t); // Log stack trace for detailed debugging
    }

    @Override
    public void onSkipInWrite(Object item, Throwable t) {
        log.error("Skipped item during write: {}", item, t);  // Log stack trace
    }

    @Override
    public void onSkipInProcess(Object item, Throwable t) {
        InvalidRecord invalidRecord = new InvalidRecord();
        invalidRecord.setRecordType(item.getClass().getSimpleName());

        // Use a safer truncation strategy for details
        String details = item.toString();
        int maxDetailsLength = 255;
        if (details.length() > maxDetailsLength) {
            details = details.substring(0, maxDetailsLength - 3) + "...";
        }
        invalidRecord.setDetails(details);

        invalidRecord.setTimeStamp(LocalDateTime.now());
        invalidRecord.setExceptionType(t.getClass().getSimpleName());

        // Capture the full exception message
        invalidRecord.setExceptionMessage(t.getMessage());

        invalidRecord.setStepName(getStepName());

        log.error("Skipped item in step '{}' due to {}: {}",
                invalidRecord.getStepName(), t.getClass().getSimpleName(), t.getMessage());

        invalidRecordRepository.save(invalidRecord);
    }


    private String getStepName() {
        return StepSynchronizationManager.getContext() != null ? StepSynchronizationManager.getContext().getStepName() : "Unknown Step";
    }
}
