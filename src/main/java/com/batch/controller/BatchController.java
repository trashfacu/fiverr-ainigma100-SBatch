package com.batch.controller;

import com.batch.services.BatchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BatchController {

    private final BatchService batchService;

    @PostMapping("/start")
    public ResponseEntity<String> startBatch(@RequestParam @NotNull String country,
                                             @RequestParam(required = false, defaultValue = "fetchAndSaveCustomerStep") String startStep) {
        try {
            batchService.startBatchStartToEnd(country, startStep);
            return new ResponseEntity<>("Job started startBatchStartToEnd successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Job failed to start",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/onlyFetch")
    public ResponseEntity<String> startBatchOnlyFetch(@RequestParam @NotNull String country) {
        try {
            batchService.startBatchOnlyFetchApiToDb(country);
            return new ResponseEntity<>("Job onlyFetch started successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Job failed to start",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/csvGen")
    public ResponseEntity<String> startCsvGen(@RequestParam @NotNull String country) {
        try {
            batchService.startBatchCsvWriting(country);
            return new ResponseEntity<>("Job startBatchCsvWriting started successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Job failed to start",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}