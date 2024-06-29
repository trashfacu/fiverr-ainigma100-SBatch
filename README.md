[![Github Status][github-shield]][github-url]

# Batch Processing Application

## Pre-requirements

+ Maven
+ Java 21
+ H2 Database

## Deployment

```bash
  mvn clean
```
```bash
  mvn install -DskipTests
```

## Endpoints

- **POST `/start`**
- Starts the batch process from a specified step.
- **Parameters:**
- `country` (required): The country code for which the batch job should run.
- `startStep` (optional, default: "fetchAndSaveCustomerStep"): The step name to start execution from.
- **Example:**
 ```
 curl -X POST "http://localhost:8080/start?country=us&startStep=fetchAndSaveCustomerStep"
 ```

- **POST `/onlyFetch`**
- Initiates a batch job to fetch data from APIs and store it in the database.
- **Parameters:**
- `country` (required): The country code for which the batch job should run.
- **Example:**
 ```
 curl -X POST "http://localhost:8080/onlyFetch?country=us"
 ```

- **POST `/csvGen`**
- Triggers a batch job to generate CSV files.
- **Parameters:**
- `country` (required): The country code for which the batch job should run.
- **Example:**
 ```
 curl -X POST "http://localhost:8080/csvGen?country=us"
 ```

### Using JsonServer

You can use JsonServer to simulate APIs for testing purposes, inside resources/mock-data there are two files for that. Here are two examples:

- **db1.json** launched with `json-server --watch db1.json -p 3000`
- **db2.json** launched with `json-server --watch db2.json -p 3001`

### Notes

- Replace `localhost:8080` with the appropriate hostname and port where your application is deployed.
- Ensure all required parameters (`country`) are provided when making requests to the endpoints.


[github-shield]: https://img.shields.io/badge/GitHub-trashfacu-blue?logo=github&style=flat
[github-url]: https://github.com/trashfacu/RantMyGameAPI