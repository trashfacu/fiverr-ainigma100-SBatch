package com.batch.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "API_EXECUTION_DETAILS")
public class ApiExecutionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "REQUEST_URL")
    private String requestUrl;

    @Column(name = "QUERY_PARAMETERS")
    private String queryParameters;

    @Column(name = "PATH_VARIABLES")
    private String pathVariables;

    @Column(name = "REQUEST_HEADERS")
    private String requestHeaders;

    @Column(name = "RESPONSE_HTTP_STATUS_CODE")
    private String responseHttpStatusCode;

    @Column(name = "EXCEPTION_MESSAGE")
    private String exceptionMessage;

    @Column(name = "START_EXEC_DATE")
    private LocalDateTime startExecDate;

    @Column(name = "END_EXEC_DATE")
    private LocalDateTime endExecDate;

    @Column(name = "DURATION_EXEC")
    private Long durationExec;
}
