package com.batch.job.writers.csv;

import com.batch.entity.AccountErm;
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
public class AccountErmCSVWriter implements ItemWriter<AccountErm> {

    private final FlatFileItemWriter<AccountErm> writer;


    public AccountErmCSVWriter() {
        this.writer = new FlatFileItemWriterBuilder<AccountErm>()
                .name("AccountErmCSVWriter")
                .resource(new FileSystemResource("ERM_OUT_4.csv"))
                .delimited()
                .delimiter(",")
                .names("interestType", "customerErm", "executionDate")
                .lineAggregator(new DelimitedLineAggregator<AccountErm>() {{
                    setFieldExtractor(new BeanWrapperFieldExtractor<>() {{
                        setNames(new String[]{"interestType","customerErm.customerId", "executionDate"});
                    }});
                }})
                .headerCallback(w -> w.write("interestType,customerErm,executionDate"))
                .build();
        this.writer.open(new ExecutionContext());
    }

    @Override
    public void write(Chunk<? extends AccountErm> items) throws Exception {
        for (AccountErm item : items){
            item.setExecutionDate(LocalDateTime.now());
        }
        writer.write(items);
    }
}
