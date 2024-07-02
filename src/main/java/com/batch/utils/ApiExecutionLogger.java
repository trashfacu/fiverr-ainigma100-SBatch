package com.batch.utils;

import com.batch.entity.ApiExecutionDetails;
import com.batch.repository.ApiExecutionDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ApiExecutionLogger {

    private final ApiExecutionDetailsRepository repository;

    public void logApiExecution(String requestUrl, String queryParameters, String pathVariables,
                                String requestHeaders, String responseHttpStatusCode, String exceptionMessage,
                                LocalDateTime startExecDate, LocalDateTime endExecDate) {

        ApiExecutionDetails details = new ApiExecutionDetails();
        details.setRequestUrl(requestUrl);
        details.setQueryParameters(queryParameters);
        details.setPathVariables(pathVariables);
        details.setRequestHeaders(requestHeaders);
        details.setResponseHttpStatusCode(responseHttpStatusCode);
        details.setExceptionMessage(exceptionMessage);
        details.setStartExecDate(startExecDate);
        details.setEndExecDate(endExecDate);
        details.setDurationExec(java.time.Duration.between(startExecDate, endExecDate).toMillis());

        repository.save(details);

    }
}
