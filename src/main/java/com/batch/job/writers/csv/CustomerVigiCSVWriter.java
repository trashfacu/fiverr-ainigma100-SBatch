package com.batch.job.writers.csv;

import com.batch.entity.CustomerVigi;
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
public class CustomerVigiCSVWriter implements ItemWriter<CustomerVigi> {

    private final FlatFileItemWriter<CustomerVigi> writer;


    public CustomerVigiCSVWriter() {
        this.writer = new FlatFileItemWriterBuilder<CustomerVigi>()
                .name("customerVigiCsvWriter")
                .resource(new FileSystemResource("OBS_OUT_5.csv"))
                .delimited()
                .delimiter(",")
                .names(new String[]{"id", "name", "email", "executionDate"})
                .lineAggregator(new DelimitedLineAggregator<CustomerVigi>() {{
                    setFieldExtractor(new BeanWrapperFieldExtractor<>() {{
                        setNames(new String[]{"id", "name", "email","executionDate"});
                    }});
                }})
                .headerCallback(w -> w.write("id,name,email,executionDate"))
                .build();
        this.writer.open(new ExecutionContext());
    }


    @Override
    public void write(Chunk<? extends CustomerVigi> items) throws Exception {
        for (CustomerVigi item : items){
            item.setExecutionDate(LocalDateTime.now());
        }
        writer.write(items);
    }
}
