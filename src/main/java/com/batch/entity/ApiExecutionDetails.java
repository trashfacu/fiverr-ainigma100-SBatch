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

    // URL of the request
    @Column(name = "requestUrl")
    private String requestUrl;

    // Query parameters
    @Column(name = "queryParameters")
    private String queryParameters;

    // Path variables
    @Column(name = "pathVariables")
    private String pathVariables;

    // Request headers
    @Column(name = "requestHeaders")
    private String requestHeaders;

    // HTTP status code of the response
    @Column(name = "responseHttpStatusCode")
    private String responseHttpStatusCode;

    // Exception message, if any
    @Column(name = "exceptionMessage")
    private String exceptionMessage;

    // Start time of the request
    @Column(name = "startExecDate")
    private LocalDateTime startExecDate;

    // End time of the request
    @Column(name = "endExecDate")
    private LocalDateTime endExecDate;

    // Duration of the request
    @Column(name = "durationExec")
    private Long durationExec;
}
