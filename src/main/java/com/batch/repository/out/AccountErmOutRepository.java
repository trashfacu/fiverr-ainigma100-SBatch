package com.batch.repository.out;

import com.batch.entity.out.AccountErmOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountErmOutRepository extends JpaRepository<AccountErmOut, Long> {
}
