package com.batch.repository;

import com.batch.entity.AccountErm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountErmRepository extends JpaRepository<AccountErm, Long> {
}
