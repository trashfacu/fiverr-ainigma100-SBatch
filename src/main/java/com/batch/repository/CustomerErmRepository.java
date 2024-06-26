package com.batch.repository;

import com.batch.entity.CustomerErm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerErmRepository extends JpaRepository<CustomerErm, Long> {
}
