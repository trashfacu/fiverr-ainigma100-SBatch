package com.batch.job.writers.csv;

import com.batch.entity.AccountVigi;
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
import java.time.format.DateTimeFormatter;

@Component
public class AccountVigiCSVWriter implements ItemWriter<AccountVigi> {

    private final FlatFileItemWriter<AccountVigi> writer;


    public AccountVigiCSVWriter() {
        this.writer = new FlatFileItemWriterBuilder<AccountVigi>()
                .name("accountVigiCsvWriter")
                .resource(new FileSystemResource("OBS_OUT_4.csv"))
                .delimited()
                .delimiter(",")
                .names(new String[]{"id", "customerName", "balance", "executionDate"})
                .lineAggregator(new DelimitedLineAggregator<AccountVigi>() {{
                    setFieldExtractor(new BeanWrapperFieldExtractor<>() {{
                        setNames(new String[]{"id", "customer.name", "balance","executionDate"});
                    }});
                }})
                .headerCallback(w -> w.write("id,customerName,balance,executionDate"))
                .build();
        this.writer.open(new ExecutionContext());
    }

    @Override
    public void write(Chunk<? extends AccountVigi> items) throws Exception {
        for (AccountVigi item : items){
            item.setExecutionDate(LocalDateTime.now());
        }
        writer.write(items);
    }
}
