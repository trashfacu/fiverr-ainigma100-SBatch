package com.batch.configuration;

import com.batch.entity.AccountVigi;
import com.batch.entity.CustomerVigi;
import com.batch.exceptions.InvalidRecordException;
import com.batch.job.listeners.CustomSkipListener;
import com.batch.job.listeners.CustomStepExecutionListener;
import com.batch.job.listeners.JobCompletionNotificationListener;
import com.batch.job.processors.AccountVigiItemProcessor;
import com.batch.job.processors.CustomerItemProcessor;
import com.batch.job.readers.CustomerApiReader;
import com.batch.job.readers.CustomerVigiItemReader;
import com.batch.job.writers.AccountItemWriter;
import com.batch.job.writers.CustomerItemWriter;
import com.batch.job.writers.csv.AccountVigiCSVWriter;
import com.batch.job.writers.csv.CustomerVigiCSVWriter;
import com.batch.model.CustomerVigiDTO;
import com.batch.utils.StepDecider;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
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
    private final CustomSkipListener skipListener;
    private final CustomStepExecutionListener stepExecutionListener;
    private final StepDecider decider;


    @Bean
    @StepScope
    public RepositoryItemReader<CustomerVigi> customerItemReaderImp() {
        return customerItemReader.customerItemReader();
    }

    @Bean
    public JobExecutionDecider decider() {
        return new StepDecider();
    }

    @Bean
    public Step fetchAndSaveCustomerStep() {
        return new StepBuilder("fetchAndSaveCustomersStep", jobRepository)
                .<CustomerVigiDTO, CustomerVigi>chunk(10, transactionManager)
                .reader(customerApiReader)
                .processor(customerProcessor)
                .writer(customerWriter)
                .faultTolerant()
                .skip(InvalidRecordException.class)
                .skipLimit(100).listener(skipListener)
                .listener(stepExecutionListener)
                .build();
    }

    @Bean
    public Step fetchAndSaveAccountsStep() {
        return new StepBuilder("fetchAndSaveAccountsStep", jobRepository)
                .<CustomerVigi, AccountVigi>chunk(10, transactionManager)
                .reader(customerItemReaderImp())
                .processor(accountProcessor)
                .writer(accountWriter)
                .faultTolerant()
                .skip(InvalidRecordException.class)
                .skipLimit(100).listener(skipListener)
                .listener(stepExecutionListener)
                .build();
    }

    @Bean
    public Step writeAccountsToCsvStep() {
        return new StepBuilder("writeAccountsToCsvStep", jobRepository)
                .<AccountVigi, AccountVigi>chunk(10, transactionManager)
                .reader(csvReaderConfig.accountVigiReader(entityManagerFactory))
                .writer(accountVigiCSVWriter)
                .faultTolerant()
                .skip(InvalidRecordException.class)
                .skipLimit(100).listener(skipListener)
                .listener(stepExecutionListener)
                .build();
    }

    @Bean
    public Step writeCustomersToCsvStep() {
        return new StepBuilder("writeCustomersToCsvStep", jobRepository)
                .<CustomerVigi, CustomerVigi>chunk(10, transactionManager)
                .reader(csvReaderConfig.customerVigiReader(entityManagerFactory))
                .writer(customerVigiCSVWriter)
                .faultTolerant()
                .skipLimit(100).skip(InvalidRecordException.class)
                .listener(skipListener)
                .listener(stepExecutionListener)
                .build();
    }

    @Bean
    public Job processJob() {
        return new JobBuilder("processJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(decider())
                .on("fetchAndSaveCustomerStep").to(fetchAndSaveCustomerStep())
                .next(fetchAndSaveAccountsStep())
                .next(writeAccountsToCsvStep())
                .next(writeCustomersToCsvStep())
                .from(decider())
                .on("fetchAndSaveAccountsStep").to(fetchAndSaveAccountsStep())
                .next(writeAccountsToCsvStep())
                .next(writeCustomersToCsvStep())
                .from(decider())
                .on("writeAccountsToCsvStep").to(writeAccountsToCsvStep())
                .next(writeCustomersToCsvStep())
                .from(decider())
                .on("writeCustomersToCsvStep").to(writeCustomersToCsvStep())
                .end()
                .build();
    }
}
