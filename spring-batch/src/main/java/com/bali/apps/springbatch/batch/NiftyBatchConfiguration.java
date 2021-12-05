package com.bali.apps.springbatch.batch;

import com.bali.apps.springbatch.entity.Nifty;
import com.bali.apps.springbatch.model.NiftyDTO;
import com.bali.apps.springbatch.processor.NiftyItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class NiftyBatchConfiguration {

    private static final String QUERY_INSERT_NIFTY = "INSERT " +
            "INTO nifty (id,company_name,industry,symbol,isin_code) " +
            "VALUES (:id, :companyName, :industry, :symbol, :isinCode)";

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<NiftyDTO> reader() {
        return new FlatFileItemReaderBuilder<NiftyDTO>()
                .name("nifty50-csv-reader")
                .resource(new ClassPathResource("static/nifty50list.csv"))
                .linesToSkip(1)
                .delimited()
                .names("companyName", "industry", "symbol", "series", "isinCode")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<NiftyDTO>() {{
                    setTargetType(NiftyDTO.class);
                }})
                .build();
    }

    @Bean
    public NiftyItemProcessor processor() {
        return new NiftyItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Nifty> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Nifty>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql(QUERY_INSERT_NIFTY)
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importNiftyJob(Step step) {
        return jobBuilderFactory.get("importNiftyJob")
                .incrementer(new RunIdIncrementer())
                .flow(step)
                .end()
                .build();
    }

    @Bean
    public Step step(JdbcBatchItemWriter<Nifty> writer) {
        return stepBuilderFactory.get("step")
                .<NiftyDTO, Nifty> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

}
