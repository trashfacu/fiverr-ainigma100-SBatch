package com.batch.repository;

import com.batch.entity.InvalidRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidRecordRepository extends JpaRepository<InvalidRecord, Long> {
}
