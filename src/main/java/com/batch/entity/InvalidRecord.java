package com.batch.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "INVALID_RECORD")
public class InvalidRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recordType; //customer or account
    private String details; // the full record
    private String exceptionType; // the type of the exception
    private String exceptionMessage; // the detailed exception message
    private String stepName; // the step where the error occurred
    private LocalDateTime timeStamp;

}