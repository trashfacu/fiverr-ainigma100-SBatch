package com.batch.repository;

import com.batch.entity.AccountVigi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountVigiRepository extends JpaRepository<AccountVigi, Long> {
}
