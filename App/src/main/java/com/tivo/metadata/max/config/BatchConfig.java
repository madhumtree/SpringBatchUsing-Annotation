package com.tivo.metadata.max.config;
 
import java.util.List;

import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.tivo.metadata.max.RowMapper.ProgramRowMapper;
import com.tivo.metadata.max.model.Employee;
import com.tivo.metadata.max.model.Program;
import com.tivo.metadata.max.processors.ProgramProccessor;
import com.tivo.metadata.max.tasklets.*;
import com.tivo.metadata.max.writer.ProgramWriter;
import com.tivo.metadata.max.processors.ScheduleProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
     
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
 
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
 
	
	  @Autowired 
	  public ProgramProccessor Proccessor;
	  
	
	  @Qualifier("programReader")
	  @Autowired public JdbcCursorItemReader programReader;
	  
	  @Qualifier("ScheduleReader")
	  @Autowired public JdbcCursorItemReader ScheduleReader;
	  
	 
	 
	 @Autowired
	 public ProgramWriter ProgramWriter;
	 
	 @Autowired 
	 public ScheduleProcessor ScheduleProcessor;
	 
	/*
	 * @Autowired public JsonFileItemWriter JsonFileItemWriter;
	 */
    
      @Autowired 
      public TruncateXumoAssociationTasklet TruncateMaxAssociationTasklet;
    
      @Autowired 
      public InsertXumoAssociationTasklet insertxumoassociationtasklet;
    
    @Bean
    public Job readMaxJob() {
        return jobBuilderFactory
                .get("readMaxJob")
                .incrementer(new RunIdIncrementer())
                .start(step1()).next(step2()).next(step3()).next(step4())
                .build();
    }
 
    
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("TruncateMaxAssociationTasklet").
        		tasklet(TruncateMaxAssociationTasklet).
        		build();
         }
    
	
	 @Bean public Step step2() { return stepBuilderFactory .get("step2").
	  tasklet(insertxumoassociationtasklet)
	 .build();
	 }
	 
     
  
	 @Bean
	    public Step step3() {
	        return stepBuilderFactory
	                .get("step3")
	                .<Program,Program>chunk(1000)
	                .reader(programReader)
	                .processor(Proccessor)
	                .writer(ProgramWriter)
	                .build();
	    }
 
	 
	 @Bean
	    public Step step4() {
	        return stepBuilderFactory
	                .get("step3")
	                .<Program,Program>chunk(1000)
	                .reader(ScheduleReader)
	                .processor(ScheduleProcessor)
	                .writer(ProgramWriter)
	                .build();
	    }

 
	 
	 
	 
	 
	 
	/*
	 * @Bean public JdbcBatchItemWriter<Employee> writer() {
	 * JdbcBatchItemWriter<Employee> itemWriter = new
	 * JdbcBatchItemWriter<Employee>(); itemWriter.setDataSource(dataSource());
	 * itemWriter.
	 * setSql("INSERT INTO EMPLOYEE (ID, FIRSTNAME, LASTNAME) VALUES (:id, :firstName, :lastName)"
	 * ); itemWriter.setItemSqlParameterSourceProvider(new
	 * BeanPropertyItemSqlParameterSourceProvider<Employee>()); return itemWriter; }
	 */
    
    
  
    
    
    
     
   
}