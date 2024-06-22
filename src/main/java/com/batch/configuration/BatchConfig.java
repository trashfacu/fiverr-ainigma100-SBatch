package com.batch.configuration;

import com.batch.entity.AccountVigi;
import com.batch.entity.CustomerVigi;
import com.batch.job.processors.AccountVigiItemProcessor;
import com.batch.job.processors.CustomerItemProcessor;
import com.batch.job.readers.CustomerApiReader;
import com.batch.job.readers.CustomerVigiItemReader;
import com.batch.job.writers.AccountItemWriter;
import com.batch.job.writers.CustomerItemWriter;
import com.batch.job.writers.csv.AccountVigiCSVWriter;
import com.batch.job.writers.csv.CustomerVigiCSVWriter;
import com.batch.model.CustomerVigiDTO;
import com.batch.utils.JobCompletionNotificationListener;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final CustomerApiReader customerApiReader;
    private final CustomerItemProcessor customerProcessor;
    private final CustomerItemWriter customerWriter;
    private final CustomerVigiItemReader customerItemReader;
    private final AccountVigiItemProcessor accountProcessor;
    private final AccountItemWriter accountWriter;
    private final JobCompletionNotificationListener listener;
    private final CustomerVigiCSVWriter customerVigiCSVWriter;
    private final AccountVigiCSVWriter accountVigiCSVWriter;
    private final EntityManagerFactory entityManagerFactory;
    private final CSVReaderConfig csvReaderConfig;


    @Bean
    @StepScope
    public RepositoryItemReader<CustomerVigi> customerItemReaderImp() {
        return customerItemReader.customerItemReader();
    }

    @Bean
    public Step fetchAndSaveCustomerStep() {
        return new StepBuilder("fetchAndSaveCustomersStep", jobRepository)
                .<CustomerVigiDTO, CustomerVigi>chunk(10, transactionManager)
                .reader(customerApiReader)
                .processor(customerProcessor)
                .writer(customerWriter)
                .build();
    }

    @Bean
    public Step fetchAndSaveAccountsStep() {
        return new StepBuilder("fetchAndSaveAccountsStep", jobRepository)
                .<CustomerVigi, AccountVigi>chunk(10, transactionManager)
                .reader(customerItemReaderImp())
                .processor(accountProcessor)
                .writer(accountWriter)
                .build();
    }

    @Bean
    public Step writeAccountsToCsvStep() {
        return new StepBuilder("writeAccountsToCsvStep", jobRepository)
                .<AccountVigi, AccountVigi>chunk(10, transactionManager)
                .reader(csvReaderConfig.accountVigiReader(entityManagerFactory))
                .writer(accountVigiCSVWriter)
                .build();
    }

    @Bean
    public Step writeCustomersToCsvStep() {
        return new StepBuilder("writeCustomersToCsvStep", jobRepository)
                .<CustomerVigi, CustomerVigi>chunk(10, transactionManager)
                .reader(csvReaderConfig.customerVigiReader(entityManagerFactory))
                .writer(customerVigiCSVWriter)
                .build();
    }

    @Bean
    public Job processJob() {
        return new JobBuilder("processJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(fetchAndSaveCustomerStep())
                .next(fetchAndSaveAccountsStep())
                .next(writeAccountsToCsvStep())
                .next(writeCustomersToCsvStep())
                .build();
    }
}
