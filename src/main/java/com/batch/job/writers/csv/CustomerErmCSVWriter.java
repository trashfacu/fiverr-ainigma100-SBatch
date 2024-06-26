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
                .names("id", "name", "email", "executionDate")
                .lineAggregator(new DelimitedLineAggregator<CustomerErm>() {{
                    setFieldExtractor(new BeanWrapperFieldExtractor<>() {{
                        setNames(new String[]{"id", "name", "email","executionDate"});
                    }});
                }})
                .headerCallback(w -> w.write("id,name,email,executionDate"))
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
