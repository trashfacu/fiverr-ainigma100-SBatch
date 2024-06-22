package com.batch.repository;

import com.batch.entity.CustomerVigi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerVigiRepository extends JpaRepository<CustomerVigi, Long> {
}
