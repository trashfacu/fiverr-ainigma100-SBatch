package com.batch.repository.out;

import com.batch.entity.out.CustomerErmOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerErmOutRepository extends JpaRepository<CustomerErmOut, Long> {
}
