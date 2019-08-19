package com.tivo.metadata.max.config;

import java.util.List;

import javax.batch.api.chunk.ItemReader;
import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import com.tivo.metadata.max.RowMapper.ProgramRowMapper;
import com.tivo.metadata.max.model.Employee;
import com.tivo.metadata.max.model.Program;

@Component
public class Reader {

	 @Value("classPath:/input/inputData.csv")
	   private Resource inputResource;
	 
	 @Autowired
	 DataSource datasource;

    @Bean
    public JsonItemReader<Employee> jsonItemReader() {
       return new JsonItemReaderBuilder<Employee>()
                     .jsonObjectReader(new JacksonJsonObjectReader<>(Employee.class))
                     .resource(new ClassPathResource("trades1.json"))
                     .name("tradeJsonItemReader")
                     .build();
    }
	
    
    
    @Bean
    public FlatFileItemReader<Employee> reader2() {
        FlatFileItemReader<Employee> itemReader = new FlatFileItemReader<Employee>();
        itemReader.setLineMapper(lineMapper());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(inputResource);
        return itemReader;
    }
 
    
    @Bean("programReader")
  public JdbcCursorItemReader<List<Object>> databaseItemReader(DataSource dataSource) {
     
    	String QUERY_FIND_PROGRAMS="select	(SELECT " + 
        		"                                         program.provider," + 
        		"                                         program.provider_program_id" + 
        		"                                         FROM  ACESDR.dbo.program AS program  JOIN " + 
        		"										  " + 
        		"										 ACESDR.dbo.schedule AS schedule " + 
        		"										 ON  schedule.provider='xumo' " + 
        		"										 AND schedule.provider_program_id=program.provider_program_id " + 
        		"										 WHERE  " + 
        		"										  program.properties IS NOT NULL AND " + 
        		"										  schedule.properties IS NOT NULL for json path) as   jsonString" ;
    	JdbcCursorItemReader<List<Object>> databaseReader = new JdbcCursorItemReader<>();
 
        databaseReader.setDataSource(dataSource);
        databaseReader.setSql(QUERY_FIND_PROGRAMS);
        databaseReader.setRowMapper(new ProgramRowMapper());
 
        return databaseReader;
    }
    
    @Bean("ScheduleReader")
    public JdbcCursorItemReader<List<Object>> databaseScheduleItemReader(DataSource dataSource) {
       
      	String QUERY_FIND_PROGRAMS="select (select * from SamsungPassthrough.dbo.MAX_ASSOCIATION_SCHEDULE_xumo for json path) as jsonString	 "  ;
      	JdbcCursorItemReader<List<Object>> databaseReader = new JdbcCursorItemReader<>();
   
          databaseReader.setDataSource(dataSource);
          databaseReader.setSql(QUERY_FIND_PROGRAMS);
          databaseReader.setRowMapper(new ProgramRowMapper());
   
          return databaseReader;
      }
    
    
    @Bean
    public LineMapper<Employee> lineMapper() {
        DefaultLineMapper<Employee> lineMapper = new DefaultLineMapper<Employee>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[] { "id", "firstName", "lastName" });
        lineTokenizer.setIncludedFields(new int[] { 0, 1, 2 });
        BeanWrapperFieldSetMapper<Employee> fieldSetMapper = new BeanWrapperFieldSetMapper<Employee>();
        fieldSetMapper.setTargetType(Employee.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
    
}
