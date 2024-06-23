# Batch Processing Application

## Table of Contents

1. [Overview](#overview)
2. [Components](#components)
3. [Setup and Usage](#setup-and-usage)
4. [Monitoring and Troubleshooting](#monitoring-and-troubleshooting)
5. [Notes](#notes)

---

## Overview <a name="overview"></a>

This Spring Batch application demonstrates the process of fetching customer data from an API, processing it, and then storing it in a database. Additionally, it includes steps to fetch account data associated with customers, write both customer and account data to CSV files, and handle notifications upon job completion.

---

## Components <a name="components"></a>

### Entities

- **AccountVigi**: Represents an account with attributes such as ID, account number, balance, and a reference to `CustomerVigi`.
- **CustomerVigi**: Represents a customer with attributes like ID, name, email, and country code.
- **InvalidRecord**: Represents an invalid record with attributes like recordType, details, and timestamp.

### DTOs

- **AccountVigiDTO**: Data Transfer Object for transferring account-related data.
- **CustomerVigiDTO**: Data Transfer Object for transferring customer-related data.

### Readers

- **CustomerApiReader**: Reads customer data from a REST API using `WebClient`.
- **CustomerVigiItemReader**: Reads customer data from a database using Spring Data `RepositoryItemReader`.

### Processors

- **AccountVigiItemProcessor**: Processes `CustomerVigi` objects to create `AccountVigi` entities, fetching account data from an API using `WebClient`.
- **CustomerItemProcessor**: Processes `CustomerVigiDTO` objects to create `CustomerVigi` entities.

### Writers

- **AccountItemWriter**: Writes `AccountVigi` entities to the database.
- **CustomerItemWriter**: Writes `CustomerVigi` entities to the database.
- **CSV Writers**: Writes `AccountVigi` and `CustomerVigi` entities respectively to CSV files (`AccountVigiCSVWriter`, `CustomerVigiCSVWriter`).

### Repositories

- **AccountVigiRepository**: Spring Data repository for `AccountVigi` entities.
- **CustomerVigiRepository**: Spring Data repository for `CustomerVigi` entities.
- **InvalidRecordRepository**: Spring Data repository for `InvalidRecord` entities.

### Configuration

- **BatchConfig**: Configures Spring Batch jobs and steps. Includes item readers, processors, writers, and listeners (`JobCompletionNotificationListener`, `CustomSkipListener`, `CustomStepExecutionListener`).
- **CSVReaderConfig**: Configures Spring Batch item readers for reading `AccountVigi` and `CustomerVigi` entities from the database.
- **StepDecider**: Decides which step to start based on job parameters.

### Controllers

- **BatchController**: REST controller to trigger the batch job with parameters.

---

## Setup and Usage <a name="setup-and-usage"></a>

### Prerequisites

- Java and Maven installed.
- Database configured in `application.properties` or `application.yml`.

### Running the Application

1. Clone the repository and import it into your IDE.
2. Build and run the Spring Boot application.
3. Use a tool like Postman or cURL to send a POST request to start the batch job.

### Interacting with the Application

#### Starting a Batch Job

Send a POST request to `/start` endpoint with the following parameters:
- `country`: Specify the country for filtering customer data.
- `startStep` (optional): Specify the initial step to start the job.

Example using Postman:
- **URL**: `http://localhost:8080/start`
- **Method**: POST
- **Headers**: Content-Type: application/json
- **Body**: Raw JSON
  ```json
  {
    "country": "US",
    "startStep": "fetchAndSaveCustomerStep"
  }
