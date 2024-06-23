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
    private String details;
    private LocalDateTime timeStamp;

}