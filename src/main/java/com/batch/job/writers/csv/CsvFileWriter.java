package com.batch.job.writers.csv;

import com.batch.entity.out.AccountErmOut;
import com.batch.entity.out.CustomerErmOut;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class CsvFileWriter {

    @Bean
    public FlatFileItemWriter<CustomerErmOut> customerErmOutCsvWriter() {
        return new FlatFileItemWriterBuilder<CustomerErmOut>()
                .name("customerErmOutCsvWriter")
                .resource(new FileSystemResource("output/erm_out_5.csv"))
                .delimited()
                .delimiter(",")
                .names("customerErmOutId", "productLineName", "arrangementId", "productGroupName", "productDescription",
                        "accountId", "currencyId", "accountIBAN", "openingDate", "companyName", "customerReference", "executionDate")
                .build();
    }

    @Bean
    public FlatFileItemWriter<AccountErmOut> accountErmOutCsvWriter() {
        return new FlatFileItemWriterBuilder<AccountErmOut>()
                .name("accountErmOutCsvWriter")
                .resource(new FileSystemResource("output/erm_out_4.csv"))
                .delimited()
                .delimiter(",")
                .names("accountErmOutId", "interestType", "customerErm", "executionDate")
                .build();
    }

}
