package com.batch.repository;

import com.batch.entity.ApiExecutionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiExecutionDetailsRepository extends JpaRepository<ApiExecutionDetails, Long> {
}
