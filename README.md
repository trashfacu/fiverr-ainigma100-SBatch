## Batch Processing Application

### Overview

This Spring Batch application demonstrates the process of fetching customer data from an API, processing it, and then storing it in a database. Additionally, it includes steps to fetch account data associated with customers, write both customer and account data to CSV files, and handle notifications upon job completion.

### Components

1. **Entities**:
    - `AccountVigi`: Represents an account with attributes such as ID, account number, balance, and a reference to `CustomerVigi`.
    - `CustomerVigi`: Represents a customer with attributes like ID, name, email, and country code.

2. **DTOs**:
    - `AccountVigiDTO`: Data Transfer Object for transferring account-related data.
    - `CustomerVigiDTO`: Data Transfer Object for transferring customer-related data.

3. **Readers**:
    - `CustomerApiReader`: Reads customer data from a REST API using `WebClient`.
    - `CustomerVigiItemReader`: Reads customer data from a database using Spring Data `RepositoryItemReader`.

4. **Processors**:
    - `AccountVigiItemProcessor`: Processes `CustomerVigi` objects to create `AccountVigi` entities, fetching account data from an API using `WebClient`.
    - `CustomerItemProcessor`: Processes `CustomerVigiDTO` objects to create `CustomerVigi` entities.

5. **Writers**:
    - `AccountItemWriter`: Writes `AccountVigi` entities to the database.
    - `CustomerItemWriter`: Writes `CustomerVigi` entities to the database.
    - CSV Writers (`AccountVigiCSVWriter` and `CustomerVigiCSVWriter`): Writes `AccountVigi` and `CustomerVigi` entities respectively to CSV files.

6. **Repositories**:
    - `AccountVigiRepository`: Spring Data repository for `AccountVigi` entities.
    - `CustomerVigiRepository`: Spring Data repository for `CustomerVigi` entities.

7. **Configuration**:
    - `BatchConfig`: Configures Spring Batch jobs and steps. Includes item readers, processors, writers, and listeners (`JobCompletionNotificationListener`).
    - `CSVReaderConfig`: Configures Spring Batch item readers for reading `AccountVigi` and `CustomerVigi` entities from the database.

8. **Controllers**:
    - `BatchController`: REST controller to trigger the batch job with parameters.

9. **Utilities**:
    - `WebClientConfig`: Configures a `WebClient` bean to communicate with external APIs.

### How to Use

1. **Setup**:
    - Ensure Java and Maven are installed.
    - Clone the repository and import into your IDE.
    - Configure `application.properties` or `application.yml` for database and other settings.

2. **Running the Application**:
    - Run the Spring Boot application.
    - Use a tool like Postman or cURL to send a POST request to `/start` endpoint with `country` parameter to start the batch job.

3. **Monitoring**:
    - Monitor logs for job status (`JobCompletionNotificationListener`) and exceptions.
    - Check database tables and CSV files (`OBS_OUT_4.csv` and `OBS_OUT_5.csv`) for stored and exported data respectively.

### Notes

- Ensure database connection and API availability before starting the job.
- Adjust batch sizes (`chunk` sizes) and configurations in `BatchConfig` as per performance and data volume requirements.
- Extend and customize processors, writers, and readers based on specific business logic and data sources.
