package com.batch.job.readers.out;

import com.batch.entity.out.AccountErmOut;
import com.batch.entity.out.CustomerErmOut;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OutErmReader {

    @Bean
    public ItemReader<CustomerErmOut> customerErmOutReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<CustomerErmOut>()
                .name("customerErmOutReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT c FROM CustomerErmOut c")
                .pageSize(10)
                .build();
    }

    @Bean
    public ItemReader<AccountErmOut> accountErmOutReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<AccountErmOut>()
                .name("accountErmOutReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT a FROM AccountErmOut a")
                .pageSize(10)
                .build();
    }

}
