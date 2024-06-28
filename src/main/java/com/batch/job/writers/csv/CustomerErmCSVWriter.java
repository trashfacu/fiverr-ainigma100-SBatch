package com.batch.job.writers.csv;

import com.batch.entity.CustomerErm;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomerErmCSVWriter implements ItemWriter<CustomerErm> {

    private final FlatFileItemWriter<CustomerErm> writer;

    public CustomerErmCSVWriter() {
        this.writer = new FlatFileItemWriterBuilder<CustomerErm>()
                .name("CustomerErmCSVWriter")
                .resource(new FileSystemResource("ERM_OUT_5.csv"))
                .delimited()
                .delimiter(",")
                .names("ex_productLineName", "ex_arrangementId", "ex_productGroupName", "ex_productDescription",
                        "ex_accountId", "ex_currencyId", "ex_accountIBAN", "ex_openingDate", "ex_companyName",
                        "ex_customerReference", "executionDate")
                .lineAggregator(new DelimitedLineAggregator<CustomerErm>() {{
                    setFieldExtractor(new BeanWrapperFieldExtractor<>() {{
                        setNames(new String[]{"productLineName", "arrangementId", "productGroupName", "productDescription",
                                "accountId", "currencyId", "accountIBAN", "openingDate", "companyName",
                                "customerReference", "executionDate"});
                    }});
                }})
                .headerCallback(w -> w.write("ex_productLineName,ex_arrangementId,ex_productGroupName," +
                        "ex_productDescription,ex_accountId,ex_currencyId,ex_accountIBAN," +
                        "ex_openingDate,ex_companyName,ex_customerReference,executionDate"))
                .build();
        this.writer.open(new ExecutionContext());
    }
    
    @Override
    public void write(Chunk<? extends CustomerErm> items) throws Exception {
        for (CustomerErm item : items){
            item.setExecutionDate(LocalDateTime.now());
        }
        writer.write(items);
    }
}
