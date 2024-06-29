package com.batch.job.readers.out;

import com.batch.entity.AccountErm;
import com.batch.entity.CustomerErm;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OutErmReader {

    @Bean
    public ItemReader<CustomerErm> customerErmReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<CustomerErm>()
                .name("customerErmReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT c FROM CustomerErm c")
                .pageSize(10)
                .build();
    }

    @Bean
    public ItemReader<AccountErm> accountErmReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<AccountErm>()
                .name("accountErmReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT a FROM AccountErm a")
                .pageSize(10)
                .build();
    }

}
