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
        log.error("Skipped item during read due to: {}", t.getMessage());
    }

    @Override
    public void onSkipInWrite(Object item, Throwable t) {
        log.error("Skipped item during write: {}", item);
    }

    @Override
    public void onSkipInProcess(Object item, Throwable t) {
        InvalidRecord invalidRecord = new InvalidRecord();
        invalidRecord.setRecordType(item.getClass().getSimpleName());
        invalidRecord.setDetails(item.toString());
        invalidRecord.setTimeStamp(LocalDateTime.now());
        invalidRecord.setExceptionType(t.getClass().getSimpleName());
        invalidRecord.setExceptionMessage("Skipped due to name starting with c");
        invalidRecord.setStepName(getStepName());
        invalidRecordRepository.save(invalidRecord);
    }

    // Helper method to get the current step name from the contextx
    private String getStepName() {
        return StepSynchronizationManager.getContext() != null ? StepSynchronizationManager.getContext().getStepName() : "Unknown Step";
    }
}
