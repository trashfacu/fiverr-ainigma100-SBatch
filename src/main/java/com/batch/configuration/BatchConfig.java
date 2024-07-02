package com.batch.configuration;

import com.batch.entity.AccountErm;
import com.batch.entity.CustomerErm;
import com.batch.entity.out.AccountErmOut;
import com.batch.entity.out.CustomerErmOut;
import com.batch.exceptions.ApiDataFetchException;
import com.batch.exceptions.InvalidRecordException;
import com.batch.job.listeners.CustomSkipListener;
import com.batch.job.listeners.CustomStepExecutionListener;
import com.batch.job.listeners.JobCompletionNotificationListener;
import com.batch.job.processors.AccountErmItemProcessor;
import com.batch.job.processors.CustomerErmItemProcessor;
import com.batch.job.processors.out.AccountToErmOut4Processor;
import com.batch.job.processors.out.CustomerToErmOut5Processor;
import com.batch.job.readers.CustomerApiReader;
import com.batch.job.readers.out.ErmTablesReader;
import com.batch.job.readers.out.OutErmReader;
import com.batch.job.writers.AccountItemWriter;
import com.batch.job.writers.CustomerItemWriter;
import com.batch.job.writers.csv.CsvFileWriter;
import com.batch.job.writers.out.AccountErmOutWriter;
import com.batch.job.writers.out.CustomerErmOutWriter;
import com.batch.model.CustomerErmDTO;
import com.batch.utils.StepDecider;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final CustomerApiReader customerApiReader;
    private final CustomerErmItemProcessor customerProcessor;
    private final CustomerItemWriter customerWriter;
    private final AccountErmItemProcessor accountProcessor;
    private final AccountItemWriter accountWriter;
    private final JobCompletionNotificationListener listener;
    private final CsvFileWriter csvFileWriter;
    private final EntityManagerFactory entityManagerFactory;
    private final ErmTablesReader ermTablesReader;
    private final CustomSkipListener skipListener;
    private final CustomStepExecutionListener stepExecutionListener;
    //OUT
    private final OutErmReader outErmReader;
    private final AccountToErmOut4Processor accountToErmOut4Processor;
    private final CustomerToErmOut5Processor customerToErmOut5Processor;
    private final AccountErmOutWriter accountErmOutWriter;
    private final CustomerErmOutWriter customerErmOutWriter;

    @Bean
    public JobExecutionDecider decider() {
        return new StepDecider();
    }

    @Bean
    public Step fetchAndSaveCustomerStep() {
        return new StepBuilder("fetchAndSaveCustomersStep", jobRepository)
                .<CustomerErmDTO, CustomerErm>chunk(10, transactionManager)
                .reader(customerApiReader)
                .processor(customerProcessor)
                .writer(customerWriter)
                .faultTolerant()
                .skip(InvalidRecordException.class)
                .skip(ApiDataFetchException.class)
                .skipLimit(100).listener(skipListener)
                .listener(stepExecutionListener)
                .build();
    }

    @Bean
    public Step fetchAndSaveAccountsStep() {
        return new StepBuilder("fetchAndSaveAccountsStep", jobRepository)
                .<CustomerErm, AccountErm>chunk(10, transactionManager)
                .reader(ermTablesReader.customerErmReader(entityManagerFactory))
                .processor(accountProcessor)
                .writer(accountWriter)
                .faultTolerant()
                .skip(InvalidRecordException.class)
                .skip(ApiDataFetchException.class)
                .skipLimit(100).listener(skipListener)
                .listener(stepExecutionListener)
                .build();
    }

    @Bean
    public Step writeAccountOutTablesToDb() {
        return new StepBuilder("step-WriteAccountOutTablesToDb", jobRepository)
                .<AccountErm, AccountErmOut>chunk(10, transactionManager)
                .reader(ermTablesReader.accountErmReader(entityManagerFactory))
                .processor(accountToErmOut4Processor)
                .writer(accountErmOutWriter)
                .listener(stepExecutionListener)
                .build();
    }

    @Bean
    public Step writeCustomerOutTablesToDb() {
        return new StepBuilder("step-WriteCustomerOutTablesToDb", jobRepository)
                .<CustomerErm, CustomerErmOut>chunk(10, transactionManager)
                .reader(ermTablesReader.customerErmReader(entityManagerFactory))
                .processor(customerToErmOut5Processor)
                .writer(customerErmOutWriter)
                .listener(stepExecutionListener)
                .build();
    }

    @Bean
    public Step writeAccountsToCsvStep() {
        return new StepBuilder("step-writeAccountsToCsvStep", jobRepository)
                .<AccountErmOut, AccountErmOut>chunk(10, transactionManager)
                .reader(outErmReader.accountErmOutReader(entityManagerFactory))
                .writer(csvFileWriter.accountErmOutCsvWriter())
                .listener(stepExecutionListener)
                .build();
    }

    @Bean
    public Step writeCustomersToCsvStep() {
        return new StepBuilder("step-writeCustomersToCsvStep", jobRepository)
                .<CustomerErmOut, CustomerErmOut>chunk(10, transactionManager)
                .reader(outErmReader.customerErmOutReader(entityManagerFactory))
                .writer(csvFileWriter.customerErmOutCsvWriter())
                .faultTolerant()
                .listener(stepExecutionListener)
                .build();
    }

    @Bean
    public Job processJobFromStartToEndWithStartDecision() {
        return new JobBuilder("job-processJobFromStartToEndWithStartDecision", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(decider())
                .on("fetchAndSaveCustomerStep").to(fetchAndSaveCustomerStep())
                .next(fetchAndSaveAccountsStep())
                .next(writeCustomerOutTablesToDb())
                .next(writeAccountOutTablesToDb())
                .next(writeAccountsToCsvStep())
                .next(writeCustomersToCsvStep())
                .from(decider())
                .on("fetchAndSaveAccountsStep").to(fetchAndSaveAccountsStep())
                .next(writeCustomerOutTablesToDb())
                .next(writeAccountOutTablesToDb())
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

    @Bean
    public Job processJobOnlyFetchApiToDb() {
        return new JobBuilder("job-processJobOnlyFetchApiToDb", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(fetchAndSaveCustomerStep())
                .next(fetchAndSaveAccountsStep())
                .end().build();
    }

    @Bean
    public Job onlyFetchDataFromOutTablesAndGenerateCsv() {
        return new JobBuilder("job-OnlyFetchDataFromOutTablesAndGenerateCsv", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(writeCustomerOutTablesToDb())
                .next(writeAccountOutTablesToDb())
                .next(writeCustomersToCsvStep())
                .next(writeAccountsToCsvStep())
                .end().build();
    }
}