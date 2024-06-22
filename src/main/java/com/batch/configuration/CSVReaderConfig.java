package com.batch.configuration;

import com.batch.entity.AccountVigi;
import com.batch.entity.CustomerVigi;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CSVReaderConfig {

    @Bean
    public ItemReader<CustomerVigi> customerVigiReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<CustomerVigi>()
                .name("customerVigiReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT c FROM CustomerVigi c")
                .pageSize(10)
                .build();
    }

    @Bean
    public ItemReader<AccountVigi> accountVigiReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<AccountVigi>()
                .name("accountVigiReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT a FROM AccountVigi a")
                .pageSize(10)
                .build();
    }

}
